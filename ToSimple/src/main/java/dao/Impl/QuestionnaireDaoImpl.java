package dao.Impl;

import ToolUtils.CountUtils;
import com.mongodb.*;
import dao.QuestionnaireDao;
import model.Questionnaire;
import model.Report;
import org.bson.types.ObjectId;
import org.hibernate.Session;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Math.min;
import static java.util.regex.Pattern.CASE_INSENSITIVE;


public class QuestionnaireDaoImpl extends HibernateDaoSupport implements QuestionnaireDao {
    protected MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }

    public String save(Questionnaire questionnaire) {
        //insert the json
        DBObject questionnaireDB = questionnaire.getQuestionnaireDB();
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        questionnaires.insert(questionnaireDB);
        ObjectId id = (ObjectId) questionnaireDB.get("_id");
        return id.toString();
    }


    @Override
    public Integer delete(String id) {
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        try {
            query.put("_id", new ObjectId(id));
        } catch (Exception e) {
            return 0;
        }

        if ((questionnaires.findOne(query)) == null) {
            return 0;
        }

        DBObject object = questionnaires.findAndRemove(query);
        return (object == null) ? 0 : 1;

    }

    @Override
    public String update(String id, Questionnaire questionnaire) {
        DBObject questionnaireDB = questionnaire.getQuestionnaireDB();
        DBObject dbObj;
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        if ((dbObj = questionnaires.findOne(query)) == null) {
            return null;
        }
        questionnaires.update(query, questionnaireDB);
        ObjectId newid = (ObjectId) dbObj.get("_id");
        return newid.toString();
    }

    @Override
    public Questionnaire findQuestionnaireById(String id) {
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        try {
            query.put("_id", new ObjectId(id));
        } catch (Exception e) {
            return null;
        }
        DBObject dbObj = questionnaires.findOne(query);
        if (dbObj == null) {
            return null;
        }
        return new Questionnaire(dbObj);
    }

    @Override
    public List<Questionnaire> findQuestionnaireByUser(Long id) {
        // TODO Auto-generated method stub
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        query.put("authorId", id);
        System.out.println("delete here");
        DBObject orderBy = new BasicDBObject();  
        orderBy.put("createDate", -1);
        DBCursor dbCursor = questionnaires.find(query).sort(orderBy);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;
    }

    @Override
    public List<Questionnaire> searchQuestionnaireByName(Integer page, Integer pageSize, String name, CountUtils countUtils) {

        pageSize = min(pageSize, 30);
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        Pattern regName = Pattern.compile(name, CASE_INSENSITIVE);
        query.put("status", new BasicDBObject("$eq", 1));
        query.put("paperTitle", regName);
        BasicDBObject fields = new BasicDBObject("paperTitle", true).append("_id", true);
        DBCursor dbCursor = questionnaires.find(query, fields).skip(page * pageSize).limit(pageSize);
        Integer count = questionnaires.find(query).size();
        countUtils.setCount(count);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;
    }

    @Override
    public List<Questionnaire> fetchAll(Integer page, Integer pageSize, CountUtils countUtils) {
        pageSize = min(pageSize, 30);
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject("paperTitle", true).append("_id", true);
        DBCursor dbCursor = questionnaires.find(query, fields).skip(page * pageSize).limit(pageSize);
        Integer count = questionnaires.find(query).size();
        countUtils.setCount(count);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;
    }

    @Override
    public List<Questionnaire> fetchAllWithAllInfo(Integer page, Integer pageSize, CountUtils countUtils) {
        pageSize = min(pageSize, 30);
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        DBCursor dbCursor = questionnaires.find(query).skip(page * pageSize).limit(pageSize);
        Integer count = questionnaires.find(query).size();
        countUtils.setCount(count);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;
    }

    @Override
    public List<Questionnaire> randomQuestionnaire(Integer size) {
        DB db = mongoTemplate.getDb();
        List<BasicDBObject> li = new LinkedList<>();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject sample = new BasicDBObject("$sample", new BasicDBObject("size", size));
        BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("status", 1));
        BasicDBObject project = new BasicDBObject("$project", new BasicDBObject("paperTitle", true));
        li.add(match);
        li.add(sample);
        li.add(project);
        AggregationOutput aggregationOutput = questionnaires.aggregate(li);
        Iterable<DBObject> result = aggregationOutput.results();
        Iterator<DBObject> it = result.iterator();
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (it.hasNext()) {
            list.add(new Questionnaire(it.next()));
        }
        return list;
    }

    @Override
    public List<Questionnaire> searchQuestionnaireByName(String name, CountUtils countUtils) {
        return searchQuestionnaireByName(0, 30, name, countUtils);
    }


    @Override
    public List<Questionnaire> findQuestionnaireByStatus(Integer status, CountUtils countUtils) {
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        BasicDBObject query = new BasicDBObject();
        query.put("status", status);
        DBCursor dbCursor = questionnaires.find(query);
        Integer count = dbCursor.size();
        countUtils.setCount(count);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;
    }

    @Override
    public List<Questionnaire> getReportedQuestionnaire() {
        Session session = this.getSession();
        session.beginTransaction();
        List<Report> reports = session.createQuery("from Report as r where r.status=0").list();
        session.getTransaction().commit();
        Set<String> questionnaireIdSet = new HashSet<String>();
        for (int i = 0; i < reports.size(); i++) {
            questionnaireIdSet.add(reports.get(i).getQuestionnaireId());
        }
        List<ObjectId> idArray = new ArrayList<ObjectId>();

        Iterator<String> it = questionnaireIdSet.iterator();
        while (it.hasNext()) {
            idArray.add(new ObjectId(it.next()));
        }
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new BasicDBObject("$in", idArray));

        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        DBCursor dbCursor = questionnaires.find(query);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;

    }

    public List<Questionnaire> getReportedQuestionnaireByPage(int page, int pageSize, CountUtils countUtils) {
        Session session = this.getSession();
        session.beginTransaction();
        List<Report> reports = session.createQuery("from Report as r where r.status=0").list();
        session.getTransaction().commit();
        Set<String> questionnaireIdSet = new HashSet<String>();
        for (int i = 0; i < reports.size(); i++) {
            questionnaireIdSet.add(reports.get(i).getQuestionnaireId());
        }
        List<ObjectId> idArray = new ArrayList<ObjectId>();

        Iterator<String> it = questionnaireIdSet.iterator();
        while (it.hasNext()) {
            idArray.add(new ObjectId(it.next()));
        }
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new BasicDBObject("$in", idArray));

        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("Questionnaires");
        DBCursor dbCursor = questionnaires.find(query).skip(page * pageSize).limit(pageSize);
        Integer count = questionnaires.find(query).size();
        countUtils.setCount(count);
        List<Questionnaire> list = new ArrayList<Questionnaire>();
        while (dbCursor.hasNext()) {
            list.add(new Questionnaire(dbCursor.next()));
        }
        return list;
    }
}
