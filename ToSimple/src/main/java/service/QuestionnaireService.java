package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Questionnaire;
import model.QuestionnaireResult;
import model.User;
import net.sf.json.JSONObject;

public interface QuestionnaireService {
	public String addOrUpdateQuestionnaire(Questionnaire questionnaireJSON);
	
	public String addQuestionnaire(String questionnaireJSON);

	public void deleteQuestionnaire();

	public Integer updateQuestionnaire(String id, String questionnaireJSON);
	
	public Questionnaire findQuestionnaireById(String id);

	public Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult);
}
