package ToolUtils;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import model.Questionnaire;
import model.QuestionnaireGSON;
import model.QuestionnaireResult;
import model.QuestionnaireResultGSON;
import org.apache.poi.hssf.usermodel.*;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.jni.Lock;

import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireResultGSON;
import model.QuestionnaireStatistics;
import model.QuestionnaireGSON;

public class EXELUtils {
	private static ReentrantLock lock = new ReentrantLock();
	DateFormat df = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");
	public static HSSFWorkbook GenerateRawResultStatistics(Questionnaire questionnaire,List<QuestionnaireResult> questionnaireResults) throws ParseException{
		QuestionnaireGSON questionnaireGSON=QuestionnaireGSON.getQuestionnaireGSON(questionnaire.getQuestionnaire());
		HSSFWorkbook wb = new HSSFWorkbook();  
        
        HSSFSheet sheet = wb.createSheet(questionnaireGSON.paperTitle+"statistics");  
        HSSFRow row = sheet.createRow((int) 0);  
        HSSFCellStyle style = wb.createCellStyle();  

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

        //input questions
        int beginPadding = 3;
        HSSFCell cellb = row.createCell((short) 0);
        cellb.setCellValue("Client IP");
        cellb.setCellStyle(style);
        cellb = row.createCell((short) 1);
        cellb.setCellValue("Answer Time");
        cellb.setCellStyle(style);
        cellb = row.createCell((short) 2);
        cellb.setCellValue("Timing");
        cellb.setCellStyle(style);
//        HSSFCell cellb = row.createCell((short) 0);  
//        cellb.setCellValue("Timing");  
//        cellb.setCellStyle(style);  
        for (int i = 0; i < questionnaireGSON.questions.size(); i++) {
            HSSFCell cell = row.createCell((short) i + beginPadding);
            cell.setCellValue(questionnaireGSON.questions.get(i).questionTitle);
            cell.setCellStyle(style);
        }

        //input answers
        System.out.println(questionnaireResults.size());
        for (int i = 0; i < questionnaireResults.size(); i++) {
            QuestionnaireResultGSON questionnaireResultGSON = QuestionnaireResultGSON.getQuestionnaireResultGSON(questionnaireResults.get(i).getQuestionnaireResult());
            HSSFRow rowa = sheet.createRow((int) i + 1);
            //add other info here

            HSSFCell cellbb = rowa.createCell((short) 0);
            cellbb.setCellValue(questionnaireResultGSON.userIP);
            cellbb.setCellStyle(style);

            cellbb = rowa.createCell((short) 1);
            cellbb.setCellValue(TimeUtils.toNormalTime(questionnaireResultGSON.beginTime));
            cellbb.setCellStyle(style);

            cellbb = rowa.createCell((short) 2);
            Long timing = TimeUtils.getLocalTime(questionnaireResultGSON.endTime).getTime() - TimeUtils.getLocalTime(questionnaireResultGSON.beginTime).getTime();
            cellbb.setCellValue(String.valueOf(timing / 1000) + "s");
            cellbb.setCellStyle(style);


            for (int j = 0; j < questionnaireResultGSON.answers.size(); j++) {
                System.out.println("hi");

                HSSFCell cell = rowa.createCell((short) j + beginPadding);
                if (questionnaireGSON.questions.get(j).type == 2) {
                    cell.setCellValue(questionnaireResultGSON.answers.get(j).blank);
                    System.out.println(questionnaireResultGSON.answers.get(j).blank);
                } else if (questionnaireGSON.questions.get(j).type == 1) {
                    String tmp = "";
                    for (int k = 0; k < questionnaireResultGSON.answers.get(j).choice.size(); k++) {
                        tmp += questionnaireGSON.questions.get(j).choices.get(questionnaireResultGSON.answers.get(j).choice.get(k)).text + ", ";
                    }
                    cell.setCellValue(tmp);
                } else {
                    for (int k = 0; k < questionnaireResultGSON.answers.get(j).choice.size(); k++) {
                        cell.setCellValue(questionnaireGSON.questions.get(j).choices.get(questionnaireResultGSON.answers.get(j).choice.get(k)).text);
                    }
                }
                cell.setCellStyle(style);
            }
        }

        
		return wb;
	}
	
	
	
	
	public static HSSFWorkbook generateChartStatistics(QuestionnaireStatistics questionnairestat) throws IOException{
		HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet(questionnairestat.questionnaireTitle+"_chartStatistics");  
        HSSFRow row = sheet.createRow((int) 0);  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        
        int picWidth=4;
        int picHeight=20;
        List<Integer> picRow=new ArrayList<Integer>();
        //load each question
        int rowNum=0;

        List<QuestionnaireStatistics.Question> questions=questionnairestat.questions;
        for (int i=0;i<questions.size();i++){
        	if  (questions.get(i).type==2){continue;}
        	int cNum=0;
        	row = sheet.createRow(rowNum); rowNum++;
            HSSFCell cell = row.createCell((short) cNum);cNum++;  
    		cell.setCellValue(questions.get(i).title);  
    		cell.setCellStyle(style); 
    		//load choice title;
    		List<QuestionnaireStatistics.Choice> choices=questions.get(i).choices;
    		for (int j=0;j<choices.size();j++){
    			cell = row.createCell((short) cNum);  cNum++;  
        		cell.setCellValue(choices.get(j).title);  
        		cell.setCellStyle(style); 
    		}
    		//load number
    		cNum=0;
    		row = sheet.createRow(rowNum); rowNum++;
            cell = row.createCell((short) cNum);cNum++;  
    		cell.setCellValue("");  
    		cell.setCellStyle(style); 
    		choices=questions.get(i).choices;
    		for (int j=0;j<choices.size();j++){
    			cell = row.createCell((short) cNum); cNum++;   
        		cell.setCellValue(choices.get(j).number);  
        		cell.setCellStyle(style); 
    		}
    		
    		//insert chart
    		rowNum++;
    		//read the image
//            BufferedImage bufferImg ;        
//			ByteArrayOutputStream byteArrayOut;     
			File filec=ChartUtils.CreateBarChart(questions.get(i).getBarDataSet(), questions.get(i).title, "Choices", "Number");
			//File filep=ChartUtils.CreatePieChart(questions.get(i).getPieDataSet(), questions.get(i).title);
			
			
	
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 1, rowNum, (short) (7), rowNum+picHeight); 
	        //HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 0, 0,(short) 8, rowNum, (short) (14), rowNum+picHeight); 
			anchor.setAnchorType(2);     
			//anchor2.setAnchorType(2); 
			
			createPic(wb,patriarch,anchor,filec);
			//createPic(wb,patriarch,anchor2,filep);
			
//			bufferImg = null;        
//			byteArrayOut = new ByteArrayOutputStream();     
//			bufferImg = ImageIO.read(filec);     
//			ImageIO.write(bufferImg, "png", byteArrayOut);  
//			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG)); 
//			
//			bufferImg = null;        
//			byteArrayOut = new ByteArrayOutputStream(); 
//			bufferImg = ImageIO.read(filep);     
//			ImageIO.write(bufferImg, "png", byteArrayOut);  
//			patriarch.createPicture(anchor2, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG)); 
//			
			rowNum+=picHeight;
			
			//leave an empty line
    		rowNum++;
    		
    		
        }
      
        
        
		return wb;
	}
	
	public static void createPic(HSSFWorkbook wb,HSSFPatriarch patriarch,HSSFClientAnchor anchor,File file) throws IOException{
		
		BufferedImage bufferImg = null;        
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(); 
		bufferImg = ImageIO.read(file);     
		ImageIO.write(bufferImg, "png", byteArrayOut);  
		patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG)); 
		
	}
	
	
	
	

}
