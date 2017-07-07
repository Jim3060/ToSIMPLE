package service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.Questionnaire;
import model.QuestionnaireGSON;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import net.sf.json.JSONObject;
import service.QuestionnaireService;

public class QuestionnaireServiceImpl implements QuestionnaireService{
	private QuestionnaireDao questionnaireDao;
	
	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}
	
	private QuestionnaireResultDao questionnaireResultDao;
	
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
	public Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult) {
		questionnaireResultDao.save(questionnaireResult);
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addOrUpdateQuestionnaire(Questionnaire questionnaire) {
		String questionnaireId;
		if (questionnaire.getQuestionnaireId().equals("")){//add
			questionnaireId = questionnaireDao.save(questionnaire);
		}
		else{//update
			questionnaireId = questionnaireDao.update(questionnaire.getQuestionnaireId(),questionnaire);
		}
		return questionnaireId;
	}

	
	@Override
	public QuestionnaireResult getQuestionnaireResultByid(String id) {
		return questionnaireResultDao.getQuestionnaireResultById(id);
	}
	
	public static void main(String[] args) {
//		String questinnaireSTRING="{'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0}";
//		Questionnaire questionnaire=new Questionnaire(questinnaireSTRING);
//		String answerSTRING="{'answer':{'0':[[0],''],'1':[[1,2],''],'2':[[],'test']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T03:17:56.866Z'}";
//		String answerSTRING2="{'answer':{'0':[[1],''],'1':[[0,1],''],'2':[[],'test2']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T06:57:10.473Z'}";
//		List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING));
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING2));
//		QuestionnaireStatistics q = new QuestionnaireStatistics(questionnaire,questionnaireResults);
		String jsontest="{'_id':{'$oid':'5959b6badac1e1082a93c51d'},'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0,'questionnaireId':'5959b6badac1e1082a93c51d'}";
        Gson gson = new GsonBuilder().create();
        QuestionnaireGSON p = gson.fromJson(jsontest, QuestionnaireGSON.class);
        System.out.println(p);
	}

	

	

}
