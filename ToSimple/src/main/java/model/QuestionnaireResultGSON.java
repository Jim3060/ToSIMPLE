package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class QuestionnaireResultGSON {
    public String questionnaireId;
    public String beginTime;
    public String endTime;
    public String questionnaireResultId;
    public List<Answer> answers;
    public String userIP;

    public static QuestionnaireResultGSON getQuestionnaireResultGSON(String json) {
        Gson gson = new GsonBuilder().create();
        QuestionnaireResultGSON p = gson.fromJson(json, QuestionnaireResultGSON.class);
        return p;
    }

    public class Answer {
        public List<Integer> choice;
        public String blank;
    }

}
