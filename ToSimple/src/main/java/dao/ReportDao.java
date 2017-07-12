package dao;

import java.util.List;

import model.Report;

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
	
	public Long getUnhandledReportsNum();
	
	public Long getAllReportsNum();
}
