package action;

import ToolUtils.CountUtils;
import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.QuestionnaireService;
import service.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


@RestController
public class QuestionnaireAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private StatisticsService statisticsService;
    private String OBJtest;
    private String questionnaireId;
    private String questionnaire;
    private String answerPaper;
    private int status;

    public void setQuestionnaireService(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    public void setStatisticsService(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Save or update the questionnaire.
     *
     * @param questionnaire the questionnaire to be saved
     * @param response      the response to be returned
     * @return none
     * @throws Exception
     */
    @RequestMapping(value = "questionnaire", method = {RequestMethod.POST, RequestMethod.PUT})
    public String addOrUpdateQuestionnaire(String questionnaire, HttpSession session, HttpServletResponse response) throws Exception {
        System.out.print(questionnaire);
        if (session.getAttribute("user") == null) {
            JSONObject result = new JSONObject();
            result.put("valid", 0);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().print(result);
            return null;
        }

        questionnaireId = questionnaireService.addOrUpdateQuestionnaire(new Questionnaire(questionnaire, ((User) session.getAttribute("user")).getId()));
        JSONObject result = new JSONObject();
        if (questionnaireId == null) {
            result.put("valid", -1);
        } else {
            result.put("valid", 1);
        }
        result.put("valid", 1);
        result.put("questionnaireId", questionnaireId);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(result);

        System.out.print(questionnaire);
        return null;
    }


    @RequestMapping(value = "questionnaire/{questionnaireId}", method = RequestMethod.POST)
    public String addOrUpdateQuestionnaire(String questionnaire, HttpSession session, HttpServletResponse response, @PathVariable("questionnaireId") String questionnaireId) throws Exception {
        //check for author
        System.out.print(questionnaire);
        if (session.getAttribute("user") == null) {
            JSONObject result = new JSONObject();
            result.put("valid", 0);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().print(result);
            return null;
        }


        //check user equals author
        Questionnaire questionnaireTest = new Questionnaire(questionnaire);

        if (questionnaireTest.questionnaireJSON.has("authorId") && !(String.valueOf(questionnaireTest.questionnaireJSON.get("authorId")).equals(String.valueOf(((User) session.getAttribute("user")).getId())))) {
            JSONObject result = new JSONObject();
            result.put("valid", 0);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().print(result);
            return null;
        }
        questionnaireId = questionnaireService.addOrUpdateQuestionnaire(new Questionnaire(questionnaire, ((User) session.getAttribute("user")).getId()));

        JSONObject result = new JSONObject();
        if (questionnaireId == null) {
            result.put("valid", -1);
        } else {
            result.put("valid", 1);
        }
        result.put("questionnaireId", questionnaireId);
        response.getWriter().print(result);
        return null;
    }

    //associateQuestionnaires?questionnaireId1={id1}&questionnaireId2={id2}&message={message}
    //{id1}:主人的问卷id {id2}:被挂在后面的问卷id {message}:连接语
    @RequestMapping(value = "associateQuestionnaires", method =  RequestMethod.GET)
    public void associateQuestionnaires(HttpSession session, HttpServletResponse response,String questionnaireId1,String questionnaireId2, String message) throws IOException{
    	response.setContentType("application/json;charset=UTF-8");
    	int i=questionnaireService.associateQuestionnaires(questionnaireId1, questionnaireId2,message,(User)session.getAttribute("user"));
    	response.getWriter().print(i);
    }

    //breakAssociation?questionnaireId1={id1}&questionnaireId2={id2}
    //{id1}:主人的问卷id {id2}:被挂在后面的问卷id
    @RequestMapping(value = "breakAssociation", method =  RequestMethod.GET)
    public void breakAssociation(HttpSession session, HttpServletResponse response,String questionnaireId1,String questionnaireId2) throws IOException{
    	response.setContentType("application/json;charset=UTF-8");
    	int i=questionnaireService.breakAssociation(questionnaireId1, questionnaireId2,(User)session.getAttribute("user"));
    	response.getWriter().print(i);
    }

    @RequestMapping(value = "getAllAssociation", method =  RequestMethod.GET)
    public void getAllAssociation(HttpSession session, HttpServletResponse response,String questionnaireId) throws IOException{
    	response.setContentType("application/json;charset=UTF-8");
    	List<Questionnaire> list=questionnaireService.getAllAssociatedQuestionnaires(questionnaireId);
    	if (list==null){
    		JSONObject jsonObject = new JSONObject();
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return ;
    	}

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", 1);
        jsonObject.put("questionnaires", list);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping(value = "getOneAssociation", method =  RequestMethod.GET)
    public void getOneAssociation(HttpSession session, HttpServletResponse response,String questionnaireId) throws IOException{
    	response.setContentType("application/json;charset=UTF-8");
    	Questionnaire q=questionnaireService.getOneAssociatedQuestionnaire(questionnaireId);
    	if (q==null){
    		JSONObject jsonObject = new JSONObject();
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return ;
    	}

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", 1);
        jsonObject.put("questionnaire", q);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping(value = "getOneAssociationInfo", method =  RequestMethod.GET)
    public void getOneAssociationInfo(HttpSession session, HttpServletResponse response,String questionnaireId) throws IOException{
    	response.setContentType("application/json;charset=UTF-8");
    	Questionnaire.Association a=questionnaireService.getOneAssociatedQuestionnaireInfo(questionnaireId);
    	if (a==null){
    		JSONObject jsonObject = new JSONObject();
            jsonObject.put("valid", 0);
            response.getWriter().print(jsonObject);
            return ;
    	}

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("valid", 1);
        jsonObject.put("questionnaireAssociation", a);
        response.getWriter().print(jsonObject);
    }

    @RequestMapping(value = "questionnaireSojump/{questionnaireId}", method =  RequestMethod.POST)
    public String forkSojumpQuestionnaire( HttpSession session,HttpServletResponse response, @PathVariable("questionnaireId") String questionnaireId) throws Exception {
    	response.setContentType("application/json;charset=UTF-8");
    	int flag=questionnaireService.saveSojumpQuestionnaire(questionnaireId);
    	JSONObject result = new JSONObject();
    	result.put("valid", flag);
        response.getWriter().print(result);
//    	SojumpParser parser=new SojumpParser("https://sojump.com/jq/"+"467815"+".aspx");
//    	questionnaireService.addOrUpdateQuestionnaire(QuestionnaireSpider.toQuestionnaire(parser.parseSojump()));
		return null;
    }
    
    @RequestMapping(value = "questionnaireSojump/{questionnaireId}", method =  RequestMethod.GET)
    public String getSojumpQuestionnaire( HttpSession session,HttpServletResponse response, @PathVariable("questionnaireId") String questionnaireId) throws Exception {
    	response.setContentType("application/json;charset=UTF-8");
    	Questionnaire q=questionnaireService.getSojumpQuestionnaire(questionnaireId);
    	if (q==null){
    		JSONObject result = new JSONObject();
    		result.put("valid", 0);
    		response.getWriter().print(result);
    		return null;
    	}
    	JSONObject result = new JSONObject();
    	result.put("valid", 1);
    	result.put("questionnaire", (q.questionnaireJSON));
        response.getWriter().print(result);
//    	SojumpParser parser=new SojumpParser("https://sojump.com/jq/"+"467815"+".aspx");
//    	questionnaireService.addOrUpdateQuestionnaire(QuestionnaireSpider.toQuestionnaire(parser.parseSojump()));
		return null;
    }

    @RequestMapping(value = "questionnaireSojumpKW/{keyword}", method =  RequestMethod.GET)
    public String getSojumpQuestionnaireByKeyword( HttpSession session,HttpServletResponse response, @PathVariable("keyword") String keyword) throws Exception {
    	response.setContentType("application/json;charset=UTF-8");
    	Questionnaire q=questionnaireService.getQuestionnaireByKW(keyword);
    	if (q==null){
    		JSONObject result = new JSONObject();
    		result.put("valid", 0);
    		response.getWriter().print(result);
    		return null;
    	}
    	JSONObject result = new JSONObject();
    	result.put("valid", 1);
    	result.put("questionnaire", (q.questionnaireJSON));
        response.getWriter().print(result);
//    	SojumpParser parser=new SojumpParser("https://sojump.com/jq/"+"467815"+".aspx");
//    	questionnaireService.addOrUpdateQuestionnaire(QuestionnaireSpider.toQuestionnaire(parser.parseSojump()));
		return null;
    }
    
    @RequestMapping(value = "questionSojumpKW/{keyword}", method =  RequestMethod.GET)
    public String getSojumpQuestionByKeyword( HttpSession session,HttpServletResponse response, @PathVariable("keyword") String keyword) throws Exception {
    	response.setContentType("application/json;charset=UTF-8");
    	String q;
    	String [] dataStr = keyword.split("-"); 
    	System.out.println(dataStr[0]);
    	if (dataStr[0].equals(keyword)){q=questionnaireService.getQuestionByKW(keyword,keyword);}
    	else{
    		q=questionnaireService.getQuestionByKW(dataStr[0],dataStr[1]);
    		
    	}
    	if (q==null){
    		JSONObject result = new JSONObject();
    		result.put("valid", 0);
    		response.getWriter().print(result);
    		return null;
    	}
    	JSONObject result = new JSONObject();
    	result.put("valid", 1);
    	result.put("question", (q));
        response.getWriter().print(result);
//    	SojumpParser parser=new SojumpParser("https://sojump.com/jq/"+"467815"+".aspx");
//    	questionnaireService.addOrUpdateQuestionnaire(QuestionnaireSpider.toQuestionnaire(parser.parseSojump()));
		return null;
    }


    /**
     * Get a questionnaire of the specific questionnaireId
     *
     * @param questionnaireId
     * @param response        the response to be returned
     * @return none
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "questionnaire/{questionnaireId}", method = RequestMethod.GET)
    public String findAQuestionnaire(@PathVariable("questionnaireId") String questionnaireId, HttpServletResponse response) throws IOException, ParseException {

        String valid = "1";
        response.setContentType("application/json;charset=UTF-8");
        String questionnairestr = null;
        if (questionnaireId == null) {valid = "0";}
        Questionnaire questionnaireGet=questionnaireService.findQuestionnaireById(questionnaireId);
        if (questionnaireGet == null) { valid = "0"; }
        else {
            questionnairestr = questionnaireGet.getQuestionnaire();
        }

        //if (questionnaireService.checkQuestionnaireInTime(questionnaireGet)==0){valid="2";}

        JSONObject result = new JSONObject();
        result.put("valid", valid);
        if (valid=="1"){
        	result.put("questionnaire", questionnairestr);
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(result);
        return null;
    }

    /**
     * get all the questionanres by page.max pageSize is 30.
     *
     * @param page
     * @param pageSize
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "allQuestionnaire", method = RequestMethod.GET)
    public void fetchAllQuestionnaire(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        CountUtils countUtils = new CountUtils(0);
        List<Questionnaire> list = questionnaireService.fetchAll(page, pageSize, countUtils);
        JSONArray jsonArray = toJSONArray(list);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", jsonArray);
        jsonObject.put("count", countUtils.getCount());
        response.getWriter().print(jsonObject);
        return;
    }


    @RequestMapping(value = "allQuestionnaireFullInfo", method = RequestMethod.GET)
    public void fetchAllQuestionnaireFullInfo(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        CountUtils countUtils = new CountUtils(0);
        List<Questionnaire> list = questionnaireService.fetchAllWithInfo(page, pageSize, countUtils);
        JSONArray jsonArray = toJSONArray(list);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", jsonArray);
        jsonObject.put("count", countUtils.getCount());
        response.getWriter().print(jsonObject);
        return;
    }

    @RequestMapping(value = "questionnaire", method = RequestMethod.GET)
    public String findQuestionnairesByUser(HttpServletResponse response, HttpSession session) throws IOException {
        //questionnaireId="5954b29d37fac38fdc65727c";
        int valid = 1;
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        JSONArray listJ = new JSONArray();
        if (session.getAttribute("user") == null) {
            valid = 0;
        } else if ((list = questionnaireService.findQuestionnairesByUser(((User) session.getAttribute("user")).getId())) == null) {
            valid = 0;
        }
        for (int i = 0; i < list.size(); i++) {
            listJ.add(list.get(i).questionnaireJSON);
        }
        JSONObject result = new JSONObject();
        result.put("valid", valid);
        result.put("questionnaires", listJ);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(result);
        return null;
    }

    /**
     * delete a questionnaire of the specific questionnaireId
     *
     * @param questionnaireId
     * @return none
     */
    @RequestMapping(value = "questionnaire/{questionnaireId}", method = RequestMethod.DELETE)
    public String deleteQuestionnaire(@PathVariable("questionnaireId") String questionnaireId,
                                      HttpServletResponse response,HttpSession session) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        //check user equals author
        Questionnaire questionnaireTest = questionnaireService.findQuestionnaireById(questionnaireId);
       // System.out.println("authorId:"+questionnaireTest.questionnaireJSON.get("authorId"));
        User user=(User) session.getAttribute("user");
        if (session.getAttribute("user")==null||questionnaireTest.questionnaireJSON.has("authorId") && user.getRole()!=1 && !(String.valueOf(questionnaireTest.questionnaireJSON.get("authorId")).equals(String.valueOf(((User) session.getAttribute("user")).getId())))) {
            JSONObject result = new JSONObject();
            result.put("valid", 0);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().print(result);
            return null;
        }
        Integer integer = questionnaireService.deleteQuestionnaire(questionnaireId);
        JSONObject result = new JSONObject();
        result.put("deleteSuccess", integer);
        response.getWriter().print(result);
        return null;
    }

    /**
     * search questionnaires
     *
     * @param name
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "questionnaire/search", method = RequestMethod.GET)
    public String searchQuestionnaireByName(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize, @RequestParam("name") String name, HttpServletResponse response) throws IOException {
//        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        List<Questionnaire> list = new LinkedList<>();
        CountUtils countUtils = new CountUtils(0);
        list = questionnaireService.searchQuestionnaireByName(page, pageSize, name, countUtils);
        JSONArray jsonArray = toJSONArray(list);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", jsonArray);
        jsonObject.put("count", countUtils.getCount());
        response.getWriter().print(jsonObject);
        return null;
    }

    @RequestMapping(value = "questionnaire/random", method = RequestMethod.GET)
    public String randomQuestionnaire(@RequestParam(value = "size", defaultValue = "30") Integer size, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<Questionnaire> list = questionnaireService.randomQuestionnaire(size);
        JSONArray jsonArray = toJSONArray(list);
        response.getWriter().print(jsonArray);
        Iterator<Questionnaire> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().questionnaireJSON.toString());
        }
        return null;
    }

    /**
     * get questionnaire with specific status
     *
     * @param status
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "questionnaire/status", method = RequestMethod.GET)
    public String getQuestionnaireByStatus(@RequestParam("status") Integer status, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        CountUtils countUtils = new CountUtils(0);
        List<Questionnaire> list = questionnaireService.findQuestionnaireByStatus(status, countUtils);
        JSONArray jsonArray = toJSONArray(list);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("items", jsonArray);
        jsonObject.put("count", countUtils.getCount());
        response.getWriter().print(jsonObject);
        return null;
    }

    private JSONArray toJSONArray(List<Questionnaire> list) {
        JSONArray jsonArray = new JSONArray();
        Iterator<Questionnaire> iterator = list.iterator();
        while (iterator.hasNext()) {
            jsonArray.add(iterator.next().questionnaireJSON);
        }
        return jsonArray;
    }

    /**
     * set the status of questionnaire to @param status
     *
     * @param status
     * @param questionnaireId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "setQuestionnaireStatus", method = RequestMethod.POST)
    public String setQuestionnaireStatus(Integer status, String questionnaireId, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String valid = "1";
        if (status > 3 || status < 0) {
            valid = "0";
            response.getWriter().print(valid);
            return null;
        }
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


    /**
     * Save a result of the questionnaire
     *
     * @param answerPaper
     * @param response
     * @param request
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "questionnaireResult", method = RequestMethod.POST)
    public String addQuestionnaireResult(String answerPaper, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (answerPaper == null) {//data not fetched, fail
            response.getWriter().print('0');
            return null;
        }
        //check in duration
        QuestionnaireResult questionnaireResult=new QuestionnaireResult(answerPaper, request);
        Questionnaire questionnaireTmp=questionnaireService.findQuestionnaireById((String)(questionnaireResult.questionnaireResultJSON.get("questionnaireId")));
        if (questionnaireService.checkQuestionnaireInTime(questionnaireTmp)==0){
        	response.getWriter().print('2');
            return null;
        }
        questionnaireService.addQuestionnaireResult(questionnaireResult);
        response.getWriter().print('1');//success
        return null;
    }


    /**
     * show a questionnaire result.
     *
     * @param questionnaireResultId
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "questionnaireResult/{questionnaireResultId}", method = RequestMethod.GET)
    public String show(@PathVariable("questionnaireResultId") String questionnaireResultId,HttpServletResponse response) throws IOException {
    	response.setContentType("application/json;charset=UTF-8");
    	QuestionnaireResult questionnaireResult=questionnaireService.getQuestionnaireResultByid(questionnaireResultId);
    	if (questionnaireResult==null){
    		JSONObject result = new JSONObject();
            result.put("valid", 0);
            response.getWriter().print(result);
            return  null;
    	}
    	JSONObject result = new JSONObject();
        result.put("valid", 1);
        result.put("questionnaireResult", questionnaireResult.questionnaireResultJSON.toString());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print(result);
        return null;
    }

    /**
     * download a questionnaire.
     *
     * @param questionnaireId
     * @param response
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "questionnaireResult/download/{questionnaireId}", method = RequestMethod.GET)
    public String statisticsDown(@PathVariable("questionnaireId") String questionnaireId, HttpServletResponse response) throws IOException, ParseException {
        //Questionnaire questionnaire=questionnaireService.findQuestionnaireById(questionnaireId);
        response.setContentType("application/json;charset=UTF-8");
        HSSFWorkbook wb = statisticsService.exportToEXEL(questionnaireId);
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("statistics.xls", "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
        wb.write(out);
        out.flush();
        out.close();
        return null;
    }
    
    @RequestMapping(value = "questionnaireStatistics/chart/{questionnaireId}", method = RequestMethod.GET)
    public String ChartDown(@PathVariable("questionnaireId") String questionnaireId, HttpServletResponse response) throws IOException, ParseException {
        //Questionnaire questionnaire=questionnaireService.findQuestionnaireById(questionnaireId);
        response.setContentType("application/json;charset=UTF-8");
        HSSFWorkbook wb = statisticsService.exportChartToEXEL(questionnaireId);
        if (wb==null){System.out.println("error");}
        OutputStream out = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("statistics.xls", "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
        wb.write(out);
        out.flush();
        out.close();
        return null;
    }


    @RequestMapping(value = "questionnaireStatistics/{questionnaireId}", method = RequestMethod.GET)
    public String getStatisticsById(@PathVariable("questionnaireId") String questionnaireId, HttpServletResponse response) throws IOException {
        //Questionnaire questionnaire=questionnaireService.findQuestionnaireById(questionnaireId);
        response.setContentType("application/json;charset=UTF-8");
        QuestionnaireStatistics s = statisticsService.getQuestionnaireStatisticsById(questionnaireId);
        JSONObject result = new JSONObject();
        result.put("questionStatistics", s.getQuestionsJSON());
        result.put("answerNumber", s.questionnaireResults.size());
        response.getWriter().print(result);
        return null;
    }

    @RequestMapping(value = "questionnaireReported", method = RequestMethod.GET)
    public String getReportedQuestionnaire(HttpServletResponse response) throws IOException {
        //Questionnaire questionnaire=questionnaireService.findQuestionnaireById(questionnaireId);
        response.setContentType("application/json;charset=UTF-8");
        List<Questionnaire> questionnaires = questionnaireService.getReportedQuestionnaire();
        JSONArray jsonArray = toJSONArray(questionnaires);
        JSONObject result = new JSONObject();
        result.put("questionnaires", jsonArray);
        //result.put("answerNumber", s.questionnaireResults.size());
        response.getWriter().print(result);
        return null;
    }

    @RequestMapping(value = "questionnaireReportedPaged", method = RequestMethod.GET)
    public String getReportedQuestionnaire(HttpServletResponse response, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) throws IOException {
        //Questionnaire questionnaire=questionnaireService.findQuestionnaireById(questionnaireId);
        response.setContentType("application/json;charset=UTF-8");
        CountUtils countUtils = new CountUtils(0);
        List<Questionnaire> questionnaires = questionnaireService.getReportedQuestionnaireByPage(page, pageSize, countUtils);
        JSONArray jsonArray = toJSONArray(questionnaires);
        JSONObject result = new JSONObject();
        result.put("questionnaires", jsonArray);
        result.put("questionnaireNum", countUtils.getCount());
        //result.put("answerNumber", s.questionnaireResults.size());
        response.getWriter().print(result);
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