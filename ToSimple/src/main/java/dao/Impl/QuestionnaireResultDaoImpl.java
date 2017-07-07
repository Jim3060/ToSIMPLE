package dao.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.Questionnaire;
import model.QuestionnaireResult;
import net.sf.json.JSONObject;


public class QuestionnaireResultDaoImpl implements QuestionnaireResultDao {
	protected MongoTemplate mongoTemplate;

    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }
    
    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
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
		if (id==null){return null;}
		List<QuestionnaireResult> questionnaireResults=new ArrayList<QuestionnaireResult>();
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("QuestionnaireResults");
		BasicDBObject query = new BasicDBObject();
		
		query.put("questionnaireId", id);
	    DBCursor dbObj = questionnaires.find(query);
	    if (dbObj==null){return null;}
	    Iterator<DBObject> it=dbObj.iterator();
	    while(it.hasNext()){
	    	questionnaireResults.add(new QuestionnaireResult(it.next()));
	    }
		return questionnaireResults;
	}


	@Override
	public QuestionnaireResult getQuestionnaireResultById(String id) {
		if (id==null){return null;}
		DB db = mongoTemplate.getDb();
		DBCollection questionnaireResults = db.getCollection("QuestionnaireResults");
		BasicDBObject query = new BasicDBObject();
		// TODO Auto-generated method stub
		try {
            query.put("_id", new ObjectId(id));
        } catch (Exception e) {
            return null;
        }
		
		DBObject resultDB=questionnaireResults.findOne(query);
		if ((resultDB) == null) {
            return null;
        }

		return new QuestionnaireResult(resultDB);
	}

	


}
