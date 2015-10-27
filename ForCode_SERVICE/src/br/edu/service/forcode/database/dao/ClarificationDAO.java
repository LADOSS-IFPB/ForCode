package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.commons.forcode.contests.Contest;
import br.edu.service.forcode.util.JPAUtil;

public class ClarificationDAO extends GenericDAO<Clarification>{
	
	@Override
	public int insert(Clarification entity) {
		super.insert(entity);
		return entity.getIdClarification();
	}
	
	@Override
	public List<Clarification> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("Clarification.getAll");
		
		@SuppressWarnings("unchecked")
		List<Clarification> clarifications = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return clarifications;
	}

	@Override
	public Clarification getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Clarification clarification = (Clarification) session.get(Clarification.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return clarification;
	}
	
	public List<Clarification> getClarificationsByContest(Contest contest){
		//TODO its broken
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Clarification clarification where clarification.user.contest.id = :idContest");
		query.setInteger("idContest", contest.getIdContest());
		
		@SuppressWarnings("unchecked")
		List<Clarification> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}
	
	public List<Clarification> getClarificationsByProblem(Integer idProblem){
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Clarification clarification where clarification.problem.id = :idProblem");
		query.setInteger("idProblem", idProblem);
		
		@SuppressWarnings("unchecked")
		List<Clarification> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}
}
