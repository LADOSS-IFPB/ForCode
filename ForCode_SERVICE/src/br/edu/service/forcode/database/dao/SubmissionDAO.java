package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Submission;
import br.edu.service.forcode.util.JPAUtil;

public class SubmissionDAO extends GenericDAO<Submission>{

	@Override
	public int insert(Submission entity) {
		super.insert(entity);
		return entity.getIdSubmission();
	}
	
	@Override
	public List<Submission> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("Submission.getAll");
		
		@SuppressWarnings("unchecked")
		List<Submission> submissions = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return submissions;
	}

	@Override
	public Submission getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Submission submission = (Submission) session.get(Submission.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return submission;
	}
	
	public List<Submission> getSubmissionsByContest(Contest contest){
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Submission where idContest = :idContest");
		query.setInteger("idContest", contest.getIdContest());
		
		@SuppressWarnings("unchecked")
		List<Submission> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}

}
