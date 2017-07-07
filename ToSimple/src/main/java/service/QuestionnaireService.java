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
	

	public QuestionnaireResult getQuestionnaireResultByid(String id);

	public QuestionnaireStatistics getQuestionnaireStatisticsById(String id);

	/**
	 * Get n random questionnaires.
	 * @param size
	 * @return
	 */
	public List<Questionnaire> randomQuestionnaire(Integer size);


	/**
	 * search questionnaire by name. max num is 30
	 * @param name
	 * @return
	 */
	public List<Questionnaire> searchQuestionnaireByName(String name);

	/**
	 * search questionnaire by name. max num is 30
	 * @param page page num
	 * @param pageSize num of the page
	 * @param name
	 * @return
	 */
	public List<Questionnaire> searchQuestionnaireByName(Integer page,Integer pageSize,String name);


	public List<Questionnaire> findQuestionnaireByStatus(Integer status);

}
