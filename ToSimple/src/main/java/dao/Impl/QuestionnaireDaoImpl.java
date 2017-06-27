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
    
	public Integer save(String questionnaireJSON) {
		
		//forge a input json
		Date d = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d);  
		String json ="{'paperTitle':'Questionnnaire test','authorId':0,'createDate':'"+dateNowStr+"' ,'questions':[{'questionTitle':'q1','type':'0','choices':['a','b']}]}";
		DBObject questionnaireMongo= (DBObject)JSON.parse(json.toString()); 
		
		
		
		
		//insert the json
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		questionnaires.insert(questionnaireMongo);
		return 1;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findQuestionnaireById(String id) {
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("Questionnaires");
		BasicDBObject query = new BasicDBObject();
	    query.put("_id", new ObjectId(id));
	    DBObject dbObj = questionnaires.findOne(query);
	   
		// TODO Auto-generated method stub
		return dbObj.toString();
	}

}
