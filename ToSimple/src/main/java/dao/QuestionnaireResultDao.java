package dao;

import model.QuestionnaireResult;

import java.util.List;

public interface QuestionnaireResultDao {


    Integer save(QuestionnaireResult questionnaireResult);

    List<QuestionnaireResult> getAllQuestionnaireResultById(String id);

    QuestionnaireResult getQuestionnaireResultById(String id);


//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
