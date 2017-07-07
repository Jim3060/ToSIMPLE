package dao;

import java.util.List;

import com.mongodb.DBObject;

import model.QuestionnaireResult;
import net.sf.json.JSONObject;

public interface QuestionnaireResultDao {
	
	public Integer save(QuestionnaireResult questionnaireResult);
	
	public List<QuestionnaireResult> getAllQuestionnaireResultById(String id);
	
	public QuestionnaireResult getQuestionnaireResultById(String id);
	
	
	

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
