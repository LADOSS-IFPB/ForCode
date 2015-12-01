package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public abstract class GenericDAO<T> {

	public int insert(T entity) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to save entity from class: "
							+ entity.getClass() + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}
		return 0;
	}

	public void update(T entity) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.merge(entity);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to merge entity from class: "
							+ entity.getClass() + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}
	}

	public void delete(T entity) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();

		try {
			session.beginTransaction();
			session.delete(entity);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to delete entity from class: "
							+ entity.getClass() + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}
	}

	public abstract List<T> getAll() throws ForCodeDataException;

	public abstract T getById(Integer pk) throws ForCodeDataException;
}