package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import model.Report;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.ReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ReportAction {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReportService reportService;

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
    
    @RequestMapping(value = "allUnhandledReports", method = RequestMethod.GET)
    public String getUnhandledReportsByPage(HttpServletResponse response,@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize ) throws Exception {
        //get users
        List<Report> reports = reportService.getAllUnhandledReportsBypage(page, pageSize);
        JSONObject result = new JSONObject();
    	result.put("reports",reports);
    	result.put("reportNum",reportService.getUnhandledReportsNum());
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
    	response.getWriter().print(result);
    	return null;
    }

    @RequestMapping(value = "allUnhandledReports/{questionnaireId}", method = RequestMethod.GET)
    public String getUnhandledReportsByPage(@PathVariable("questionnaireId") String questionnaireId,HttpServletResponse response,@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize ) throws Exception {
        //get users
        List<Report> reports = reportService.getAllUnhandledReportsByQuestionnaireId(page, pageSize, questionnaireId);
        JSONObject result = new JSONObject();
    	result.put("reports",reports);
    	result.put("reportNum",reportService.getUnhandledReportsNumByQuestionnaireId(questionnaireId));
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
    	response.getWriter().print(result);
    	return null;
    }


    @RequestMapping(value = "allReports", method = RequestMethod.GET)
    public String getAllReportsByPage(HttpServletResponse response,@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize ) throws Exception {
        //get users
        List<Report> reports = reportService.getAllReportsBypage(page, pageSize);
        JSONObject result = new JSONObject();
    	result.put("reports",reports);
    	result.put("reportNum",reportService.getAllReportsNum());
    	response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
    	response.getWriter().print(result);
    	return null;
    }
    

    @RequestMapping(value = "report/{reportId}", method = RequestMethod.POST)
    public String edit(@PathVariable("reportId") Long reportId, Integer status, HttpServletResponse response) throws IOException {
        reportService.setReportStatus(reportId, status);
        return null;
    }

    @RequestMapping(value = "report/{reportId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("reportId") Long reportId, HttpServletResponse response) throws IOException {
        reportService.deleteReport(reportId);
        return null;
    }
    

    @RequestMapping(value = "reportHandled/{questionnaireId}", method = RequestMethod.POST)
    public String setReportHandledByQuestionnaire(@PathVariable("questionnaireId") String questionnaireId, HttpServletResponse response) throws IOException{
    	reportService.setReportHandledByQuestionnaire(questionnaireId);
    	response.getWriter().print(1);
    	return null;
    }




}
