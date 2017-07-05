package service;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.QuestionnaireStatistics;

public interface StatisticsService {
	public QuestionnaireStatistics getQuestionnaireStatisticsById(String id);
	
	public HSSFWorkbook exportToEXEL(String questionnaireId) throws IOException, ParseException;
}
