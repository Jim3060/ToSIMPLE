package service.Impl;

import java.util.List;

import dao.QuestionnaireDao;
import net.sf.json.JSONObject;
import service.QuestionnaireService;

public class QuestionnaireServiceImpl implements QuestionnaireService{
	private QuestionnaireDao questionnaireDao;
	
	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}
	

	@Override
	public Integer addQuestionnaire(String questionnaireJSON) {
		questionnaireDao.save(questionnaireJSON);
		return null;
	}

	@Override
	public void deleteQuestionnaire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateQuestionnaire() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String findQuestionnaireById(String id) {
		// TODO Auto-generated method stub
		return questionnaireDao.findQuestionnaireById(id);
	}


	

}
