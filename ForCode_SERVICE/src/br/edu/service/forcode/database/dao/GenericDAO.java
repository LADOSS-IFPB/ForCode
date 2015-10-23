package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Session;

import br.edu.service.forcode.util.JPAUtil;

public abstract class GenericDAO<T> {

	public int insert(T entity) {
		Session session = JPAUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.save(entity);

		session.getTransaction().commit();
		session.close();
		return 0;
	}

	public void update(T entity) {
		Session session = JPAUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.update(entity);

		session.getTransaction().commit();
		session.close();
	}

	public void delete(T entity) {
		Session session = JPAUtil.getSessionFactory().openSession();

		session.beginTransaction();

		session.delete(entity);

		session.getTransaction().commit();
		session.close();
	}

	public abstract List<T> getAll();

	public abstract T getById(Integer pk);
}