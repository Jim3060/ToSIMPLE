package model;

import action.SecurityInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import dao.Impl.QuestionnaireDaoImpl;
import dao.ReportDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:springmvc.xml"})
public class test {

    @Autowired
    QuestionnaireDaoImpl questionnaireDao;

    @Autowired
    ReportDao reportDao;

    @Autowired
    MongoTemplate mongoTemplate;

    public static void main(String[] args) throws IOException {

        String jsontest = "{'_id':{'$oid':'5959b6badac1e1082a93c51d'},'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0,'questionnaireId':'5959b6badac1e1082a93c51d'}";
        Gson gson = new GsonBuilder().create();
        QuestionnaireGSON p = gson.fromJson(jsontest, QuestionnaireGSON.class);
        System.out.println(p);

    }

    @Test
    public void test() throws IOException {

//        String jsontest = "{'_id':{'$oid':'5959b6badac1e1082a93c51d'},'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0,'questionnaireId':'5959b6badac1e1082a93c51d'}";
//        Gson gson = new GsonBuilder().create();
//        QuestionnaireGSON p = gson.fromJson(jsontest, QuestionnaireGSON.class);
//        System.out.println(p);
        System.out.print(this.questionnaireDao.delete("595b3952074a1315339609ec"));
//        DB db = mongoTemplate.getDb();
//        DBCollection questionnaires = db.getCollection("Questionnaires");
//        BasicDBObject query = new BasicDBObject();
//        try {
//            query.put("_id", new ObjectId("595b3ded074a13192cd86690"));
//        } catch (Exception e) {
//            return ;
//        }
////        DBObject object = questionnaires.findOne(query);
//        DBObject object = questionnaires.findAndRemove(query);
////        System.out.print(object.get("questions"));
    }

    @Test
    public void dao() throws IOException {
//        DB db = mongoTemplate.getDb();
//        DBCollection dbCollection = db.getCollection("Questionnaires");
//        DBObject query = new BasicDBObject();
//        Pattern john = Pattern.compile("问卷", CASE_INSENSITIVE);
////        query.put("status", new BasicDBObject("$gt", "1"));
//        query.put("paperTitle", john);
//        DBCursor dbCursor = dbCollection.find(query);
//
//        while (dbCursor.hasNext()){
//            System.out.print(dbCursor.next());
//        }
//        List<Questionnaire> list = questionnaireDao.findQuestionnaireByStatus(1);
////        System.out.print(list);
//        Iterator<Questionnaire> iterator = list.iterator();
//        while (iterator.hasNext()){
//            System.out.print(iterator.next().questionnaireJSON);
//        }
//        questionnaireDao.delete("595c8504074a131e1d0fe7fd");

//        DB db = mongoTemplate.getDb();
//        DBCollection questionnaires = db.getCollection("Questionnaires");
//        BasicDBObject query = new BasicDBObject();
//        DBCursor dbCursor = questionnaires.find().limit(2);
//        List<Questionnaire> list = new ArrayList<Questionnaire>();
//        while (dbCursor.hasNext()) {
//            list.add(new Questionnaire(dbCursor.next()));
//        }


        String name = "";
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        Pattern regName = Pattern.compile(name, CASE_INSENSITIVE);
        query.put("status", new BasicDBObject("$eq", 1));
        query.put("paperTitle", regName);
        BasicDBObject fields = new BasicDBObject("paperTitle", true).append("_id", true);
        DBCursor dbCursor = questionnaires.find(query, fields);


        while (dbCursor.hasNext()) {
            DBObject o = dbCursor.next();
            System.out.println(o.toString());
        }

    }

    @Test
    public void testRandom() {
        List<Questionnaire> list = questionnaireDao.randomQuestionnaire(3);
        Iterator<Questionnaire> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().questionnaireJSON.toString());
        }
//        DB db = mongoTemplate.getDb();
//        DBCollection questionnaires = db.getCollection("Questionnaires");
//        BasicDBObject sample = new BasicDBObject("$sample", new BasicDBObject("size", 2));
//        BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("status", 1));
//        BasicDBObject project = new BasicDBObject("$project", new BasicDBObject("paperTitle", true));
////        ProjectionOperation projectionOperation = Aggregation.project(String.valueOf(Projections.fields(Projections.include("_id"),
////                Projections.include("paperTitle"))));
//        List<BasicDBObject> li = new LinkedList<>();
//        li.add(match);
//        li.add(sample);
//        li.add(project);
////        li.add(projectionOperation);
//        AggregationOutput aggregationOutput = questionnaires.aggregate(li);
////        AggregationOutput aggregationOutput = questionnaires.aggregate(Arrays.asList(
////                new Bson[]{Aggregates.match(Filters.eq("categories", "Bakery"))},
////                Aggregates.group("$stars", Accumulators.sum("count", 1))));
//        Iterable<DBObject> result = aggregationOutput.results();
//        Iterator it = result.iterator();
//        while (it.hasNext()) {
//            System.out.print(it.next());
////            list.add(new Questionnaire(it.next()));
//        }
    }

    @Test
    public void testBackUp() throws IOException {
        Properties prop = new Properties();
        File file = new File("setting.properties");
        InputStream in = getClass().getResourceAsStream("/setting.properties");
        prop.load(in);
        System.out.print(prop.get("spring.data.mongodb.host"));
    }

    @Test
    public void testYM() {
        StringBuffer stringBuffer = new StringBuffer("Hello");
//        Integer i = 20;
        System.out.print(stringBuffer.toString());
        change(stringBuffer);
        System.out.print(stringBuffer.toString());

        Integer i = 20;
        System.out.print(i);
        change1(i);
        System.out.print(i);

        INT k = new INT(5);
        System.out.print(k.getI());
        change3(k);
        System.out.print(k.getI());


    }

    private void change3(INT i) {
        i.setI(30);
    }

    private void change(StringBuffer stringBuffer) {
        stringBuffer.append("world");
    }

    private void change1(Integer i) {
        i += 5;
    }

    @Test
    public void testReport() {
//        List<Report> list = reportDao.getAllUnhandledReports(new Integer(0),new Integer(30),new Integer(3));
//        Iterator<Report> iterator = list.iterator();
//        while (iterator.hasNext()){
//            System.out.print(iterator.next().getContent());
//        }
    }

    @Test
    public void testIn() {
        final String[] userUrls = new String[]{
                "admin", "yyk"
        };
        List<String> list1 = Arrays.asList(userUrls);

        List<String> list = new LinkedList<String>() {{
            add("ad");
            add("sdf");
        }};
        ;
        Assert.assertEquals(true, list1.contains("admin"));
//        Integer i = 1;
//        boolean b = (i ==1);
//        System.out.print(b);
//        Assert.assertEquals(true,(i == 1));
    }

    @Test
    public void testSec() {
        SecurityInterceptor securityInterceptor = new SecurityInterceptor();
        System.out.print(securityInterceptor.needRole("allUsers", "GET"));
        System.out.print(securityInterceptor.needRole("registerValidate", "GET"));
    }

    class INT {
        private Integer i;

        public INT(Integer i) {
            this.i = i;
        }

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }
    }


}
