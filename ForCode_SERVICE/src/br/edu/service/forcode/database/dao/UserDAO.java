package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class UserDAO extends GenericDAO<User> {

	@Override
	public int insert(User entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdUser();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<User> users;
		Query query;

		try {

			session.beginTransaction();
			query = session.getNamedQuery("User.getAll");
			users = query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + User.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return users;
	}

	@Override
	public User getById(Integer pk) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();
		User user;
		try {
			session.beginTransaction();
			user = (User) session.get(User.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + User.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public User getByUsername(String username) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		User user;
		List<User> users;
		Query query;

		try {
			session.beginTransaction();

			query = session.createQuery("from User where username = :username");
			query.setParameter("username", username);

			users = (List<User>) query.list();

			if (users.isEmpty()) {
				user = null;
			} else {
				user = users.get(0);
			}

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + User.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public User getByEmail(String email) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		User user;
		List<User> users;

		try {
			session.beginTransaction();

			Query query = session.createQuery("from User where email = :email");
			query.setParameter("email", email);

			users = (List<User>) query.list();

			if (users.isEmpty()) {
				user = null;
			} else {
				user = users.get(0);
			}

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + User.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllByTypeUser(String type) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<User> users;

		try {
			session.beginTransaction();

			query = session.createQuery("from User where type_user like :type");
			query.setParameter("type", "%" + type + "%");

			users = (List<User>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + User.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return users;
	}
}
