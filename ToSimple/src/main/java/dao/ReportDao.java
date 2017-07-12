package dao;

import model.Report;

import java.util.List;

public interface ReportDao {
    Long save(Report report);

    void update(Report report);

    void delete(long id);

    List<Report> getAllReports();

    Report getReportById(Long id);

    List<Report> getReportsByQuestionnaireId(String questionnaireId);

    List<Report> getReportsByUserId(Long userId);

    List<Report> getAllReportsBypage(int page, int pageSize);

    List<Report> getAllUnhandledReportsBypage(int page, int pageSize);

    Long getUnhandledReportsNum();

    Long getAllReportsNum();
}
