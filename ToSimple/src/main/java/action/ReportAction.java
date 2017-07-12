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
import service.ReportService;

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
    
    @RequestMapping(value = "report/reportId", method = RequestMethod.PUT)
    public String edit(@PathVariable("reportId") Long reportId, Integer status, HttpServletResponse response) throws IOException {
        reportService.setReportStatus(reportId, status);
        return null;
    }
    
    @RequestMapping(value = "report/reportId", method = RequestMethod.DELETE)
    public String delete(@PathVariable("reportId") Long reportId, HttpServletResponse response) throws IOException {
        reportService.deleteReport(reportId);
        return null;
    }
    
    
}
