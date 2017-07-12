package ToolUtils;

import model.Questionnaire;
import model.QuestionnaireGSON;
import model.QuestionnaireResult;
import model.QuestionnaireResultGSON;
import org.apache.poi.hssf.usermodel.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EXELUtils {
    DateFormat df = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ssZ");

    public static HSSFWorkbook GenerateRawResultStatistics(Questionnaire questionnaire, List<QuestionnaireResult> questionnaireResults) throws ParseException {
        QuestionnaireGSON questionnaireGSON = QuestionnaireGSON.getQuestionnaireGSON(questionnaire.getQuestionnaire());
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(questionnaireGSON.paperTitle + "statistics");
        HSSFRow row = sheet.createRow(0);
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
            HSSFRow rowa = sheet.createRow(i + 1);
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
                        tmp += questionnaireGSON.questions.get(j).choices.get(questionnaireResultGSON.answers.get(j).choice.get(k)) + ", ";
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
}
