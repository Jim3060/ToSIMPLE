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
import net.sf.json.JSONObject;


public class QuestionnaireDaoImpl implements QuestionnaireDao {
	protected MongoTemplate mongoTemplate;

    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }
    
    public MongoTemplate getMongoTemplate() {
        return this.mongoTemplate;
    }
    
	public String save(DBObject questionnaireDB) {
		//insert the json
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		questionnaires.insert(questionnaireDB);
		ObjectId id = (ObjectId)questionnaireDB.get( "_id" );
		return id.toString();
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String update(String id, DBObject questionnaireDB) {
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
	public DBObject findQuestionnaireById(String id) {
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    DBObject dbObj = questionnaires.findOne(query);
	    if (dbObj==null){return null;}
		// TODO Auto-generated method stub
		return dbObj;
	}

}
