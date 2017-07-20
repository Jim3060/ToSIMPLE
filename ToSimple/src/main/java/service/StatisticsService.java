package service;

import model.QuestionnaireStatistics;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.text.ParseException;

public interface StatisticsService {
    QuestionnaireStatistics getQuestionnaireStatisticsById(String id);

    HSSFWorkbook exportToEXEL(String questionnaireId) throws IOException, ParseException;


}
