package dao;

import com.mongodb.DBObject;

import model.Questionnaire;
import net.sf.json.JSONObject;

public interface QuestionnaireDao {
	public String save(Questionnaire questionnaire);

	public void delete();

	public String update(String id, Questionnaire questionnaire);
	
	public Questionnaire findQuestionnaireById(String id);
	
	public Questionnaire findQuestionnaireByUser(int id);

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
