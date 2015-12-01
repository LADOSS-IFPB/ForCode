package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class ContestDAO extends GenericDAO<Contest> {

	@Override
	public int insert(Contest entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdContest();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Contest> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Contest> contests;

		try {
			session.beginTransaction();
			Query query = session.getNamedQuery("Contest.getAll");
			contests = (List<Contest>) query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Contest.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.clear();
			session.close();
		}

		return contests;
	}

	@Override
	public Contest getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Contest contest;

		try {
			session.beginTransaction();
			contest = (Contest) session.get(Contest.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + Contest.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return contest;
	}

	@SuppressWarnings("unchecked")
	public List<Contest> getByName(String name) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<Contest> contests;

		try {
			session.beginTransaction();

			query = session.createQuery("from Contest where name like :name");
			query.setString("name", "%" + name + "%");
			contests = (List<Contest>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Contest.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return contests;
	}

	@SuppressWarnings("unchecked")
	public List<Contest> getAllByProblemSetter(Manager problemSetter) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Contest> list;
		Query query;

		try {
			session.beginTransaction();

			query = session
					.createQuery("from Contest where fk_id_contest_manager like :id_problem_setter");
			query.setParameter("id_problem_setter", "%" + problemSetter.getIdUser() + "%");
			list = (List<Contest>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Contest.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return list;
	}
}
