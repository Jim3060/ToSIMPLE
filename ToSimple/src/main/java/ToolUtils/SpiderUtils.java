package ToolUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ToolUtils.Spider.Algorithm;
import ToolUtils.Spider.BasicWebUtils;
import model.Questionnaire;
import model.QuestionnaireSpider;



public class SpiderUtils {
	private static String keyWord;
	public static class SojumpBrief{
		public String title;
		public String id;
		public SojumpBrief(){;}
		public SojumpBrief(String title,String id){
			this.title=title;
			this.id=id;
		}
	}
	
	public static List<String> searchSojumpIdByKW(String kw){
		List<SojumpBrief> result = new ArrayList<SojumpBrief>();
		
		Comparator<SojumpBrief> cmps =  new Comparator<SojumpBrief>(){  
            public int compare(SojumpBrief o1, SojumpBrief o2) {  
            	return (Algorithm.getStringGap(o1.title, keyWord)-Algorithm.getStringGap(o2.title, keyWord)>0)?1:-1;
            }  
		};

		keyWord=kw;
		Document document=BasicWebUtils.connUrl("https://www.sojump.com/publicsurveys.aspx?keyword="+ kw+"&sort=1&jt=&qc=");
        if (document==null){return null;}
        Elements searchResults=document.select("div[class=sinnerbox surveryA]");
        for (Element i:searchResults){
        	String href=i.child(0).child(0).attr("href");
        	String regEx="activity=*"; 
        	String [] dataStr = href.split(regEx); 
        	System.out.println(i.child(0).child(0).text());
        	System.out.println(dataStr[1]);
        	System.out.println(Algorithm.getStringGap(i.child(0).child(0).text(), keyWord));
        	
        	result.add( new SojumpBrief(i.child(0).child(0).text(),dataStr[1]));
        	
        	
        }
        result.sort(cmps);
        System.out.println("test:");
        
        
        List<String> ids=new ArrayList<String>();
        for (int i=0;i<result.size();i++){
        	ids.add(result.get(i).id);
        	System.out.println(result.get(i).title);
        	
        }
        return ids;
	}
	
	public Questionnaire getQuestionnaireByKW(String kw){
		List<String> ids=searchSojumpIdByKW(kw);
		
		SojumpParser parser=new SojumpParser(ids.get(0));
		QuestionnaireSpider q=parser.parseSojump();
		return QuestionnaireSpider.toQuestionnaire(q);
	}
	
	public static void main(String[] args) throws IOException   {
		searchSojumpIdByKW("上海交大选修课调查");
		
			
		
		
//		SojumpParser parser=new SojumpParser("https://sojump.com/jq/3048945.aspx");
//		System.out.println(QuestionnaireSpider.toJson(parser.parseSojump()));
	}
}
