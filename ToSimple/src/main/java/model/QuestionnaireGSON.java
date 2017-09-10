package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class QuestionnaireGSON {
    public String paperTitle;
    public String createDate;
    public int status;
    public List<Question> questions;

    public static QuestionnaireGSON getQuestionnaireGSON(String json) {
        Gson gson = new GsonBuilder().create();
        QuestionnaireGSON p = gson.fromJson(json, QuestionnaireGSON.class);
        return p;
    }

    public static class Question {
        public String questionTitle;
        public int type;
        public List<Choice> choices;
    }

    public static class Choice {
        public String text;
        public String photoId;
    }
}
