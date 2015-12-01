package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.UserContest;
import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class UserContestDAO extends GenericDAO<UserContest> {

	@Override
	public int insert(UserContest entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdUserContest();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserContest> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<UserContest> userContests;

		try {
			session.beginTransaction();

			query = session.getNamedQuery("UserContest.getAll");
			userContests = query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ UserContest.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return userContests;
	}

	@Override
	public UserContest getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		UserContest userContest;

		try {
			session.beginTransaction();
			userContest = (UserContest) session.get(UserContest.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ UserContest.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return userContest;
	}

	@SuppressWarnings("unchecked")
	public List<UserContest> getByUser(User user) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<UserContest> userContests;

		try {
			session.beginTransaction();

			query = session
					.createQuery("from UserContest userContest where userContest.user.id = :idUser");
			query.setInteger("idUser", user.getIdUser());
			userContests = query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ UserContest.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return userContests;
	}

	@SuppressWarnings("unchecked")
	public UserContest getUserContestByContest(Integer idContestant, Integer idContest)
			throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<UserContest> userContests;
		UserContest userContest;

		try {
			session.beginTransaction();

			query = session
					.createQuery("from UserContest userContest where userContest.contestant.id = :idContestant and"
							+ " userContest.contest.id = :idContest");

			query.setInteger("idContestant", idContestant);
			query.setInteger("idContest", idContest);

			userContests = (List<UserContest>) query.list();

			if (userContests.isEmpty()) {
				userContest = null;
			} else {
				userContest = userContests.get(0);
			}

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ UserContest.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return userContest;
	}

}
