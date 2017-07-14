package model;

import java.util.List;

import com.google.gson.Gson;

import net.sf.json.JSONObject;



public class QuestionnaireSpider {
	public String paperTitle;
	public String briefing;
	public List<Question> questions;
	
	public static class Question{
		public String questionTitle;
		public boolean forced;
		public int type;
		public boolean mix;
		public List<Choice> choices;
	}
	
	public static class Choice{
		public String text;
		//public boolean withBlank;
	}
	
	public static String toJson(QuestionnaireSpider q){
		Gson gson = new Gson();
        String json = gson.toJson(q);
        return json;
	}
	
	public static Questionnaire toQuestionnaire(QuestionnaireSpider q){
		Gson gson = new Gson();
        String json = gson.toJson(q);
        JSONObject jobj=JSONObject.fromObject(json);
        jobj.put("status", 0);
        jobj.put("answerNumber", 0);
        jobj.put("authorId", 1);
        jobj.put("createDate", "1900-01-01T00:00:00.000Z");
        
        return new Questionnaire(jobj.toString());
	}
	
	
}
