package dao;

import com.mongodb.DBObject;

import net.sf.json.JSONObject;

public interface QuestionnaireDao {
	public Integer save(DBObject questionnaireJSON);

	public void delete();

	public Integer update(String id, DBObject questionnaireDB);
	
	public DBObject findQuestionnaireById(String id);

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
