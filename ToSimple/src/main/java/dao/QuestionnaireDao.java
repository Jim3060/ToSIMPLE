package dao;

import com.mongodb.DBObject;

import model.Questionnaire;
import net.sf.json.JSONObject;

import java.util.List;

public interface QuestionnaireDao {
    public String save(Questionnaire questionnaire);

    /**
     * delete a questionnaire.
     *
     * @param id
     * @return 1 if was delete success,else return 0
     */
    public Integer delete(String id);

    public String update(String id, Questionnaire questionnaire);

    public Questionnaire findQuestionnaireById(String id);

    public List<Questionnaire> findQuestionnaireByUser(Long id);

    /**
     * get a sample of questionnaires. The questionnaire status must be 1
     * @param size the max size of the questionnaires.
     * @return
     */
    public List<Questionnaire> randomQuestionnaire(Integer size);

    /**
     * search questionnaires by name.(fuzzy search) Only return the questionnaire with status equals 1
     * @param page the page num.begin with 0.
     * @param name the name
     * @param pageSize the number of questionnaire not allowed bigger than 30.
     * @return  the list of questionnaires
     */
    public List<Questionnaire> searchQuestionnaireByName(Integer page,Integer pageSize,String name);

    /**
     * search questionnaires by name.(fuzzy search)
     * @param name the name
     * @return  the list of questionnaires
     */
    public List<Questionnaire> searchQuestionnaireByName(String name);



    /**
     * find questionnaires by status
     * @param status the status of the questionnaire
     * @return the list of questionnaire
     */
    public List<Questionnaire> findQuestionnaireByStatus(Integer status);
    
    

//    /**
//     * find questionnaires by status
//     * @param status the status of the questionnaire
//     * @param size the number of questionnaire not allowed bigger than 30.
//     * @return the list of questionnaire
//     */
//    public List<Questionnaire> findQuestionnaireByStatus(Integer status,Integer size);



//	public User getQuestionnaireById(int id);
//
//	public List<User> getAllQuestionnaires();
}
