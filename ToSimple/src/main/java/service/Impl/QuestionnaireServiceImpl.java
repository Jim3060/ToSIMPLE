package service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
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
	public String addQuestionnaire(String questionnaireJSON) {
		DBObject questionnaireDB= (DBObject)JSON.parse(questionnaireJSON); 
		return questionnaireDao.save(questionnaireDB);
	}

	@Override
	public void deleteQuestionnaire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer updateQuestionnaire(String id, String questionnaireJSON) {
		DBObject questionnaireDB=(DBObject)JSON.parse(questionnaireJSON); 
		if (questionnaireDao.update(id, questionnaireDB)==null){return 0;}
		return 1;
		// TODO Auto-generated method stub
		
	}


	@Override
	public String findQuestionnaireById(String id) {
		// TODO Auto-generated method stub
		DBObject questionnaireDB=questionnaireDao.findQuestionnaireById(id);
		if (questionnaireDB==null){return null;}
		questionnaireDB.put("questionnaireId", questionnaireDB.get("_id").toString());
		return questionnaireDB.toString();
	}


	@Override
	public Integer addQuestionnaireResult(String questionnaireResultJSON, HttpServletRequest request) {
		JSONObject questionnaireResult= JSONObject.fromObject(questionnaireResultJSON);
		String userIP = request.getHeader("x-forwarded-for");
		questionnaireResult.put("userIP", userIP);
		DBObject questionnaireResultDB= (DBObject)JSON.parse(questionnaireResult.toString()); 
		questionnaireResultDao.save(questionnaireResultDB);
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String addOrUpdateQuestionnaire(String questionnaire) {
		String questionnaireId;
		JSONObject questionnaireJSON=JSONObject.fromObject(questionnaire);
		//update
		if (questionnaireJSON.get("questionnaireId")!=null&&(questionnaireJSON.get("questionnaireId"))!=""){
			questionnaireId=questionnaireJSON.get("questionnaireId").toString();
			DBObject questionnaireDB=(DBObject)JSON.parse(questionnaire); 
			questionnaireId = questionnaireDao.update(questionnaireId, questionnaireDB);
			
			
		}
		//add
		else {
			DBObject questionnaireDB= (DBObject)JSON.parse(questionnaire); 
			questionnaireId = questionnaireDao.save(questionnaireDB);
		}
		
		return questionnaireId;
	}


	

}
