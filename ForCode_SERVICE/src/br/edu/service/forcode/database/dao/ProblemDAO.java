package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class ProblemDAO extends GenericDAO<Problem> {

	@Override
	public int insert(Problem entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdProblem();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Problem> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<Problem> problems;

		try {
			session.beginTransaction();

			query = session.getNamedQuery("Problem.getAll");
			problems = query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Problem.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}
		return problems;
	}

	@SuppressWarnings("unchecked")
	public List<Problem> getAllByProblemSetter(Manager problemSetter) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<Problem> problems;

		try {
			session.beginTransaction();

			query = session
					.createQuery("from Problem where fk_id_problem_setter like :id_problem_setter");
			query.setParameter("id_problem_setter", "%" + problemSetter.getIdUser() + "%");

			problems = (List<Problem>) query.list();

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: " + Problem.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return problems;
	}

	@Override
	public Problem getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Problem problem;

		try {
			session.beginTransaction();
			problem = (Problem) session.get(Problem.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + Problem.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return problem;
	}

	@SuppressWarnings("unchecked")
	public Problem getByTitle(String problemTitle) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Problem> problems;
		Problem problem;

		try {
			session.beginTransaction();

			Query query = session.createQuery("from Problem where title like :title");
			query.setParameter("title", "%" + problemTitle + "%");

			problems = (List<Problem>) query.list();

			if (problems.isEmpty()) {
				problem = null;
			} else {
				problem = problems.get(0);
			}

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: " + Problem.class
							+ "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return problem;
	}

}
