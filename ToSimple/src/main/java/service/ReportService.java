package service;

import model.Report;

import java.util.List;

public interface ReportService {
    public int setReportStatus(Long id, int status);

    public List<Report> getAllReports();

    public List<Report> getAllReportsBypage(int page, int pageSize);

    public List<Report> getAllUnhandledReportsBypage(int page, int pageSize);

    public List<Report> getReportsByQuestionnaireId(String questionnaireId);

    public List<Report> getReportsByUserId(long userid);

    public List<Report> getAllUnhandledReportsByQuestionnaireId(int page, int pageSize, String questionnaireId);

    public int deleteReport(long id);

    public Long getUnhandledReportsNum();

    public Long getAllReportsNum();

    public Long getUnhandledReportsNumByQuestionnaireId(String questionnaireId);

    public int setReportHandledByQuestionnaire(String questionnaireId);

    /**
     * save the report.
     *
     * @param report
     * @return
     */
    public Long save(Report report);
}
