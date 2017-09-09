package model;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Questionnaire {
    public JSONObject questionnaireJSON;

    public Questionnaire() {
    }
    
    public static class Association{
    	public String getQuestionnaireId() {
			return questionnaireId;
		}
		public void setQuestionnaireId(String questionnaireId) {
			this.questionnaireId = questionnaireId;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String questionnaireId;
    	public String message;
    	
    	public Association(){}
    	public Association(String q,String m){
    		this.questionnaireId=q;
    		this.message=m;
    	}
    }

    public Questionnaire(String questionnaireStr, Long userid) {
        this.questionnaireJSON = JSONObject.fromObject(questionnaireStr);

        this.questionnaireJSON.put("authorId", userid);
        if (this.questionnaireJSON.get("questionnaireId") == null) {
            questionnaireJSON.put("questionnaireId", "");
        }
    }
    
    public void setAssociate(String questionnaireId,String message){
    	JSONArray associatedQsJ=(JSONArray) this.questionnaireJSON.get("associatedQuestionnaires");
    	if (associatedQsJ==null){
    		System.out.println("null var");
    		JSONArray associatedQsJn=new JSONArray();
    		JSONObject associatedQn=JSONObject.fromObject(new Association(questionnaireId,message));
    		associatedQsJn.add(associatedQn);
    		System.out.println(associatedQsJn.toString());
    		//associatedQsJn.add(new Association(questionnaireId,message));
    		this.questionnaireJSON.put("associatedQuestionnaires",associatedQsJn);
    		System.out.println(this.questionnaireJSON.toString());
    	}
    	else{
    		System.out.println("has var");
    		for (int i=0;i<associatedQsJ.size();i++){
    			JSONObject obj=associatedQsJ.getJSONObject(i);
    			if (obj!=null&&obj.get("questionnaireId").equals(questionnaireId)){
    				associatedQsJ.remove(i);
    			}
    		}
    		associatedQsJ.add(new Association(questionnaireId,message));
    		this.questionnaireJSON.put("associatedQuestionnaires",associatedQsJ);
    	}
    	
    	
    	
    }
    
    public int removeAssociate(String questionnaireId){
    	JSONArray associatedQsJ=(JSONArray) this.questionnaireJSON.get("associatedQuestionnaires");
    	if (associatedQsJ==null){return 0;}
    	for (int i=0;i<associatedQsJ.size();i++){
			JSONObject obj=associatedQsJ.getJSONObject(i);
			if (obj!=null&&obj.get("questionnaireId").equals(questionnaireId)){
				associatedQsJ.remove(i);
			}
		}
    	return  1;
    }

    public Questionnaire(String questionnaireStr) {
        System.out.println(questionnaireStr);
        this.questionnaireJSON = JSONObject.fromObject(questionnaireStr);
        if (!this.questionnaireJSON.has("questionnaireId")) {
            questionnaireJSON.put("questionnaireId", "");
        }
    }

    public Questionnaire(DBObject questionnaireDB) {
        if (questionnaireDB == null) {
            return;
        }
        questionnaireDB.put("questionnaireId", questionnaireDB.get("_id").toString());
        this.questionnaireJSON = JSONObject.fromObject(questionnaireDB.toString());
    }

    public String getQuestionnaire() {
        return questionnaireJSON.toString();
    }

    public void setStatus(int status) {
        this.questionnaireJSON.put("status", status);
    }

    public String getQuestionnaireId() {
        return this.questionnaireJSON.get("questionnaireId").toString();
    }

    public DBObject fetchQuestionnaireDB() {
        JSONObject tmp = JSONObject.fromObject(questionnaireJSON.toString());
        tmp.remove("questionnaireId");
        return (DBObject) JSON.parse(tmp.toString());
    }
    
    public Questionnaire cleanDb(){
    	this.questionnaireJSON.remove("questionnaireDB");
    	return this;
    }


}
