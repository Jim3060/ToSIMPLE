package action;

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
    public String getUnhandledReportsByPage(HttpServletResponse response, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) throws Exception {
        //get users
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        List<Report> reports = reportService.getAllUnhandledReportsBypage(page, pageSize);
        JSONObject result = new JSONObject();
    	result.put("reports",reports);
    	result.put("reportNum",reportService.getUnhandledReportsNum());
    	response.getWriter().print(result);
    	return null;
    }

    @RequestMapping(value = "allUnhandledReports/{questionnaireId}", method = RequestMethod.GET)
    public String getUnhandledReportsByPage(@PathVariable("questionnaireId") String questionnaireId,HttpServletResponse response,@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize ) throws Exception {
        //get users
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        List<Report> reports = reportService.getAllUnhandledReportsByQuestionnaireId(page, pageSize, questionnaireId);
        JSONObject result = new JSONObject();
    	result.put("reports",reports);
    	result.put("reportNum",reportService.getUnhandledReportsNumByQuestionnaireId(questionnaireId));
    	response.getWriter().print(result);
    	return null;
    }


    @RequestMapping(value = "allReports", method = RequestMethod.GET)
    public String getAllReportsByPage(HttpServletResponse response, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize) throws Exception {
        //get users
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        List<Report> reports = reportService.getAllReportsBypage(page, pageSize);
        JSONObject result = new JSONObject();
    	result.put("reports",reports);
    	result.put("reportNum",reportService.getAllReportsNum());
    	response.getWriter().print(result);
    	return null;
    }

    //    @ResponseBody
    @RequestMapping(value = "report/{reportId}", method = RequestMethod.POST)
    public String edit(@PathVariable("reportId") Long reportId, Integer status, @RequestParam(value = "questionnaireId", defaultValue = "") String questionnaireId, HttpServletResponse response) throws IOException {
        reportService.setReportStatus(reportId, status);
        JSONObject jsonObject = new JSONObject();
        System.out.print(questionnaireId);
        if (status == 1 && !questionnaireId.equals("")) {
            jsonObject.put("status", "1");
            reportService.setReportHandledByQuestionnaire(questionnaireId);
        } else {
            jsonObject.put("status", "0");
        }
        response.getWriter().print(jsonObject);
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
