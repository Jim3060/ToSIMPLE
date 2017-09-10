package service.Impl;

import ToolUtils.EXELUtils;
import dao.QuestionnaireDao;
import dao.QuestionnaireResultDao;
import model.Questionnaire;
import model.QuestionnaireResult;
import model.QuestionnaireStatistics;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import service.StatisticsService;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {
    private static QuestionnaireResultDao questionnaireResultDao;
    private QuestionnaireDao questionnaireDao;

    public static void main(String[] args) throws IOException {
        String questinnaireSTRING = "{'questions':[{'questionTitle':'testSINGLE','type':0,'choices':['c1','c2']},{'questionTitle':'testMUTIPLE','type':1,'choices':['c1','c2','c3'],'limit':'2'},{'questionTitle':'testBLANK','type':2}],'paperTitle':'testPaper','createDate':'2017-07-03T03:15:06.174Z','status':0}";
        Questionnaire questionnaire = new Questionnaire(questinnaireSTRING);
        String answerSTRING = "{'answers' : [{'choice' : [ 0 ],'blank' : ''      }, { 'choice' : [                 1,                2            ],            'blank' : ''        },        {            'choice' : [],            'blank' : 'c'        }    ],    'questionnaireId' : '595b4f2adac1e11c3b16d59f',    'beginTime' : '2017-07-04T08:18:09.798Z',    'endTime' : '2017-07-04T08:18:21.050Z',    'questionnaireResultId' : ''}";
        String answerSTRING2 = "{    'answers' : [         {            'choice' : [                 1            ],            'blank' : ''        },         {            'choice' : [                 1,                 0            ],            'blank' : ''        },         {            'choice' : [],            'blank' : 'd'        }    ],    'questionnaireId' : '595b4f2adac1e11c3b16d59f',    'beginTime' : '2017-07-04T08:18:09.798Z',    'endTime' : '2017-07-04T08:18:28.291Z',    'questionnaireResultId' : ''}";
        List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
        questionnaireResults.add(new QuestionnaireResult(answerSTRING));
        questionnaireResults.add(new QuestionnaireResult(answerSTRING2));
        QuestionnaireStatistics q = new QuestionnaireStatistics(questionnaire, questionnaireResults);


//		String jsonq="{    '_id' : ObjectId('595b4f2adac1e11c3b16d59f'),    'questions' : [         {            'questionTitle' : 'q1',            'type' : 0,            'showAfter' : {},            'choices' : [                 'a',                 'b',                 'c'            ]        },         {            'questionTitle' : 'q2',            'type' : 1,            'showAfter' :{},            'choices' : [                 'a',                 'b',                 'c'            ],            'limit' : '2'        },         {            'questionTitle' : q3',            'type' : 2,            'showAfter' : {}        }    ],    'paperTitle' : 'test',    'createDate' : '2017-07-04T08:26:19.298Z',    'status' : 0}";
//		String jsonr="{'_id' : ObjectId('595b4f4ddac1e11c3b16d5a0'),'answers' : [{'choice' : [ 0 ],'blank' : ''      }, { 'choice' : [                 1,                2            ],            'blank' : ''        },        {            'choice' : [],            'blank' : 'c'        }    ],    'questionnaireId' : '595b4f2adac1e11c3b16d59f',    'beginTime' : '2017-07-04T08:18:09.798Z',    'endTime' : '2017-07-04T08:18:21.050Z',    'questionnaireResultId' : ''}";
//		List<QuestionnaireResult> questionnaireResults= new ArrayList<QuestionnaireResult>();
//		questionnaireResults.add(new QuestionnaireResult(jsonr));
//		 System.out.println(questionnaireResults.size());
//		HSSFWorkbook wb=EXELUtils.GenerateRawResultStatistics( new Questionnaire(jsonq), questionnaireResults);
//		FileOutputStream fout = new FileOutputStream("/Users/JimLiu/Desktop/exel.exel");
//        wb.write(fout);
//        fout.close();

        System.out.println("hi");
    }

    public QuestionnaireStatistics getQuestionnaireStatisticsById(String id) {
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(id);
        List<QuestionnaireResult> questionnaireResults = questionnaireResultDao.getAllQuestionnaireResultById(id);
        return new QuestionnaireStatistics(questionnaire, questionnaireResults);
    }

    public HSSFWorkbook exportToEXEL(String questionnaireId) throws IOException, ParseException {
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(questionnaireId);
        List<QuestionnaireResult> questionnaireResults = questionnaireResultDao.getAllQuestionnaireResultById(questionnaireId);
        HSSFWorkbook wb = EXELUtils.GenerateRawResultStatistics(questionnaire, questionnaireResults);

//		FileOutputStream fout = new FileOutputStream(filePath);
//        wb.write(fout);
//        fout.close();
        return wb;
    }

    public HSSFWorkbook exportChartToEXEL(String questionnaireId) throws IOException, ParseException {
        Questionnaire questionnaire = questionnaireDao.findQuestionnaireById(questionnaireId);
        List<QuestionnaireResult> questionnaireResults = questionnaireResultDao.getAllQuestionnaireResultById(questionnaireId);
        QuestionnaireStatistics questionnaireStat = new QuestionnaireStatistics(questionnaire, questionnaireResults);
        HSSFWorkbook wb = EXELUtils.generateChartStatistics(questionnaireStat);

//		FileOutputStream fout = new FileOutputStream(filePath);
//        wb.write(fout);
//        fout.close();
        return wb;
    }

    public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
        this.questionnaireDao = questionnaireDao;
    }

    public void setQuestionnaireResultDao(QuestionnaireResultDao questionnaireResultDao) {
        StatisticsServiceImpl.questionnaireResultDao = questionnaireResultDao;
    }


}
