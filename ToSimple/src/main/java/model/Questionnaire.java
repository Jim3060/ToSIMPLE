package model;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import net.sf.json.JSONObject;

public class Questionnaire {
    public JSONObject questionnaireJSON;

    public Questionnaire() {
        ;
    }

    public Questionnaire(String questionnaireStr, Long userid) {
        this.questionnaireJSON = JSONObject.fromObject(questionnaireStr);
        
        this.questionnaireJSON.put("authorId", userid);
        if (this.questionnaireJSON.get("questionnaireId") == null) {
            questionnaireJSON.put("questionnaireId", "");
        }
    }

    public Questionnaire(String questionnaireStr) {
    	System.out.println(questionnaireStr);
        this.questionnaireJSON = JSONObject.fromObject(questionnaireStr);
        if (!this.questionnaireJSON.has("questionnaireId") ) {
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

    public DBObject getQuestionnaireDB() {
        JSONObject tmp = JSONObject.fromObject(questionnaireJSON.toString());
        tmp.remove("questionnaireId");
        return (DBObject) JSON.parse(tmp.toString());
    }


}
