package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuestionnaireStatistics {
	public static List<QuestionnaireResult> questionnaireResults= new ArrayList<QuestionnaireResult>();
	public static Questionnaire questionnaire;
	public String questionnaireTitle;
	public List<Question> questions=new ArrayList<Question>();
	
	public QuestionnaireStatistics( Questionnaire questionnaire, List<QuestionnaireResult> questionnaireResults) {
		this.questionnaireResults = questionnaireResults;
		this.questionnaire = questionnaire;
		//initial questions
		initalQuestions(questionnaire);
		//put statistics
		putResults(questionnaireResults);
		
	}
	
	public void initalQuestions(Questionnaire questionnaire){
		JSONArray questionsJ= (JSONArray) questionnaire.questionnaireJSON.get("questions");
		Question tmp;
		Iterator<Object> it = questionsJ.iterator();
		while(it.hasNext()){
			tmp=new Question();
			JSONObject questionJ = (JSONObject) it.next();
			tmp.title=(String) questionJ.get("questionTitle");
			tmp.type=(int) questionJ.get("type");
			tmp.blanks=(List<Blank>) new ArrayList<Blank>();
			if (tmp.type!=2){//has choices
				tmp.choices = new ArrayList<Choice> ();
				JSONArray choicesJ = (JSONArray) questionJ.get("choices");
				Iterator<Object> itc = choicesJ.iterator();
				while  (itc.hasNext()){
					Choice ctmp=new Choice();
					ctmp.title=(String) itc.next();
					tmp.choices.add(ctmp);
				}
			}
			questions.add(tmp);
		}
	}
	
	public void putResults(List<QuestionnaireResult> questionnaireResults){
		//put statistics
		for (int i=0;i<questionnaireResults.size();i++){
			JSONObject tmpResultJ= questionnaireResults.get(i).questionnaireResultJSON;
			JSONObject  answersJ = (JSONObject) tmpResultJ.get("answer");
			for (int j=0;j<questions.size();j++){
				if (answersJ.has(String.valueOf(j))){
					JSONArray answertmp = (JSONArray) answersJ.get(String.valueOf(j));
					if (questions.get(j).type==2){
						questions.get(j).blanks.add(new Blank(answertmp.get(1).toString(),(String)tmpResultJ.get("questionnaireResultId")));//,tmpResultJ.get("_id").toString())
					}
					JSONArray choicetmp = (JSONArray) answertmp.get(0);
					for (int k=0; k<choicetmp.size();k++){
						questions.get(j).choices.get((int) choicetmp.get(k)).number++;
					}
				}
			}
		}
	}
	
	public static class Choice {
		public String title;
		public int number;
	}
	
	public static class Blank {
		public Blank (){;}
		public Blank (String c, String r){
			content=c;
			resultId=r;
		}
		public String content;
		public String resultId;
	}
	
	public static class Question {
		public List<Choice> choices = new ArrayList<Choice>();
		public List<Blank> blanks = new ArrayList<Blank>();
		public String title;
		public int type;
	}
	
	
	
	
	

}
