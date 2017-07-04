package service.Impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import model.QuestionnaireResultGSON;
import ToolUtils.EXELUtils;
import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.Questionnaire;
import model.QuestionnaireGSON;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import net.sf.json.JSONObject;
import service.QuestionnaireService;
import service.StatisticsService;

public class StatisticsServiceImpl implements StatisticsService{
	private QuestionnaireDao questionnaireDao;
	
	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}
	
	private static QuestionnaireResultDao questionnaireResultDao;
	
	public void setQuestionnaireResultDao(QuestionnaireResultDao questionnaireResultDao) {
		this.questionnaireResultDao = questionnaireResultDao;
	}

	

	
	public QuestionnaireStatistics getQuestionnaireStatisticsById(String id){
		Questionnaire questionnaire=questionnaireDao.findQuestionnaireById(id);
		List<QuestionnaireResult> questionnaireResults=questionnaireResultDao.getAllQuestionnaireResultById(id);
		return new QuestionnaireStatistics(questionnaire,questionnaireResults);
	}
	
	public Integer exportToEXEL(String questionnaireId,String filePath) throws IOException{
		Questionnaire questionnaire=questionnaireDao.findQuestionnaireById(questionnaireId);
		List<QuestionnaireResult> questionnaireResults=questionnaireResultDao.getAllQuestionnaireResultById(questionnaireId);
		HSSFWorkbook wb=EXELUtils.GenerateRawResultStatistics(questionnaire, questionnaireResults);
		FileOutputStream fout = new FileOutputStream(filePath);  
        wb.write(fout);  
        fout.close();  
		return null;
	}
	
	
	
	public static void main(String[] args) throws IOException {
//		String questinnaireSTRING="{'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0}";
//		Questionnaire questionnaire=new Questionnaire(questinnaireSTRING);
//		String answerSTRING="{'answer':{'0':[[0],''],'1':[[1,2],''],'2':[[],'test']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T03:17:56.866Z'}";
//		String answerSTRING2="{'answer':{'0':[[1],''],'1':[[0,1],''],'2':[[],'test2']},'objectId':'5959b6badac1e1082a93c51d','answerTime':'2017-07-03T06:57:10.473Z'}";
//		List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING));
//		questionnaireResults.add(new QuestionnaireResult(answerSTRING2));
//		QuestionnaireStatistics q = new QuestionnaireStatistics(questionnaire,questionnaireResults);
		//List<QuestionnaireResult> questionnaireResults=questionnaireResultDao.getAllQuestionnaireResultById ("595b4f2adac1e11c3b16d59f");
		//String jsontest="{'_id':{'$oid':'5959b6badac1e1082a93c51d'},'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0,'questionnaireId':'5959b6badac1e1082a93c51d'}";
		String jsonq="{    '_id' : ObjectId('595b4f2adac1e11c3b16d59f'),    'questions' : [         {            'questionTitle' : 'q1',            'type' : 0,            'showAfter' : {},            'choices' : [                 'a',                 'b',                 'c'            ]        },         {            'questionTitle' : 'q2',            'type' : 1,            'showAfter' :{},            'choices' : [                 'a',                 'b',                 'c'            ],            'limit' : '2'        },         {            'questionTitle' : q3',            'type' : 2,            'showAfter' : {}        }    ],    'paperTitle' : 'test',    'createDate' : '2017-07-04T08:26:19.298Z',    'status' : 0}";
		String jsonr="{'_id' : ObjectId('595b4f4ddac1e11c3b16d5a0'),'answers' : [{'choice' : [ 0 ],'blank' : ''      }, { 'choice' : [                 1,                2            ],            'blank' : ''        },        {            'choice' : [],            'blank' : 'c'        }    ],    'questionnaireId' : '595b4f2adac1e11c3b16d59f',    'beginTime' : '2017-07-04T08:18:09.798Z',    'endTime' : '2017-07-04T08:18:21.050Z',    'questionnaireResultId' : ''}";
		List<QuestionnaireResult> questionnaireResults= new ArrayList<QuestionnaireResult>();
		questionnaireResults.add(new QuestionnaireResult(jsonr));
		 System.out.println(questionnaireResults.size());
		HSSFWorkbook wb=EXELUtils.GenerateRawResultStatistics( new Questionnaire(jsonq), questionnaireResults);
		FileOutputStream fout = new FileOutputStream("/Users/JimLiu/Desktop/exel.exel");  
        wb.write(fout);  
        fout.close();  
        
        System.out.println("hi");
	}

	

}
