package dao;

import model.Report;

import java.util.List;

public interface ReportDao {
	public Long save(Report report);
	
	public void update(Report report);
	
	public void delete(long id);
	
	public List<Report> getAllReports();
	
	public Report getReportById(Long id);
	
	public List<Report> getReportsByQuestionnaireId(String questionnaireId);
	
	public List<Report> getReportsByUserId(Long userId);
	
	public List<Report> getAllReportsBypage(int page, int pageSize);
	
	public List<Report> getAllUnhandledReportsBypage(int page, int pageSize);
	
	public List<Report> getAllUnhandledReportsByQuestionnaireId(int page, int pageSize, String questionnaireId);

	public List<Report> getAllUnhandledReportsByQuestionnaireId(String questionnaireId);

	public List<Report> getAllUnhandledReports(Integer page, Integer pageSize);

	public Long getUnhandledReportsNum();

	public Long getAllReportsNum();

	public Long getUnhandledReportsNumByQuestionnaireId(String questionnaireId);
}
