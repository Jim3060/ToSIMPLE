package ToolUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.QuestionnaireSpider;

public class SojumpParser {
	public static Document document;
	public String url;
	
	public class Choicetmp{
		public String text;
		public boolean withBlank;
	}
	
	public SojumpParser(){;}
	public SojumpParser(String url){
		this.url=url;
		Connection conn = Jsoup.connect(url);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:54.0) Gecko/20100101 Firefox/54.0");
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String getQuestionnaireTitle(){
		Elements titles=document.select("span[id$=QuestionnaireName]");
		if (titles!=null&&titles.size()>0){
			return titles.get(0).text();
		}
		return null;
	}
	
	public static String getQuestionnaireDescription(){
		Elements titles=document.select("span[id$=QuestionnaireName]");
		if (titles!=null&&titles.size()>0){
			return titles.get(0).text();
		}
		return "";
	}
	
	public static int getQuestionType(Element question){
		Element choices=question.child(1).child(1);
		if (choices.tagName()=="textarea"){
			return 2;
		}
		else if (choices.tagName()=="ul"){
			if (choices.child(0).getElementsByTag("input").attr("type").equals("radio")){
				return 0;
			}
			else if (choices.child(0).getElementsByTag("input").attr("type").equals("checkbox")){
				return 1;
			}
		}
		return -1;
	}
	
	public static String getQuestionTitle(Element question){
		question.child(0).select("div[class=div_title_question]").get(0).select("span").remove();
		return question.child(0).select("div[class=div_title_question]").get(0).text();
	}
	public static boolean getQuestionForced(Element question){
		 Elements req=question.child(0).select("div[class=div_title_question]").get(0).select("span[class=req]");
		 if (req!=null&&req.size()>0){
			 return true;
		 }
		 return false;
	}
	
	public static List<QuestionnaireSpider.Choice> getQuestionChoices(Element question){
		List<QuestionnaireSpider.Choice> result=new ArrayList<QuestionnaireSpider.Choice>();
		Elements choicesP=question.child(1).getElementsByTag("ul");
		Element choices;
		for (int i =0;i<choicesP.size();i++){
			choices=choicesP.get(i);
			Element choice;
			for (int j=0;j<choices.children().size();j++){
				choice=choices.child(j);
				QuestionnaireSpider.Choice c=new QuestionnaireSpider.Choice();
				if (choice.getElementsByTag("label")!=null&&choice.getElementsByTag("label").size()>0){
					c.text=choice.getElementsByTag("label").get(0).text();
					
					result.add(c);
				}
			}
		}
		return result;
	}
	
	public static boolean getQuestionMix(Element question){
		Elements choicesP=question.child(1).getElementsByTag("ul");
		Element choices;
		for (int i =0;i<choicesP.size();i++){
			choices=choicesP.get(i);
			Element choice;
			for (int j=0;j<choices.children().size();j++){
				choice=choices.child(j);
				if (choice.getElementsByTag("label")!=null&&choice.getElementsByTag("label").size()>0){
					if (choice.getElementsByTag("input").size()>=2){
						return true;
					}
					
				}
			}
		}
		return false;
	}
	
	public static List<QuestionnaireSpider.Question> getQuestionnaireQuestions(){
		List<QuestionnaireSpider.Question> qs=new ArrayList<QuestionnaireSpider.Question>();
		Elements questions=document.select("div[class=div_question]");
        Element question;
        QuestionnaireSpider.Question q;
        for (int i =0; i<questions.size();i++){
        	q=new QuestionnaireSpider.Question();
        	question=questions.get(i);
        	q.forced=SojumpParser.getQuestionForced(question);
        	q.questionTitle=SojumpParser.getQuestionTitle(question);
        	q.type=SojumpParser.getQuestionType(question);
        	if (q.type==-1){continue;}
        	List<QuestionnaireSpider.Choice> choices=null; 
        	if (q.type==1||q.type==0){
        		choices=SojumpParser.getQuestionChoices(question);
        		q.mix=getQuestionMix(question);
        		q.choices=choices;
        		
        	}
        	qs.add(q);
        }
		return qs;
	}
	
	public QuestionnaireSpider parseSojump(){
		QuestionnaireSpider questionnaire=new QuestionnaireSpider();
		questionnaire.paperTitle=getQuestionnaireTitle();
		if (questionnaire.paperTitle==null){return null;}
		questionnaire.briefing=getQuestionnaireDescription();
		questionnaire.questions=getQuestionnaireQuestions();
		return questionnaire;
	}
}
