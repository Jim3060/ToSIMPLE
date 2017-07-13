package service.Impl;

import ToolUtils.CountUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.Questionnaire;
import model.QuestionnaireGSON;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import service.QuestionnaireService;

import java.util.ArrayList;
import java.util.List;


public class QuestionnaireServiceImpl implements QuestionnaireService {
    private QuestionnaireDao questionnaireDao;
    private QuestionnaireResultDao questionnaireResultDao;

    public static void main(String[] args) {

//		String questinnaireSTRING="{'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0}";
//		Questionnaire questionnaire=new Questionnaire(questinnaireSTRING);
//		String answerSTRING="{'answer':{'0':[[0],''],'1':[[1,2],''],'2':[[],'test']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T03:17:56.866Z'}";
//		String answerSTRING2="{'answer':{'0':[[1],''],'1':[[0,1],''],'2':[[],'test2']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T06:57:10.473Z'}";
//		List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING));
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING2));
//		QuestionnaireStatistics q = new QuestionnaireStatistics(questionnaire,questionnaireResults);
        String jsontest = "{'_id':{'$oid':'5959b6badac1e1082a93c51d'},'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0,'questionnaireId':'5959b6badac1e1082a93c51d'}";
        Gson gson = new GsonBuilder().create();
        QuestionnaireGSON p = gson.fromJson(jsontest, QuestionnaireGSON.class);
        System.out.println(p);
    }

    public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
        this.questionnaireDao = questionnaireDao;
    }

    public void setQuestionnaireResultDao(QuestionnaireResultDao questionnaireResultDao) {
        this.questionnaireResultDao = questionnaireResultDao;
    }

    @Override
    public Integer deleteQuestionnaire(String id) {
        return questionnaireDao.delete(id);
        // TODO Auto-generated method stub

    }

    @Override
    public Questionnaire findQuestionnaireById(String id) {
        // TODO Auto-generated method stub
        return questionnaireDao.findQuestionnaireById(id);

    }

    @Override
    public QuestionnaireResult getQuestionnaireResultByid(String id) {
        return questionnaireResultDao.getQuestionnaireResultById(id);
    }

    @Override
    public Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult) {
        questionnaireResultDao.save(questionnaireResult);
        //update answer number
        String questionnaireId=(String) questionnaireResult.questionnaireResultJSON.get("questionnaireId");
        Questionnaire questionnaire=questionnaireDao.findQuestionnaireById(questionnaireId);
        System.out.println((int)questionnaire.questionnaireJSON.get("answerNumber"));
        questionnaire.questionnaireJSON.put("answerNumber",((int)questionnaire.questionnaireJSON.get("answerNumber")+1));
        questionnaireDao.update(questionnaireId, questionnaire);
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<Questionnaire> getReportedQuestionnaire(){
    	return questionnaireDao.getReportedQuestionnaire();
    }

    @Override
    public String addOrUpdateQuestionnaire(Questionnaire questionnaire) {
        String questionnaireId;
        if (questionnaire.getQuestionnaireId().equals("")) {//add
            questionnaireId = questionnaireDao.save(questionnaire);
        } else {//update
            questionnaireId = questionnaireDao.update(questionnaire.getQuestionnaireId(), questionnaire);
        }
        return questionnaireId;
    }

    public QuestionnaireStatistics getQuestionnaireStatisticsById(String id) {
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(id);
        List<QuestionnaireResult> questionnaireResults = questionnaireResultDao.getAllQuestionnaireResultById(id);
        return new QuestionnaireStatistics(questionnaire, questionnaireResults);
    }

    @Override
    public List<Questionnaire> searchQuestionnaireByName(Integer page, Integer pageSize, String name, CountUtils countUtils) {
        return questionnaireDao.searchQuestionnaireByName(page, pageSize, name, countUtils);
    }

    @Override
    public List<Questionnaire> searchQuestionnaireByName(String name, CountUtils countUtils) {
        return questionnaireDao.searchQuestionnaireByName(name, countUtils);
    }


    @Override
    public List<Questionnaire> findQuestionnaireByStatus(Integer status, CountUtils countUtils) {
        return questionnaireDao.findQuestionnaireByStatus(status, countUtils);
    }

    @Override
    public List<Questionnaire> fetchAll(Integer page, Integer pageSize, CountUtils countUtils) {
        return questionnaireDao.fetchAll(page, pageSize, countUtils);
    }

    @Override
    public List<Questionnaire> randomQuestionnaire(Integer size) {
        return questionnaireDao.randomQuestionnaire(size);
    }

    @Override
    public List<Questionnaire> findQuestionnairesByUser(Long userid) {
        List<Questionnaire> raw = questionnaireDao.findQuestionnaireByUser(userid);
        List<Questionnaire> result = new ArrayList<Questionnaire>();
        //Questionnaire tmp=new Questionnaire();
        for (int i = 0; i < raw.size(); i++) {
            raw.get(i).questionnaireJSON.remove("questions");
            result.add(new Questionnaire(raw.get(i).getQuestionnaire()));
        }
        return result;
    }

	@Override
	public List<Questionnaire> getReportedQuestionnaireByPage(int page, int pageSize,CountUtils countUtils) {
		
		return questionnaireDao.getReportedQuestionnaireByPage(page,pageSize,countUtils);
	}

}
