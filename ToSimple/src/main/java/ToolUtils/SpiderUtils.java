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
	
	
	public static void main(String[] args) throws IOException   {
		SojumpParser parser=new SojumpParser("https://sojump.com/jq/3048945.aspx");
		System.out.println(QuestionnaireSpider.toJson(parser.parseSojump()));
	}
}
