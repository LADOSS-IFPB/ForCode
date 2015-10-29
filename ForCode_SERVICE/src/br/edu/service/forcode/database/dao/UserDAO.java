package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.entities.User;
import br.edu.service.forcode.util.JPAUtil;

public class UserDAO extends GenericDAO<User> {

	@Override
	public int insert(User entity) {
		super.insert(entity);
		return entity.getIdUser();
	}

	@Override
	public List<User> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("User.getAll");

		@SuppressWarnings("unchecked")
		List<User> users = query.list();

		session.getTransaction().commit();
		session.close();

		return users;
	}

	@Override
	public User getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		User user = (User) session.get(User.class, pk);

		session.getTransaction().commit();
		session.close();

		return user;
	}

	public User getByUsername(String username) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from User where username = :username");
		query.setParameter("username", username);

		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) query.list();

		User user;
		if (list.isEmpty()) {
			user = null;
		} else {
			user = list.get(0);
		}
		session.getTransaction().commit();
		session.close();

		return user;
	}

	public User getByEmail(String email) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("from User where email = :email");
		query.setParameter("email", email);

		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) query.list();

		User user;
		if (list.isEmpty()) {
			user = null;
		} else {
			user = list.get(0);
		}

		session.getTransaction().commit();
		session.close();

		return user;
	}
	
	public List<User> getAllByTypeUser(String type) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from User where type_user like :type");
		query.setParameter("type", "%" + type + "%");

		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) query.list();
		
		session.getTransaction().commit();
		session.close();

		return list;
	}
}
