package dao.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;

import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
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
    
	public Integer save(DBObject questionnaireResultDB) {
		//insert the json
		DB db = mongoTemplate.getDb();
		DBCollection questionnaires = db.getCollection("QuestionnaireResults");
		questionnaires.insert(questionnaireResultDB);
		return 1;
	}

	@Override
	public List<QuestionnaireResult> getAllQuestionnaireResultById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
