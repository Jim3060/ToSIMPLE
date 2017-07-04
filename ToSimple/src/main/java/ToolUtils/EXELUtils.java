package ToolUtils;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireResultGSON;
import model.QuestionnaireGSON;

public class EXELUtils {
	public static HSSFWorkbook GenerateRawResultStatistics(Questionnaire questionnaire,List<QuestionnaireResult> questionnaireResults){
		QuestionnaireGSON questionnaireGSON=QuestionnaireGSON.getQuestionnaireGSON(questionnaire.getQuestionnaire());
		HSSFWorkbook wb = new HSSFWorkbook();  
        
        HSSFSheet sheet = wb.createSheet(questionnaireGSON.paperTitle+"statistics");  
        HSSFRow row = sheet.createRow((int) 0);  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        
        //input questions
        for (int i=0;i<questionnaireGSON.questions.size();i++){
        	HSSFCell cell = row.createCell((short) i);  
            cell.setCellValue(questionnaireGSON.questions.get(i).questionTitle);  
            cell.setCellStyle(style);  
        }
        
        //input answers
        System.out.println(questionnaireResults.size());
        for (int i=0;i<questionnaireResults.size();i++){
        	QuestionnaireResultGSON questionnaireResultGSON=QuestionnaireResultGSON.getQuestionnaireResultGSON(questionnaireResults.get(i).getQuestionnaireResult());
        	HSSFRow rowa = sheet.createRow((int) i+1); 
        	for (int j=0;j<questionnaireResultGSON.answers.size();j++){
        		System.out.println("hi");
        		
        		HSSFCell cell = rowa.createCell((short) j);  
        		if (questionnaireGSON.questions.get(j).type==2){
        			cell.setCellValue(questionnaireResultGSON.answers.get(j).blank);
        			System.out.println(questionnaireResultGSON.answers.get(j).blank);
        		}
        		else if (questionnaireGSON.questions.get(j).type==1){
        			String tmp="";
        			for (int k=0;k<questionnaireResultGSON.answers.get(j).choice.size();k++){
        				tmp+=questionnaireGSON.questions.get(j).choices.get(questionnaireResultGSON.answers.get(j).choice.get(k))+", ";
        			}
        			cell.setCellValue(tmp);
        		}
        		else {
        			for (int k=0;k<questionnaireResultGSON.answers.get(j).choice.size();k++){
        				cell.setCellValue(questionnaireGSON.questions.get(j).choices.get(questionnaireResultGSON.answers.get(j).choice.get(k)));
        			}
        		}
                cell.setCellStyle(style);  
        	}
        }
        
		return wb;
	}
}
