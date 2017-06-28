package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.User;
import net.sf.json.JSONObject;

public interface QuestionnaireService {
	public Integer addQuestionnaire(String questionnaireJSON);

	public void deleteQuestionnaire();

	public void updateQuestionnaire();
	
	public String findQuestionnaireById(String id);

	public Integer addQuestionnaireResult(String questionnaireResultJSON, HttpServletRequest request);
}
