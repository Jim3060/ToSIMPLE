package model;

import ToolUtils.IPUtils;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class QuestionnaireResult {
    public JSONObject questionnaireResultJSON;

    public QuestionnaireResult() {
    }

    public QuestionnaireResult(String questionnaireResult) {
        this.questionnaireResultJSON = JSONObject.fromObject(questionnaireResult);
        if (this.questionnaireResultJSON.get("questionnaireResultId") == null) {
            questionnaireResultJSON.put("questionnaireResultId", "");
        }
    }

    public QuestionnaireResult(String questionnaireResult, HttpServletRequest request) {
        this.questionnaireResultJSON = JSONObject.fromObject(questionnaireResult);

        String userIP = IPUtils.getIpAddress(request);
        System.out.println(userIP);
        this.questionnaireResultJSON.put("userIP", userIP);

        if (this.questionnaireResultJSON.get("questionnaireResultId") == null) {
            questionnaireResultJSON.put("questionnaireResultId", "");
        }
    }

    public QuestionnaireResult(DBObject questionnaireResultDB) {
        if (questionnaireResultDB == null) {
            return;
        }
        questionnaireResultDB.put("questionnaireResultId", questionnaireResultDB.get("_id").toString());
        this.questionnaireResultJSON = JSONObject.fromObject(questionnaireResultDB.toString());
    }

    public DBObject getQuestionnaireResultDB() {
        System.out.println(this.questionnaireResultJSON.get("userIP"));
        return (DBObject) JSON.parse(this.questionnaireResultJSON.toString());
    }

    public String getQuestionnaireResult() {
        return this.questionnaireResultJSON.toString();
    }


}
