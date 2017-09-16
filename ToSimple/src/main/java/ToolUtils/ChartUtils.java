package ToolUtils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class ChartUtils {


    public static File CreateBarChart(DefaultCategoryDataset dataset, String title, String xName, String yName) throws IOException {

        setChartTheme();
        JFreeChart chart = ChartFactory.createBarChart3D(title, xName,
                yName, dataset, PlotOrientation.VERTICAL, true, true, false);

        String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ranstr() + ".png";
        File file = new File(filename);
        if (chart == null) {
            return null;
        }
        ChartUtilities.saveChartAsPNG(file, chart, 600, 500);
        //ChartUtilities.saveChartAsJPEG(new File("/Users/JimLiu/Downloads/test.png"), chart, 400, 300);

        return file;


    }

    public static String ranstr() {
        int max = 1000;
        int min = 1;
        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;
        System.out.println(s);
        return String.valueOf(s);
    }

    public static File CreatePieChart(PieDataset dataset, String title) throws IOException {
        JFreeChart chart = ChartFactory.createPieChart(
                title,  // chart title
                dataset,        // data
                true,           // include legend
                true,
                false);
        String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".png";
        File file = new File(filename);
        ChartUtilities.saveChartAsPNG(file, chart, 600, 500);
        // ChartUtilities.saveChartAsJPEG(new File("/Users/JimLiu/Downloads/test.png"), chart, 400, 300);
        return file;
    }

    private static void setChartTheme() {

        // 创建主题样式       
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        // 设置标题字体       
        standardChartTheme.setExtraLargeFont(new Font("宋书", Font.BOLD, 20));
        // 设置图例的字体       
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
        // 设置轴向的字体       
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
        // 应用主题样式       
        ChartFactory.setChartTheme(standardChartTheme);
    }

//	private DefaultCategoryDataset getBarDataSet(QuestionnaireStatistics.Question question)       
//    {       
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();      
//        List<QuestionnaireStatistics.Choice> choices=question.choices;
//        for (int i=0;i<choices.size();i++){
//        	QuestionnaireStatistics.Choice choice=choices.get(i);
//        	dataset.addValue(choice.number,choice.title,choice.title);
//        }      
//        return dataset;       
//    }       
}
