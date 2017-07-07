package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import model.User;
import net.sf.json.JSONObject;

public interface QuestionnaireService {
	public String addOrUpdateQuestionnaire(Questionnaire questionnaireJSON);

	/**
	 * delete a questionnaire.
	 * @param id
	 * @return 1 if deleted success,0 if fail.
	 */
	public Integer deleteQuestionnaire(String id);
	
	public Questionnaire findQuestionnaireById(String id);

	public Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult);
	
	public QuestionnaireResult getQuestionnaireResultByid(String id);
}
