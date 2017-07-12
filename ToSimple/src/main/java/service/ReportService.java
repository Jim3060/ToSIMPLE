package service;

import model.Report;

import java.util.List;

public interface ReportService {
    int setReportStatus(Long id, int status);

    List<Report> getAllReports();

    List<Report> getAllReportsBypage(int page, int pageSize);

    List<Report> getAllUnhandledReportsBypage(int page, int pageSize);

    List<Report> getReportsByQuestionnaireId(String questionnaireId);

    List<Report> getReportsByUserId(long userid);

    int deleteReport(long id);

    Long getUnhandledReportsNum();

    Long getAllReportsNum();
}
