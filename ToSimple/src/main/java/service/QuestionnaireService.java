package service;

import java.util.List;

import model.User;
import net.sf.json.JSONObject;

public interface QuestionnaireService {
	public Integer addQuestionnaire(String questionnaireJSON);

	public void deleteQuestionnaire();

	public void updateQuestionnaire();
	
	public String findQuestionnaireById(String id);


}
