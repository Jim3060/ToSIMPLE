package service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.Questionnaire;
import model.QuestionnaireResult;
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
	public void deleteQuestionnaire() {
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
	public String addQuestionnaire(String questionnaireJSON) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Integer updateQuestionnaire(String id, String questionnaireJSON) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
