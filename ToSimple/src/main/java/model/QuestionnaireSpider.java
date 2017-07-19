package model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ToolUtils.Spider.Algorithm;
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
	
	public QuestionnaireSpider.Question getQuestionByKW(String kw){
		QuestionnaireSpider q=this;
		List<QuestionnaireSpider.Question> questions= q.questions;
		QuestionnaireSpider.Question result=null;
		int distance=-1;
		int len=0;
		int flag=0;
		for (int i=0;i<questions.size();i++){
			QuestionnaireSpider.Question qtmp=questions.get(i);
			System.out.println(distance);
			if ((len=Algorithm.getStringGap(kw, qtmp.questionTitle))<distance||flag==0){
				if (len==((kw.length()>qtmp.questionTitle.length())?kw.length():qtmp.questionTitle.length())){continue;}
				result=qtmp;distance=len;
				flag=1;
				System.out.println("here");
			}
		}
		return result;
	}
	
	public List<QuestionnaireSpider.Question> getQuestionsByKW(String kw){
		QuestionnaireSpider q=this;
		List<QuestionnaireSpider.Question> questions= q.questions;
		QuestionnaireSpider.Question result=null;
		List<QuestionnaireSpider.Question> results=new ArrayList<QuestionnaireSpider.Question>();
		int distance=-1;
		int len=0;
		int flag=0;
		String hotTitle="";
		for (int i=0;i<questions.size();i++){
			QuestionnaireSpider.Question qtmp=questions.get(i);
			System.out.println(distance);
			if (hotTitle.equals(qtmp.questionTitle)){continue;}
			hotTitle=qtmp.questionTitle;
			if (qtmp.questionTitle.indexOf(kw)!=-1){System.out.println("match");results.add(qtmp);continue;}
			if ((len=Algorithm.getStringGap(kw, qtmp.questionTitle))<distance||flag==0){
				if (len==((kw.length()>qtmp.questionTitle.length())?kw.length():qtmp.questionTitle.length())){continue;}
				result=qtmp;distance=len;
				flag=1;
				System.out.println("here");
			}
		
		}
		if (result!=null){results.add(result);}
		if (results!=null&&results.size()!=0){return results;}
		return null;
		
	}
	
	
}
