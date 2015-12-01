package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.TestCaseSample;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class TestCaseSampleDAO extends GenericDAO<TestCaseSample>{

	@Override
	public int insert(TestCaseSample entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdTestCaseSample();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TestCaseSample> getAll() throws ForCodeDataException {
		
		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<TestCaseSample> testCaseSamples;
		
		try{
			session.beginTransaction();
			query = session.getNamedQuery("TestCaseSample.getAll");
			testCaseSamples = query.list();
			session.getTransaction().commit();
			
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ TestCaseSample.class + "Hibernate says: " + hexp.getMessage());
	
		} finally {
			session.close();
		}
		
		return testCaseSamples;
	}

	@Override
	public TestCaseSample getById(Integer pk) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();
		TestCaseSample testCaseSample;
		
		try{
			session.beginTransaction();
			testCaseSample = (TestCaseSample) session.get(TestCaseSample.class, pk);
			session.getTransaction().commit();
			
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ TestCaseSample.class + "Hibernate says: " + hexp.getMessage());
	
		} finally {
			session.close();
		}
		
		return testCaseSample;
	}

	@SuppressWarnings("unchecked")
	public List<TestCaseSample> getByProblem(Integer idProblem) throws ForCodeDataException {
		
		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<TestCaseSample> testCaseSamples;
		
		try{
			session.beginTransaction();
			
			query = session.createQuery("from TestCaseSample where problem.id = :idProblem");
			query.setInteger("idProblem", idProblem);
			testCaseSamples = query.list();
			
			session.getTransaction().commit();
			
		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ TestCaseSample.class + "Hibernate says: " + hexp.getMessage());
	
		} finally {
			session.close();
		}
		
		return testCaseSamples;
	}
}
