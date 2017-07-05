package dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import dao.QuestionnaireDao;
import model.Questionnaire;
import net.sf.json.JSONObject;


public class QuestionnaireDaoImpl implements QuestionnaireDao {
	protected MongoTemplate mongoTemplate;

    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }
    
    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }
    
	public String save(Questionnaire questionnaire) {
		//insert the json
		DBObject questionnaireDB=questionnaire.getQuestionnaireDB();
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		questionnaires.insert(questionnaireDB);
		ObjectId id = (ObjectId)questionnaireDB.get( "_id" );
		return id.toString();
	}

	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		BasicDBObject query = new BasicDBObject();
		try{
			query.put("_id", new ObjectId(id));
		}catch(Exception e){
			return null;
		}
		questionnaires.remove(query);
		return id;
	}

	@Override
	public String update(String id, Questionnaire questionnaire) {
		DBObject questionnaireDB=questionnaire.getQuestionnaireDB();
		DBObject dbObj;
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));   
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		if ((dbObj=questionnaires.findOne(query))==null){return null;}
		questionnaires.update(query, questionnaireDB);
		// TODO Auto-generated method stub
		ObjectId newid = (ObjectId)dbObj.get( "_id" );
		return newid.toString();
	}

	@Override
	public Questionnaire findQuestionnaireById(String id) {
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		BasicDBObject query = new BasicDBObject();
		try{
			query.put("_id", new ObjectId(id));
		}catch(Exception e){
			return null;
		}
	    DBObject dbObj = questionnaires.findOne(query);
	    if (dbObj==null){return null;}
		// TODO Auto-generated method stub
		return new Questionnaire(dbObj);
	}

	@Override
	public Questionnaire findQuestionnaireByUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
