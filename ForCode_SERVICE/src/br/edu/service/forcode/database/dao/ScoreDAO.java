package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Score;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class ScoreDAO extends GenericDAO<Score> {

	@Override
	public int insert(Score entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdScore();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Score> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Score> scores;
		Query query;

		try {
			session.beginTransaction();
			query = session.getNamedQuery("Score.getAll");
			scores = query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Score.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return scores;
	}

	@Override
	public Score getById(Integer pk) throws ForCodeDataException {
		Session session = JPAUtil.getSessionFactory().openSession();
		Score score;

		try {
			session.beginTransaction();
			score = (Score) session.get(Score.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + Score.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return score;
	}

	@SuppressWarnings("unchecked")
	public List<Score> getByProblem(Problem problem) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<Score> scores;

		try {
			session.beginTransaction();

			query = session.createQuery("from Score where problem.id = :idProblem");
			query.setInteger("idProblem", problem.getIdProblem());
			scores = query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Score.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return scores;
	}

}
