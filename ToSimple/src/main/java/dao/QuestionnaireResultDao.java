package dao;

import java.util.List;

import com.mongodb.DBObject;

import model.QuestionnaireResult;
import net.sf.json.JSONObject;

public interface QuestionnaireResultDao {
<<<<<<< HEAD
	
	public Integer save(QuestionnaireResult questionnaireResult);
	
	public List<QuestionnaireResult> getAllQuestionnaireResultById(String id);
	
	public QuestionnaireResult getQuestionnaireResultById(String id);
	
	
	
=======

    public Integer save(QuestionnaireResult questionnaireResult);

    public List<QuestionnaireResult> getAllQuestionnaireResultById(String id);
>>>>>>> ceb6d8281b72df5952abea6bb4c7349c1b979116

//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
