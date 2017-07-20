package dao.Impl;

import com.mongodb.*;
import dao.QuestionnaireResultDao;
import model.QuestionnaireResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class QuestionnaireResultDaoImpl implements QuestionnaireResultDao {
    protected MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }

    public Integer save(QuestionnaireResult questionnaireResult) {
        //insert the json
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("QuestionnaireResults");
        questionnaires.insert(questionnaireResult.getQuestionnaireResultDB());
        return 1;
    }

    @Override
    public List<QuestionnaireResult> getAllQuestionnaireResultById(String id) {
        // TODO Auto-generated method stub
        if (id == null) {
            return null;
        }
        List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
        DB db = mongoTemplate.getDb();
        DBCollection questionnaires = db.getCollection("QuestionnaireResults");
        BasicDBObject query = new BasicDBObject();

        query.put("questionnaireId", id);
        DBCursor dbObj = questionnaires.find(query);
        if (dbObj == null) {
            return null;
        }
        Iterator<DBObject> it = dbObj.iterator();
        while (it.hasNext()) {
            questionnaireResults.add(new QuestionnaireResult(it.next()));
        }
        return questionnaireResults;
    }


    @Override
    public QuestionnaireResult getQuestionnaireResultById(String id) {
        if (id == null) {
            return null;
        }
        DB db = mongoTemplate.getDb();
        DBCollection questionnaireResults = db.getCollection("QuestionnaireResults");
        BasicDBObject query = new BasicDBObject();
        // TODO Auto-generated method stub
        try {
            query.put("_id", new ObjectId(id));
        } catch (Exception e) {
            return null;
        }

        DBObject resultDB = questionnaireResults.findOne(query);
        if ((resultDB) == null) {
            return null;
        }

        return new QuestionnaireResult(resultDB);
    }


}
