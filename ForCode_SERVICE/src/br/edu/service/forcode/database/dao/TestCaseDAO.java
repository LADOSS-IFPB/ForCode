package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.TestCase;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class TestCaseDAO extends GenericDAO<TestCase> {

	@Override
	public int insert(TestCase entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdTestCase();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TestCase> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<TestCase> testCase;

		try {
			session.beginTransaction();
			query = session.getNamedQuery("TestCase.getAll");
			testCase = query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ TestCase.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return testCase;
	}

	@Override
	public TestCase getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		TestCase testCase;

		try {
			session.beginTransaction();
			testCase = (TestCase) session.get(TestCase.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ TestCase.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return testCase;
	}

}
