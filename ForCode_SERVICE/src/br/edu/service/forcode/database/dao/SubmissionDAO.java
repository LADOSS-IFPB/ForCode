package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class SubmissionDAO extends GenericDAO<Submission> {

	@Override
	public int insert(Submission entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdSubmission();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Submission> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Submission> submissions;
		Query query;

		try {
			session.beginTransaction();

			query = session.getNamedQuery("Submission.getAll");
			submissions = (List<Submission>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ Submission.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return submissions;
	}

	@Override
	public Submission getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Submission submission;

		try {
			session.beginTransaction();
			submission = (Submission) session.get(Submission.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ Submission.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return submission;
	}

	@SuppressWarnings("unchecked")
	public List<Submission> getSubmissionsByContest(Contest contest) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Submission> submissions;
		Query query;

		try {
			session.beginTransaction();

			query = session.createQuery("from Submission where idContest = :idContest");
			query.setInteger("idContest", contest.getIdContest());
			submissions = query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ Submission.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return submissions;
	}

}
