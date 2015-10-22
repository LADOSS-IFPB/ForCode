package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Contest;
import br.edu.service.forcode.util.JPAUtil;

public class ContestDAO extends GenericDAO<Contest>{
	
	@Override
	public int insert(Contest entity) {
		super.insert(entity);
		return entity.getIdContest();
	}
	
	@Override
	public List<Contest> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("Contest.getAll");
		
		@SuppressWarnings("unchecked")
		List<Contest> contests = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return contests;
	}

	@Override
	public Contest getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Contest contest = (Contest) session.get(Contest.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return contest;
	}
	
	public List<Contest> getByName(String name){
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Contest where name like :name");
		query.setString("name", "%" + name + "%");
		
		@SuppressWarnings("unchecked")
		List<Contest> list = (List<Contest>) query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return list;
	}
}
