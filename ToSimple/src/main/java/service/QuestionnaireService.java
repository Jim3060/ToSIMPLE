package service;

import ToolUtils.CountUtils;
import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;

import java.util.List;

public interface QuestionnaireService {
    String addOrUpdateQuestionnaire(Questionnaire questionnaireJSON);

    /**
     * delete a questionnaire.
     *
     * @param id
     * @return 1 if deleted success,0 if fail.
     */
    Integer deleteQuestionnaire(String id);

    /**
     * fetch all questionnaires.
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<Questionnaire> fetchAll(Integer page, Integer pageSize, CountUtils countUtils);

    Questionnaire findQuestionnaireById(String id);

    Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult);


    QuestionnaireResult getQuestionnaireResultByid(String id);

    QuestionnaireStatistics getQuestionnaireStatisticsById(String id);

    /**
     * Get n random questionnaires.
     *
     * @param size
     * @return
     */
    List<Questionnaire> randomQuestionnaire(Integer size);


    /**
     * search questionnaire by name. max num is 30
     *
     * @param name
     * @return
     */
    List<Questionnaire> searchQuestionnaireByName(String name, CountUtils countUtils);

    /**
     * search questionnaire by name. max num is 30
     *
     * @param page     page num
     * @param pageSize num of the page
     * @param name
     * @return
     */
    List<Questionnaire> searchQuestionnaireByName(Integer page, Integer pageSize, String name, CountUtils countUtils);

    List<Questionnaire> findQuestionnaireByStatus(Integer status, CountUtils countUtils);

    List<Questionnaire> findQuestionnairesByUser(Long userid);

}
