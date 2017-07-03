package model;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import net.sf.json.JSONObject;

public class QuestionnaireResult {
	private JSONObject questionnaireResultJSON;

	public QuestionnaireResult(){;}
	
	public QuestionnaireResult(String questionnaireResult, HttpServletRequest request) {
		this.questionnaireResultJSON = JSONObject.fromObject(questionnaireResult);
		String userIP = request.getHeader("x-forwarded-for");
		this.questionnaireResultJSON.put("userIP",userIP);
	}
	
	public DBObject getQuestionnaireResultDB(){
		return (DBObject)JSON.parse(this.questionnaireResultJSON.toString());
	}
	
	
	
}
