package action;

import java.io.IOException;

import model.Questionnaire;
import model.QuestionnaireResult;
import net.sf.json.JSONObject;
import service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.QuestionnaireService;

import javax.servlet.http.HttpServletResponse;


@RestController
public class QuestionnaireAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    @Autowired
    private QuestionnaireService questionnaireService;
    public void setQuestionnaireService(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @Autowired
    private StatisticsService statisticsService;
    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    private String OBJtest;
    private String questionnaireId;
    private String questionnaire;
    private String answerPaper;
    private int status;

    @RequestMapping(value = "questionnaire", method = RequestMethod.POST)
    public String addOrUpdateQuestionnaire(String questionnaire, HttpServletResponse response) throws Exception {
        System.out.print(questionnaire);
        questionnaireId = questionnaireService.addOrUpdateQuestionnaire(new Questionnaire(questionnaire));
        JSONObject result = new JSONObject();
        result.put("questionnaireId", questionnaireId);
        response.getWriter().print(result);
        System.out.print(questionnaire);
        return null;
    }

    @RequestMapping(value = "questionnaire/{questionnaireId}", method = RequestMethod.GET)
    public String findAQuestionnaire(@PathVariable("questionnaireId") String questionnaireId, HttpServletResponse response) throws IOException {
        //questionnaireId="5954b29d37fac38fdc65727c";
        String valid = "1";
        String questionnairestr = null;
        if (questionnaireId == null) {
            valid = "0";
        }
        if (questionnaireService.findQuestionnaireById(questionnaireId) == null) {
            valid = "0";
        } else {
            questionnairestr = questionnaireService.findQuestionnaireById(questionnaireId).getQuestionnaire();
        }

        JSONObject result = new JSONObject();
        result.put("valid", valid);
        result.put("questionnaire", questionnairestr);
        response.getWriter().print(result);
        return null;
    }

    @RequestMapping(value = "questionnaire/{questionnaireId}", method = RequestMethod.DELETE)
    public String deleteQuestionnaire(@PathVariable("questionnaireId") String questionnaireId) {
        // TODO delete a questionnaire
        return null;
    }

    @RequestMapping(value = "setQuestionnaireStatus", method = RequestMethod.POST)
    public String setQuestionnaireStatus(int status, String questionnaireId, HttpServletResponse response) throws IOException {
        String valid = "1";
        if (questionnaireId == null) {
            valid = "0";
        }
        Questionnaire questionnaireS = questionnaireService.findQuestionnaireById(questionnaireId);
        if (questionnaireS == null) {
            valid = "0";
        } else {
            questionnaireS.setStatus(status);
            questionnaireService.addOrUpdateQuestionnaire(questionnaireS);
        }
        response.getWriter().print(valid);
        return null;
    }

    @RequestMapping(value = "answerPaper", method = RequestMethod.POST)
    public String addQuestionnaireResult(String answerPaper, HttpServletResponse response) throws IOException {
        if (answerPaper == null) {//data not fetched, fail
            response.getWriter().print('0');
            return null;
        }
        questionnaireService.addQuestionnaireResult(new QuestionnaireResult(answerPaper, request()));
        response.getWriter().print('1');//success
        return null;
    }

    public String exel() throws IOException{
        Questionnaire questionnaire=questionnaireService.findQuestionnaireById("595b4f2adac1e11c3b16d59f");
        statisticsService.exportToEXEL("595b4f2adac1e11c3b16d59f", "/Users/JimLiu/Desktop/exel.xls");
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

    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


}
