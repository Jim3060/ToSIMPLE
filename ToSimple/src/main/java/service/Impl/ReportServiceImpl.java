package service.Impl;

import dao.ReportDao;
import model.Report;
import service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService {
    private ReportDao reportDao;

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public int setReportStatus(Long id, int status) {
        Report report = reportDao.getReportById(id);
        report.setStatus(status);
        reportDao.update(report);
        return 1;
    }

    @Override
    public List<Report> getAllReports() {
        // TODO Auto-generated method stub
        return reportDao.getAllReports();
    }

    @Override
    public List<Report> getReportsByQuestionnaireId(String questionnaireId) {
        // TODO Auto-generated method stub
        return reportDao.getReportsByQuestionnaireId(questionnaireId);
    }

    @Override
    public List<Report> getReportsByUserId(long userId) {
        // TODO Auto-generated method stub
        return reportDao.getReportsByUserId(userId);
    }

    @Override
    public int deleteReport(long id) {
        reportDao.delete(id);
        return 1;
    }

    @Override
    public List<Report> getAllReportsBypage(int page, int pageSize) {

        return reportDao.getAllReportsBypage(page, pageSize);
    }

    @Override
    public List<Report> getAllUnhandledReportsBypage(int page, int pageSize) {
        // TODO Auto-generated method stub
        return reportDao.getAllUnhandledReportsBypage(page, pageSize);
    }

    @Override
    public Long getUnhandledReportsNum() {
        // TODO Auto-generated method stub
        return reportDao.getUnhandledReportsNum();
    }

    @Override
    public Long getAllReportsNum() {
        // TODO Auto-generated method stub
        return reportDao.getAllReportsNum();
    }


}
