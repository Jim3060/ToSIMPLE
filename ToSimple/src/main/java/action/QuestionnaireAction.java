package action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import service.QuestionnaireService;

public class QuestionnaireAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private QuestionnaireService questionnaireService;
	public void setQuestionnaireService(QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}
	
	
	private String OBJtest;
	private String questionnaireId;
	private String questionnaire;
	private String answerPaper;
	
	

	public String addOrUpdateQuestionnaire() throws Exception{
		JSONObject questionnaireJSON=JSONObject.fromObject(questionnaire);
		if (questionnaireJSON.get("objectId")!=null&&(questionnaireJSON.get("objectId"))!=""){
			questionnaireId=questionnaireJSON.get("objectId").toString();
			updateQuestionnaire();
		}
		else {questionnaireId=addQuestionnaire();}
		JSONObject result = new JSONObject();
		result.put("questionnaireId", questionnaireId);
		ServletActionContext.getResponse().getWriter().print(result);
		return null;
	}

	public String addQuestionnaire() throws Exception {
		return questionnaireService.addQuestionnaire(questionnaire);//objectId
	}
	
	public String findAQuestionnaire() throws IOException{
		String questionnaire=questionnaireService.findQuestionnaireById(questionnaireId);
		String valid="1";
		if (questionnaire==null){valid="0";}
		JSONObject result = new JSONObject();
		result.put("valid", valid);
		result.put("questionnaire", questionnaire);
		ServletActionContext.getResponse().getWriter().print(result);
		return null;
	}
	
	public String updateQuestionnaire() throws IOException{
		//OBJtest=(questionnaireService.findQuestionnaireById("595215c837fac36943ff8f26")).toString();
		int flag=questionnaireService.updateQuestionnaire(questionnaireId,questionnaire);
		String valid="1";
		if (flag==1){valid="0";}
		JSONObject result = new JSONObject();
		result.put("valid", valid);
		ServletActionContext.getResponse().getWriter().print(result);
		return null;
	}
	
	public String saveAnAnswerPaper() throws IOException{
		if (answerPaper==null){//data not fetched, fail
			ServletActionContext.getResponse().getWriter().print('0');
			return null;
		}
		questionnaireService.addQuestionnaireResult(answerPaper, request());
		ServletActionContext.getResponse().getWriter().print('1');//success
		return null;
	}

	
	
	
	
	//helper
	public String getAnswerPaper() {
		return answerPaper;
	}

	public void setAnswerPaper(String answerPaper) {
		this.answerPaper = answerPaper;
	}

	public String getOBJtest() {
		return OBJtest;
	}

	public void setOBJtest(String oBJtest) {
		OBJtest = oBJtest;
	}

	public String getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(String questionnaire) {
		this.questionnaire = questionnaire;
	}
	
	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}



	

}
