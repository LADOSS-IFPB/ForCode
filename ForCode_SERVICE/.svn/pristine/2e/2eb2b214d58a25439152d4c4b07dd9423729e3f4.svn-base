package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.TestCase;
import br.edu.service.forcode.util.JPAUtil;

public class TestCaseDAO extends GenericDAO<TestCase>{

	@Override
	public int insert(TestCase entity) {
		super.insert(entity);
		return entity.getIdTestCase();
	}
	
	@Override
	public List<TestCase> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("TestCase.getAll");
		
		@SuppressWarnings("unchecked")
		List<TestCase> testCase = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return testCase;
	}

	@Override
	public TestCase getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		TestCase testCase = (TestCase) session.get(TestCase.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return testCase;
	}

}
