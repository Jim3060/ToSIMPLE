package service.Impl;

import dao.QuestionnaireDao;
import dao.ReportDao;
import model.Report;
import service.ReportService;

import java.util.List;

public class ReportServiceImpl implements ReportService{
	private ReportDao reportDao;
    private QuestionnaireDao questionnaireDao;

    public ReportDao getReportDao() {
        return reportDao;
    }

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public QuestionnaireDao getQuestionnaireDao() {
        return questionnaireDao;
    }

    public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
        this.questionnaireDao = questionnaireDao;
    }

//    private

	@Override
	public int setReportStatus(Long id, int status) {
		Report report=reportDao.getReportById(id);
		report.setStatus(status);
		reportDao.update(report);
		return 1;
	}

	@Override
	public List<Report> getAllReports() {
		return reportDao.getAllReports();
	}

	@Override
	public List<Report> getReportsByQuestionnaireId(String questionnaireId) {
		return reportDao.getReportsByQuestionnaireId(questionnaireId);
	}

	@Override
	public List<Report> getReportsByUserId(long userId) {
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
		return reportDao.getAllUnhandledReportsBypage(page, pageSize);
	}
	@Override
	public Long getUnhandledReportsNum() {
		return reportDao.getUnhandledReportsNum();
	}
	@Override
	public Long getAllReportsNum() {
		return reportDao.getAllReportsNum();
	}
	@Override
	public List<Report> getAllUnhandledReportsByQuestionnaireId(int page, int pageSize, String questionnaireId) {
		return reportDao.getAllUnhandledReportsByQuestionnaireId(page, pageSize, questionnaireId);
	}
	@Override
	public Long getUnhandledReportsNumByQuestionnaireId(String questionnaireId) {
		return reportDao.getUnhandledReportsNumByQuestionnaireId(questionnaireId);
	}
	@Override
	public int setReportHandledByQuestionnaire(String questionnaireId) {
		List<Report> reports=reportDao.getReportsByQuestionnaireId(questionnaireId);
		Report tmp;
		for (int i=0; i<reports.size();i++){
			tmp=reports.get(i);
			tmp.setStatus(1);
			reportDao.update(tmp);
		}
		return 1;
	}


}
