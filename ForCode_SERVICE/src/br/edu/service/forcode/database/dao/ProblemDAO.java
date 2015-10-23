package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Problem;
import br.edu.service.forcode.util.JPAUtil;

public class ProblemDAO extends GenericDAO<Problem>{
	
	@Override
	public int insert(Problem entity) {
		super.insert(entity);
		return entity.getIdProblem();
	}
	
	@Override
	public List<Problem> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("Problem.getAll");
		
		@SuppressWarnings("unchecked")
		List<Problem> problems = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return problems;
	}

	@Override
	public Problem getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Problem problem = (Problem) session.get(Problem.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return problem;
	}

}
