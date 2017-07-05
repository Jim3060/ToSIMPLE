package model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QuestionnaireResultGSON {
	public String questionnaireId;
	public String beginTime;
	public String endTime;
	public String questionnaireResultId;
	public List<Answer> answers;
	
	public class Answer{
		public List<Integer> choice;
		public String blank;
	}
	
	public static QuestionnaireResultGSON getQuestionnaireResultGSON(String json){
		Gson gson = new GsonBuilder().create();
        QuestionnaireResultGSON p = gson.fromJson(json, QuestionnaireResultGSON.class);
        return p;
	}
	
}
