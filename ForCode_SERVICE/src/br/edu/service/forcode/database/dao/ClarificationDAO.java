package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class ClarificationDAO extends GenericDAO<Clarification> {

	@Override
	public int insert(Clarification entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdClarification();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Clarification> getAll() throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();
		List<Clarification> clarifications;
		Query query;

		try {
			session.beginTransaction();

			query = session.getNamedQuery("Clarification.getAll");
			clarifications = (List<Clarification>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ Clarification.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return clarifications;
	}

	@Override
	public Clarification getById(Integer pk) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();
		Clarification clarification;

		try {
			session.beginTransaction();
			clarification = (Clarification) session.get(Clarification.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ Clarification.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return clarification;
	}

	public List<Clarification> getClarificationsByContest(Contest contest) {
		// TODO its broken
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from Clarification clarification where clarification.user.contest.id = :idContest");
		query.setInteger("idContest", contest.getIdContest());

		@SuppressWarnings("unchecked")
		List<Clarification> list = query.list();

		session.getTransaction().commit();
		session.close();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Clarification> getClarificationsByProblem(Integer idProblem)
			throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<Clarification> clarifications;

		try {
			session.beginTransaction();

			query = session
					.createQuery("from Clarification clarification where clarification.problem.id = :idProblem");
			query.setInteger("idProblem", idProblem);

			clarifications = (List<Clarification>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ Clarification.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return clarifications;
	}
}
