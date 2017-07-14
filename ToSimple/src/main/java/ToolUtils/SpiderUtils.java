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

public class SpiderUtils {
	public static String getQuestionnaire(String url){
		url="https://sojump.com/jq/3048945.aspx";
		Connection conn = Jsoup.connect(url);
        // 修改http包中的header,伪装成浏览器进行抓取
        conn.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:54.0) Gecko/20100101 Firefox/54.0");
        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        Element questionnaireTitle = doc.select("span[id$=QuestionnaireName]").get(0);
        Element questionnaireDescription = doc.select("span[id$=QuestionnaireDescription]").get(0);
        Elements questions=doc.select("div[class=div_question]");
        Element question;
        for (int i =0; i<questions.size();i++){
        	question=questions.get(i);
        	System.out.println(SojumpParser.getQuestionType(question));
        	System.out.println(SojumpParser.getQuestionTitle(question));
        	List<QuestionnaireSpider.Choice> choices=SojumpParser.getQuestionChoices(question);
        	for (QuestionnaireSpider.Choice c : choices){
        		System.out.println(c.text);
        		System.out.println(c.withBlank);
        	}
        }
       
        //System.out.println(questionnaireDescription.text());
		return null;
	}
	
	public static class Choice{
		public String text="";
		public boolean withBlank=false;
	}
	
	public static class SojumpParser{
		public static Document document;
		public String url;
		
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
			return document.select("span[id$=QuestionnaireName]").get(0).text();
		}
		
		public static String getQuestionnaireDescription(){
			return document.select("span[id$=QuestionnaireDescription]").get(0).text();
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
			return question.child(0).select("div[class=div_title_question]").get(0).text();
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
						if (choice.getElementsByTag("input").size()>=2){
							c.withBlank=true;
						}
						result.add(c);
					}
				}
			}
			return result;
		}
		
		public static List<QuestionnaireSpider.Question> getQuestionnaireQuestions(){
			List<QuestionnaireSpider.Question> qs=new ArrayList<QuestionnaireSpider.Question>();
			Elements questions=document.select("div[class=div_question]");
	        Element question;
	        QuestionnaireSpider.Question q;
	        for (int i =0; i<questions.size();i++){
	        	q=new QuestionnaireSpider.Question();
	        	question=questions.get(i);
	        	q.questionTitle=SojumpParser.getQuestionTitle(question);
	        	q.type=SojumpParser.getQuestionType(question);
	        	List<QuestionnaireSpider.Choice> choices=null; 
	        	if (q.type==1||q.type==0){
	        		choices=SojumpParser.getQuestionChoices(question);
	        		q.choices=choices;
	        		for (QuestionnaireSpider.Choice c : choices){
		        		if (c.withBlank){
		        			q.mix=true;
		        		}
		        	}
	        	}
	        	qs.add(q);
	        }
			return qs;
		}
		
		public QuestionnaireSpider parseSojump(){
			QuestionnaireSpider questionnaire=new QuestionnaireSpider();
			questionnaire.paperTitle=getQuestionnaireTitle();
			questionnaire.briefing=getQuestionnaireDescription();
			questionnaire.questions=getQuestionnaireQuestions();
			return questionnaire;
		}
		
	}
	
	public static void main(String[] args) throws IOException   {
		SojumpParser parser=new SojumpParser("https://sojump.com/jq/3048945.aspx");
		System.out.println(QuestionnaireSpider.toJson(parser.parseSojump()));
	}
}
