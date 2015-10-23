package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.entities.UserKey;
import br.edu.service.forcode.util.JPAUtil;


public class UserKeyDAO extends GenericDAO<UserKey>{
	
	@Override
	public int insert(UserKey entity) {
		super.insert(entity);
		return entity.getId();
	}
	
	@Override
	public List<UserKey> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("UserKey.getAll");
		
		@SuppressWarnings("unchecked")
		List<UserKey> userKey = query.list();

		session.getTransaction().commit();
		session.close();

		return userKey;
	}

	@Override
	public UserKey getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		UserKey userKey = (UserKey)session.get(UserKey.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return userKey;
	}
	
	public UserKey getByUser(User user){
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from UserKey where user = :user"); 
		query.setParameter("user", user); 
		
		@SuppressWarnings("unchecked")
		List<UserKey> list = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		if(list.isEmpty())
			return null;
		
		return list.get(0);
	}

}