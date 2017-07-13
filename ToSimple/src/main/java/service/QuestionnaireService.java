package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ToolUtils.CountUtils;
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

	/**
	 * fetch all questionnaires.
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Questionnaire> fetchAll(Integer page,Integer pageSize, CountUtils countUtils);

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
	public List<Questionnaire> searchQuestionnaireByName(String name, CountUtils countUtils);

	/**
	 * search questionnaire by name. max num is 30
	 * @param page page num
	 * @param pageSize num of the page
	 * @param name
	 * @return
	 */
	public List<Questionnaire> searchQuestionnaireByName(Integer page,Integer pageSize,String name, CountUtils countUtils);

	public List<Questionnaire> findQuestionnaireByStatus(Integer status, CountUtils countUtils);
	
	public List<Questionnaire> findQuestionnairesByUser(Long userid);

	public List<Questionnaire> getReportedQuestionnaire();

	public List<Questionnaire> getReportedQuestionnaireByPage(int page , int pageSize,CountUtils countUtils);

}
