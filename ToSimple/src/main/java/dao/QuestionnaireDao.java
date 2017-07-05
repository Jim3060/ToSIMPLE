package dao;

import com.mongodb.DBObject;

import model.Questionnaire;
import net.sf.json.JSONObject;

public interface QuestionnaireDao {
	public String save(Questionnaire questionnaire);

	/**
	 * delete a questionnaire.
	 * @param id
	 * @return 1 if was delete success,else return 0
	 */
	public Integer delete(String id);

	public String update(String id, Questionnaire questionnaire);
	
	public Questionnaire findQuestionnaireById(String id);
	
	public Questionnaire findQuestionnaireByUser(int id);

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
