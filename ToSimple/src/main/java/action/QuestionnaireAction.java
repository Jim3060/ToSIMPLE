package action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import service.QuestionnaireService;

public class QuestionnaireAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private QuestionnaireService questionnaireService;
	public void setQuestionnaireService(QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}
	
	
	private String OBJtest;
	
	private String answerPaper;
	
	

	public String addQuestionnaire() throws Exception {
		//get users
		questionnaireService.addQuestionnaire(null);
		return SUCCESS;
	}
	
	public String findAQuestionnaire() throws IOException{
		OBJtest=(questionnaireService.findQuestionnaireById("595215c837fac36943ff8f26")).toString();
		//ServletActionContext.getResponse().getWriter().print(questionnaireService.findQuestionnaireById("595215c837fac36943ff8f26"));
		return SUCCESS;
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
}
