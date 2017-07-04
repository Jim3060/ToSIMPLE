package service;

import java.io.IOException;

import model.QuestionnaireStatistics;

public interface StatisticsService {
	public QuestionnaireStatistics getQuestionnaireStatisticsById(String id);
	
	public Integer exportToEXEL(String questionnaireId,String filePath) throws IOException;
}
