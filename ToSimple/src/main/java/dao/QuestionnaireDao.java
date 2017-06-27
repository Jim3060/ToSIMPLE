package dao;

import net.sf.json.JSONObject;

public interface QuestionnaireDao {
	public Integer save(String questionnaireJSON);

	public void delete();

	public void update();
	
	public String findQuestionnaireById(String id);

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
