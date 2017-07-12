package dao.Impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.ReportDao;
import model.Report;
import model.User;

public class ReportDaoImpl extends HibernateDaoSupport implements ReportDao{

	public Long save(Report report) {
		return (Long) getHibernateTemplate().save(report);
		
	}

	public void update(Report report) {
		getHibernateTemplate().merge(report);
		
		
	}


	public void delete(long id ) {
		getHibernateTemplate().find("delete from Report as u where u.id=?", id);
		
	}

	
	public List<Report> getAllReports() {
		@SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) getHibernateTemplate()
                .find("from Report");
        return reports;
	}
	
	public Report getReportById(Long id){
		@SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) getHibernateTemplate().find(
                "from Report as u where u.id=?", id);
       	Report report = reports.size() > 0 ? reports.get(0) : null;
        return report;
	}

	@Override
	public List<Report> getReportsByQuestionnaireId(String questionnaireId) {
		@SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) getHibernateTemplate().find(
                "from Report as u where u.questionnaireId=?", questionnaireId);
       	
		return reports;
	}

	@Override
	public List<Report> getReportsByUserId(Long userId) {
		@SuppressWarnings("unchecked")
        List<Report> reports = (List<Report>) getHibernateTemplate().find(
                "from Report as u where u.userId=?", userId);
		return reports;
	}

	@Override
	public List<Report> getAllReportsBypage(int page , int pageSize) {
		Session session = this.getSession();
        session.beginTransaction(); 
        List<Report> list=session.createQuery("from Report ").setMaxResults(pageSize).setFirstResult(page*pageSize).list();
        session.getTransaction().commit();
		return list;
		
	}

	@Override
	public List<Report> getAllUnhandledReportsBypage(int page, int pageSize) {
		Session session = this.getSession();
        session.beginTransaction(); 
        List<Report> list=session.createQuery("from Report as r where r.status=0").setMaxResults(pageSize).setFirstResult(page*pageSize).list();
        session.getTransaction().commit();
		return list;
		
	}

	@Override
	public Long getUnhandledReportsNum() {
		@SuppressWarnings("unchecked")
        Long num =  (Long)getHibernateTemplate().find("select count(*) from Report as u where u.status=0").listIterator().next();
       
		return num;
	}

	@Override
	public Long getAllReportsNum() {
		Long num =  (Long)getHibernateTemplate().find("select count(*) from Report ").listIterator().next();
	       
		return num;
	}

}