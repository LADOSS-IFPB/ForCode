package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.UserContest;
import br.edu.commons.forcode.entities.User;
import br.edu.service.forcode.util.JPAUtil;

public class UserContestDAO extends GenericDAO<UserContest>{

	@Override
	public int insert(UserContest entity) {
		super.insert(entity);
		return entity.getIdUserContest();
	}
	
	@Override
	public List<UserContest> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("UserContest.getAll");
		
		@SuppressWarnings("unchecked")
		List<UserContest> userContests = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return userContests;
	}

	@Override
	public UserContest getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		UserContest userContest = (UserContest) session.get(UserContest.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return userContest;
	}
	
	public List<UserContest> getByUser(User user){
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from UserContest userContest where userContest.user.id = :idUser");
		query.setInteger("idUser", user.getIdUser());
		
		@SuppressWarnings("unchecked")
		List<UserContest> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}
	
	public UserContest getUserContestByContest(Integer idContestant, Integer idContest){
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("from UserContest userContest where userContest.contestant.id = :idContestant and"
				+ " userContest.contest.id = :idContest");
		query.setInteger("idContestant", idContestant);
		query.setInteger("idContest", idContest);

		@SuppressWarnings("unchecked")
		List<UserContest> list = (List<UserContest>)query.list();

		UserContest userContest;
		
		if (list.isEmpty()) {
			userContest = null;
		} else {
			userContest = list.get(0);
		}
		
		session.getTransaction().commit();
		session.close();

		return userContest;
	}
	
}
