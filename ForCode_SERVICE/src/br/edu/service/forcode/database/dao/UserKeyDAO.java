package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.entities.UserKey;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class UserKeyDAO extends GenericDAO<UserKey> {

	@Override
	public int insert(UserKey entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getId();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserKey> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<UserKey> userKey;

		try {
			session.beginTransaction();

			query = session.getNamedQuery("UserKey.getAll");
			userKey = query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + UserKey.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return userKey;
	}

	@Override
	public UserKey getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		UserKey userKey;

		try {
			session.beginTransaction();
			userKey = (UserKey) session.get(UserKey.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + UserKey.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return userKey;
	}

	public UserKey getByUser(User user) throws ForCodeDataException {
		return null;// TODO
		// Session session = JPAUtil.getSessionFactory().openSession();
		// session.beginTransaction();
		//
		// Query query = session.createQuery("from UserKey where user = :user");
		// query.setParameter("user", user);
		//
		// @SuppressWarnings("unchecked")
		// List<UserKey> list = query.list();
		//
		// session.getTransaction().commit();
		// session.close();
		//
		// if(list.isEmpty())
		// return null;
		//
		// return list.get(0);
	}

}