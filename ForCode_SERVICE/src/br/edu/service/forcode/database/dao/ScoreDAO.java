package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Score;
import br.edu.service.forcode.util.JPAUtil;

public class ScoreDAO extends GenericDAO<Score> {

	@Override
	public int insert(Score entity) {
		super.insert(entity);
		return entity.getIdScore();
	}

	@Override
	public List<Score> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("Score.getAll");

		@SuppressWarnings("unchecked")
		List<Score> scores = query.list();

		session.getTransaction().commit();
		session.close();

		return scores;
	}

	@Override
	public Score getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Score score = (Score) session.get(Score.class, pk);

		session.getTransaction().commit();
		session.close();

		return score;
	}

	public List<Score> getByProblem(Problem problem) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Score where problem = :idProblem");
		query.setInteger("idProblem", problem.getIdProblem());
		
		@SuppressWarnings("unchecked")
		List<Score> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}

}
