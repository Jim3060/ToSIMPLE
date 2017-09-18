package model;


import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.util.ArrayList;
import java.util.List;


public class QuestionnaireStatistics {
    public static List<QuestionnaireResult> questionnaireResults = new ArrayList<QuestionnaireResult>();
    public static Questionnaire questionnaire;
    public String questionnaireTitle;
    public List<Question> questions = new ArrayList<Question>();
    
    
    public static class Choice {
        public String title;
        public int number;
    }

    public static class Blank {
        public String content;
        public String resultId;

        public Blank() {
            ;
        }

        public Blank(String c, String r) {
            content = c;
            resultId = r;
        }
    }

    public static class Question {
        public List<Choice> choices = new ArrayList<Choice>();
        public List<Blank> blanks = new ArrayList<Blank>();
        public String title;
        public int type;
        public boolean mix;

        public DefaultCategoryDataset getBarDataSet() {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            List<QuestionnaireStatistics.Choice> choices = this.choices;
            if (choices.size() == 0) {
                dataset.addValue(0, "other", "other");
            }

            for (int i = 0; i < choices.size(); i++) {
                QuestionnaireStatistics.Choice choice = choices.get(i);
                dataset.addValue(choice.number, choice.title, choice.title);
            }
//            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
//            System.out.println(dataset);
//            System.out.println(choices.size());
            return dataset;
        }

        public PieDataset getPieDataSet() {
            DefaultPieDataset dataset = new DefaultPieDataset();
            List<QuestionnaireStatistics.Choice> choices = this.choices;
            for (int i = 0; i < choices.size(); i++) {
                QuestionnaireStatistics.Choice choice = choices.get(i);
                dataset.setValue(choice.title, choice.number);
            }
            return dataset;
        }
    }

    public QuestionnaireStatistics(Questionnaire questionnaire, List<QuestionnaireResult> questionnaireResults) {
        this.questionnaireResults = questionnaireResults;
        this.questionnaire = questionnaire;
        //initial questions
        initalQuestions(questionnaire);
        //put statistics
        putResults(questionnaireResults);

    }

    public void initalQuestions(Questionnaire questionnaire) {
        QuestionnaireGSON questionnaireGSON = QuestionnaireGSON.getQuestionnaireGSON(questionnaire.getQuestionnaire());
        Question tmp;
        for (int i = 0; i < questionnaireGSON.questions.size(); i++) {
            QuestionnaireGSON.Question qtmp = new QuestionnaireGSON.Question();
            qtmp = questionnaireGSON.questions.get(i);
            tmp = new Question();
            tmp.title = qtmp.questionTitle;
            tmp.type = qtmp.type;
            tmp.blanks = (List<Blank>) new ArrayList<Blank>();
            if (tmp.type != 2) {//has choices
                tmp.choices = new ArrayList<Choice>();
                for (int j = 0; j < qtmp.choices.size(); j++) {
                    Choice ctmp = new Choice();
                    ctmp.title = qtmp.choices.get(j).text;
                    tmp.choices.add(ctmp);
                }
                if (qtmp.mix == true) {
                	tmp.mix=true;
                	//add the choice "other"
                    Choice ctmp = new Choice();
                    ctmp.title = "other";
                    tmp.choices.add(ctmp);
                }
                else {tmp.mix=false;}
            }
            questions.add(tmp);
        }

    }

    public void putResults(List<QuestionnaireResult> questionnaireResults) {
        //put statistics
        QuestionnaireResultGSON questionnaireResultGSON;
        for (int i = 0; i < questionnaireResults.size(); i++) {
            questionnaireResultGSON = QuestionnaireResultGSON.getQuestionnaireResultGSON(questionnaireResults.get(i).getQuestionnaireResult());
            QuestionnaireResultGSON.Answer atmp;
            for (int j = 0; j < questionnaireResultGSON.answers.size(); j++) {
                atmp = questionnaireResultGSON.answers.get(j);
                Question thisQuestion=questions.get(j);
                if (thisQuestion.type == 2) {
                    if (atmp.blank != null && !atmp.blank.equals("")) {
                        questions.get(j).blanks.add(new Blank(atmp.blank, questionnaireResultGSON.questionnaireResultId));
                    }

                } else {
                    if (atmp.choice != null) {//单选+mix有可能
                        for (int k = 0; k < atmp.choice.size(); k++) {
                            if (atmp.choice.get(k) != null) {
                            	//System.out.println(atmp.choice.get(k));
                                questions.get(j).choices.get(atmp.choice.get(k)).number++;
                            }

                        }
                        if (atmp.blank != null && !atmp.blank.equals("")) {
                            questions.get(j).choices.get(questions.get(j).choices.size() - 1).number++;
                        }
                    }
                    else{
                    	questions.get(j).choices.get(thisQuestion.choices.size() - 1).number++;
                    	questions.get(j).blanks.add(new Blank(atmp.blank, questionnaireResultGSON.questionnaireResultId));
                    }
                }
            }
        }
        System.out.println("break");

    }

    public String getQuestionsJSON() {
        JSONArray questionArray = new JSONArray();
        for (int i = 0; i < questions.size(); i++) {
            Gson gson = new Gson();
            String obj = gson.toJson(questions.get(i));
            questionArray.add(JSONObject.fromObject(obj));
        }
        return questionArray.toString();
    }

  


}