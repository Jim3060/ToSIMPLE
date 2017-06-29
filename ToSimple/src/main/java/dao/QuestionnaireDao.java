package dao;

import com.mongodb.DBObject;

import net.sf.json.JSONObject;

public interface QuestionnaireDao {
	public String save(DBObject questionnaireJSON);

	public void delete();

	public String update(String id, DBObject questionnaireDB);
	
	public DBObject findQuestionnaireById(String id);

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
