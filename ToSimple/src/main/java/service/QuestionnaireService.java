package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import model.User;
import net.sf.json.JSONObject;

public interface QuestionnaireService {
	public String addOrUpdateQuestionnaire(Questionnaire questionnaireJSON);

	/**
	 * delete a questionnaire.
	 * @param id
	 * @return 1 if deleted success,0 if fail.
	 */
	public Integer deleteQuestionnaire(String id);
	
	public Questionnaire findQuestionnaireById(String id);

	public Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult);
	
	public QuestionnaireStatistics getQuestionnaireStatisticsById(String id);

	/**
	 * search questionnaire by name.
	 * @param name
	 * @param size
	 * @return
	 */
	public List<Questionnaire> searchQuestionnaireByName(String name,Integer size);


	/**
	 * search questionnaire by name. max num is 30
	 * @param name
	 * @return
	 */
	public List<Questionnaire> searchQuestionnaireByName(String name);


	public List<Questionnaire> findQuestionnaireByStatus(Integer status);
}
