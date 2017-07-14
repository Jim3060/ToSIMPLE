package model;

import java.util.List;

import com.google.gson.Gson;



public class QuestionnaireSpider {
	public String paperTitle;
	public String briefing;
	public List<Question> questions;
	
	public static class Question{
		public String questionTitle;
		public int type;
		public boolean mix;
		public List<Choice> choices;
	}
	
	public static class Choice{
		public String text;
		public boolean withBlank;
	}
	
	public static String toJson(QuestionnaireSpider q){
		Gson gson = new Gson();
        String json = gson.toJson(q);
        return json;
	}
	
	
}
