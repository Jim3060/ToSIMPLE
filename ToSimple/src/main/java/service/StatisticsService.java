package service;

import model.QuestionnaireStatistics;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.text.ParseException;


public interface StatisticsService {
	public QuestionnaireStatistics getQuestionnaireStatisticsById(String id);
	
	public HSSFWorkbook exportToEXEL(String questionnaireId) throws IOException, ParseException;
	
	public HSSFWorkbook exportChartToEXEL(String questionnaireId) throws IOException, ParseException;
	
	

}
