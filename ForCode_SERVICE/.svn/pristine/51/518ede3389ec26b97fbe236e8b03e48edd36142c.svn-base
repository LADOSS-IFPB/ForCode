package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.TestCaseSample;
import br.edu.service.forcode.util.JPAUtil;

public class TestCaseSampleDAO extends GenericDAO<TestCaseSample>{

	@Override
	public int insert(TestCaseSample entity) {
		super.insert(entity);
		return entity.getIdTestCaseSample();
	}
	
	@Override
	public List<TestCaseSample> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("TestCaseSample.getAll");
		
		@SuppressWarnings("unchecked")
		List<TestCaseSample> testCaseSamples = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return testCaseSamples;
	}

	@Override
	public TestCaseSample getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		TestCaseSample testCaseSample = (TestCaseSample) session.get(TestCaseSample.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return testCaseSample;
	}

	public List<TestCaseSample> getByProblem(Integer idProblem) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
			
		Query query = session.createQuery("from TestCaseSample where problem = :idProblem");
		query.setInteger("idProblem", idProblem);
		
		@SuppressWarnings("unchecked")
		List<TestCaseSample> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}
}
