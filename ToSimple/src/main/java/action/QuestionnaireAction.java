package action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import service.QuestionnaireService;

public class QuestionnaireAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private QuestionnaireService questionnaireService;
	public void setQuestionnaireService(QuestionnaireService questionnaireService) {
		this.questionnaireService = questionnaireService;
	}
	
	
	private String OBJtest;
	
	public String getOBJtest() {
		return OBJtest;
	}

	public void setOBJtest(String oBJtest) {
		OBJtest = oBJtest;
	}

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

}
