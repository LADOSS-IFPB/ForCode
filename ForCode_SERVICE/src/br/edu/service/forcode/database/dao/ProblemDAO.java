package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.entities.Manager;
import br.edu.service.forcode.util.JPAUtil;

public class ProblemDAO extends GenericDAO<Problem> {

	@Override
	public int insert(Problem entity) {
		super.insert(entity);
		return entity.getIdProblem();
	}

	@Override
	public List<Problem> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("Problem.getAll");

		@SuppressWarnings("unchecked")
		List<Problem> problems = query.list();

		session.getTransaction().commit();
		session.close();

		return problems;
	}

	public List<Problem> getAllByProblemSetter(Manager problemSetter) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from Problem where fk_id_problem_setter like :id_problem_setter");
		query.setParameter("id_problem_setter", "%" + problemSetter.getIdUser()
				+ "%");

		@SuppressWarnings("unchecked")
		List<Problem> list = (List<Problem>) query.list();

		session.getTransaction().commit();
		session.close();

		return list;
	}

	@Override
	public Problem getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Problem problem = (Problem) session.get(Problem.class, pk);

		session.getTransaction().commit();
		session.close();

		return problem;
	}

	public Problem getByTitle(String problemTitle) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session
				.createQuery("from Problem where title like :title");
		query.setParameter("title", "%" + problemTitle + "%");

		@SuppressWarnings("unchecked")
		List<Problem> list = (List<Problem>) query.list();

		Problem problem;

		if (list.isEmpty()) {
			problem = null;
		} else {
			problem = list.get(0);
		}

		session.getTransaction().commit();
		session.close();
		return problem;
	}

}
