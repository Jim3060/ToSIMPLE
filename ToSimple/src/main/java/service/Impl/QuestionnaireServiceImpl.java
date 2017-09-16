package service.Impl;


import ToolUtils.CountUtils;
import ToolUtils.SojumpParser;
import ToolUtils.Spider.Algorithm;
import ToolUtils.SpiderUtils;
import ToolUtils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.*;
import model.Questionnaire.Association;
import net.sf.json.JSONArray;
import service.QuestionnaireService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class QuestionnaireServiceImpl implements QuestionnaireService {
    private QuestionnaireDao questionnaireDao;
    private QuestionnaireResultDao questionnaireResultDao;

    public static void main(String[] args) {

//		String questinnaireSTRING="{'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0}";
//		Questionnaire questionnaire=new Questionnaire(questinnaireSTRING);
//		String answerSTRING="{'answer':{'0':[[0],''],'1':[[1,2],''],'2':[[],'test']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T03:17:56.866Z'}";
//		String answerSTRING2="{'answer':{'0':[[1],''],'1':[[0,1],''],'2':[[],'test2']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T06:57:10.473Z'}";
//		List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING));
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING2));
//		QuestionnaireStatistics q = new QuestionnaireStatistics(questionnaire,questionnaireResults);
        String jsontest = "{'_id':{'$oid':'5959b6badac1e1082a93c51d'},'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0,'questionnaireId':'5959b6badac1e1082a93c51d'}";
        Gson gson = new GsonBuilder().create();
        QuestionnaireGSON p = gson.fromJson(jsontest, QuestionnaireGSON.class);
        System.out.println(p);
    }

    public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
        this.questionnaireDao = questionnaireDao;
    }

    public void setQuestionnaireResultDao(QuestionnaireResultDao questionnaireResultDao) {
        this.questionnaireResultDao = questionnaireResultDao;
    }

    @Override
    public Integer deleteQuestionnaire(String id) {
        System.out.println(questionnaireResultDao.deleteByQuestionnaireId(id));
        return questionnaireDao.delete(id);


    }

    @Override
    public Questionnaire findQuestionnaireById(String id) {
        return questionnaireDao.findQuestionnaireById(id);

    }

    @Override
    public QuestionnaireResult getQuestionnaireResultByid(String id) {
        return questionnaireResultDao.getQuestionnaireResultById(id);
    }

    @Override
    public Integer addQuestionnaireResult(QuestionnaireResult questionnaireResult) {
        questionnaireResultDao.save(questionnaireResult);
        //update answer number

        String questionnaireId = (String) questionnaireResult.questionnaireResultJSON.get("questionnaireId");
        System.out.println(questionnaireId);
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(questionnaireId);

        //System.out.println((int)questionnaire.questionnaireJSON.get("answerNumber"));
        questionnaire.questionnaireJSON.put("answerNumber", ((int) questionnaire.questionnaireJSON.get("answerNumber") + 1));

        questionnaireDao.update(questionnaireId, questionnaire);
        return null;
    }

    public List<Questionnaire> getReportedQuestionnaire() {
        return questionnaireDao.getReportedQuestionnaire();
    }

    @Override
    public String addOrUpdateQuestionnaire(Questionnaire questionnaire) {
        String questionnaireId;
        if (questionnaire.getQuestionnaireId().equals("")) {//add
            questionnaireId = questionnaireDao.save(questionnaire);
        } else {//update
            questionnaireId = questionnaireDao.update(questionnaire.getQuestionnaireId(), questionnaire);
        }
        return questionnaireId;
    }

    public QuestionnaireStatistics getQuestionnaireStatisticsById(String id) {
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(id);
        List<QuestionnaireResult> questionnaireResults = questionnaireResultDao.getAllQuestionnaireResultById(id);
        return new QuestionnaireStatistics(questionnaire, questionnaireResults);
    }

    @Override
    public List<Questionnaire> searchQuestionnaireByName(Integer page, Integer pageSize, String name, CountUtils countUtils) {
        return questionnaireDao.searchQuestionnaireByName(page, pageSize, name, countUtils);
    }

    @Override
    public List<Questionnaire> searchQuestionnaireByName(String name, CountUtils countUtils) {
        return questionnaireDao.searchQuestionnaireByName(name, countUtils);
    }


    @Override
    public List<Questionnaire> findQuestionnaireByStatus(Integer status, CountUtils countUtils) {
        return questionnaireDao.findQuestionnaireByStatus(status, countUtils);
    }

    @Override
    public List<Questionnaire> fetchAll(Integer page, Integer pageSize, CountUtils countUtils) {
        return questionnaireDao.fetchAll(page, pageSize, countUtils);
    }

    @Override
    public List<Questionnaire> randomQuestionnaire(Integer size) {
        return questionnaireDao.randomQuestionnaire(size);
    }

    @Override
    public List<Questionnaire> findQuestionnairesByUser(Long userid) {
        List<Questionnaire> raw = questionnaireDao.findQuestionnaireByUser(userid);
        List<Questionnaire> result = new ArrayList<Questionnaire>();
        //Questionnaire tmp=new Questionnaire();
        for (int i = 0; i < raw.size(); i++) {
            raw.get(i).questionnaireJSON.remove("questions");
            result.add(new Questionnaire(raw.get(i).getQuestionnaire()));
        }
        return result;
    }

    @Override
    public List<Questionnaire> fetchAllWithInfo(Integer page, Integer pageSize, CountUtils countUtils) {
        return questionnaireDao.fetchAllWithAllInfo(page, pageSize, countUtils);
    }

    @Override
    public List<Questionnaire> getReportedQuestionnaireByPage(int page, int pageSize, CountUtils countUtils) {

        return questionnaireDao.getReportedQuestionnaireByPage(page, pageSize, countUtils);
    }

    @Override
    public Questionnaire getSojumpQuestionnaire(String sojumpId) {
        SojumpParser parser = new SojumpParser("https://sojump.com/jq/" + sojumpId + ".aspx");
        QuestionnaireSpider q = parser.parseSojump();
        if (q == null) {
            return null;
        }
        Questionnaire result = QuestionnaireSpider.toQuestionnaire(q);
        return result;

    }

    @Override
    public int saveSojumpQuestionnaire(String sojumpId) {
        SojumpParser parser = new SojumpParser(sojumpId);
        QuestionnaireSpider q = parser.parseSojump();
        if (q == null) {
            return 0;
        }
        Questionnaire result = QuestionnaireSpider.toQuestionnaire(q);


        questionnaireDao.save(result);

        return 1;
    }

    public Questionnaire getQuestionnaireByKW(String kw) {
        List<String> ids = SpiderUtils.searchSojumpIdByKW(kw);
        if (ids == null || ids.size() == 0) {
            return null;
        }
        SojumpParser parser = new SojumpParser(ids.get(0));
        QuestionnaireSpider q = parser.parseSojump();
        return QuestionnaireSpider.toQuestionnaire(q);
    }

    @Override
    public String getQuestionByKW(String kw) {
        List<String> ids = SpiderUtils.searchSojumpIdByKW(kw);
        if (ids == null || ids.size() == 0) {
            return null;
        }
        SojumpParser parser = new SojumpParser(ids.get(0));
        QuestionnaireSpider q = parser.parseSojump();
        Gson gson = new Gson();
        QuestionnaireSpider.Question ques = q.getQuestionByKW(kw);
        if (ques == null) {
            return null;
        }
        return gson.toJson(ques);
    }

    @Override
    public String getQuestionByKW(String kwq, String kw) {
        List<String> ids = SpiderUtils.searchSojumpIdByKW(kwq);
        //ids.addAll(SpiderUtils.searchSojumpIdByKW(kw));
        if (ids == null || ids.size() == 0) {
            return null;
        }
        Comparator<QuestionnaireSpider.Question> cmps = new Comparator<QuestionnaireSpider.Question>() {
            public int compare(QuestionnaireSpider.Question o1, QuestionnaireSpider.Question o2) {
                return (Algorithm.getStringGap(o1.questionTitle, kw) - Algorithm.getStringGap(o2.questionTitle, kw) > 0) ? 1 : -1;
            }
        };
        List<QuestionnaireSpider.Question> quess = new ArrayList<QuestionnaireSpider.Question>();
        for (int i = 0; i < ids.size(); i++) {
            SojumpParser parser = new SojumpParser(ids.get(i));
            QuestionnaireSpider q = parser.parseSojump();
            if (q == null) {
                continue;
            }
            List<QuestionnaireSpider.Question> ques = q.getQuestionsByKW(kw);
            if (ques == null) {
                System.out.println("error:" + q.paperTitle);
                continue;
            }
            System.out.println("ques Size!!!:" + ques.size());
            quess.addAll(ques);
        }
        quess.sort(cmps);
        Gson gson = new Gson();

        return gson.toJson(quess);
    }

    @Override
    public Integer associateQuestionnaires(String id1, String id2, String message, User user) {//main id, slave id, message
        //identify the questionnaires
        Questionnaire q1 = questionnaireDao.findQuestionnaireById(id1);
        if (q1 == null || (int) q1.questionnaireJSON.get("status") == 2) {
            return 0;
        }
        //check the user
        if ((int) (user.getId().intValue()) != (int) q1.questionnaireJSON.get("authorId")) {
            //System.out.println("testttttttttttttttttttttttttt");
            // System.out.println(user.getId());
            //  System.out.println(q1.questionnaireJSON.get("authorId"));
            return -1;
        }
        //identify the second questionnaire
        Questionnaire q2 = questionnaireDao.findQuestionnaireById(id2);
        if (q2 == null || (int) q1.questionnaireJSON.get("status") == 2) {
            return 0;
        }
        //set associate
        q1.setAssociate(id2, message);
        //q2.setAssociate(id1);
        //save
        questionnaireDao.update(id1, q1);
        //questionnaireDao.save(q2);
        return 1;
    }


    @Override
    public Integer breakAssociation(String id1, String id2, User user) {//main id, slave id, message
        //identify the questionnaires
        Questionnaire q1 = questionnaireDao.findQuestionnaireById(id1);
        if (q1 == null) {
            return 0;
        }
        //check the user
        if (user.getId() != q1.questionnaireJSON.get("authorId")) {
            return -1;
        }
        //identify the second questionnaire
        Questionnaire q2 = questionnaireDao.findQuestionnaireById(id2);
        if (q2 == null) {
            return 0;
        }
        //set associate
        q1.removeAssociate(id2);
        //q2.setAssociate(id1);
        //save
        questionnaireDao.update(id1, q1);
        //questionnaireDao.save(q2);
        return 1;
    }

    @Override
    public List<Questionnaire> getAllAssociatedQuestionnaires(String id) {
        // TODO Auto-generated method stub
        Questionnaire q = questionnaireDao.findQuestionnaireById(id);
        if (q == null) {
            System.out.println(2345);
            return null;
        }
        JSONArray associatedIdsJ = (JSONArray) q.questionnaireJSON.get("associatedQuestionnaires");
        if (associatedIdsJ == null) {
            System.out.println(123);
            return null;
        }
        System.out.println(234);
        System.out.println(associatedIdsJ);
        List<Association> as = JSONArray.toList(associatedIdsJ, Association.class);
        List<String> ids = new ArrayList<String>();
        for (int i = 0; i < as.size(); i++) {
            ids.add(as.get(i).questionnaireId);
        }
        return questionnaireDao.findQuestionnaireByIds(ids);
    }

    @Override
    public Questionnaire getOneAssociatedQuestionnaire(String id) {
        // TODO Auto-generated method stub
        Questionnaire q = questionnaireDao.findQuestionnaireById(id);
        if (q == null) {
            System.out.println(2345);
            return null;
        }
        JSONArray associatedIdsJ = (JSONArray) q.questionnaireJSON.get("associatedQuestionnaires");
        if (associatedIdsJ == null) {
            System.out.println(123);
            return null;
        }
        System.out.println(234);
        System.out.println(associatedIdsJ);
        List<Association> qs = JSONArray.toList(associatedIdsJ, Association.class);
        //System.out.println(Math.random());
        int tmp = (int) (Math.random() * qs.size());
        System.out.println(tmp);
        return (questionnaireDao.findQuestionnaireById(qs.get(tmp).questionnaireId)).cleanDb();
    }

    @Override
    public Questionnaire.Association getOneAssociatedQuestionnaireInfo(String id) {
        // TODO Auto-generated method stub
        Questionnaire q = questionnaireDao.findQuestionnaireById(id);
        if (q == null) {
            System.out.println(2345);
            return null;
        }
        JSONArray associatedIdsJ = (JSONArray) q.questionnaireJSON.get("associatedQuestionnaires");
        if (associatedIdsJ == null) {
            System.out.println(123);
            return null;
        }
        System.out.println(234);
        System.out.println(associatedIdsJ);
        List<Association> qs = JSONArray.toList(associatedIdsJ, Association.class);
        //System.out.println(Math.random());
        int tmp = (int) (Math.random() * qs.size());
        System.out.println(tmp);
        return qs.get(tmp);
    }

    public int checkQuestionnaireInTime(Questionnaire questionnaire) throws ParseException {
        if (questionnaire.questionnaireJSON.get("startDate") == null || questionnaire.questionnaireJSON.get("startDate").equals("null")) {
            return 1;
        }
        String startDateStr = (String) questionnaire.questionnaireJSON.get("startDate");

        Date startDate = TimeUtils.getLocalTime(startDateStr);
        if (startDate == null) {
            return 1;
        }
        Date endDate = TimeUtils.getLocalTime((String) questionnaire.questionnaireJSON.get("endDate"));
        Date now = new Date();
        if (endDate.getTime() >= now.getTime() && startDate.getTime() <= now.getTime()) {
            return 1;
        }
        return 0;
    }

}