package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class LanguageDAO extends GenericDAO<Language> {

	@Override
	public int insert(Language entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdLanguage();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Language> getAll() throws ForCodeDataException {
		
		Session session = JPAUtil.getSessionFactory().openSession();
		List<Language> languages;
		Query query;
		
		try{
			session.beginTransaction();	
			query = session.getNamedQuery("Language.getAll");
			languages = query.list();
			session.getTransaction().commit();
			
		}catch(HibernateException hexp){
			session.getTransaction().rollback();
			throw new ForCodeDataException("There was a problem while trying to get entities from class: " + Language.class + "Hibernate says: " + hexp.getMessage());
		
		}finally{
			session.close();
		}

		return languages;
	}

	@Override
	public Language getById(Integer pk) throws ForCodeDataException {
		
		Session session = JPAUtil.getSessionFactory().openSession();
		Language language;
		
		try{
			session.beginTransaction();	
			language = (Language) session.get(Language.class, pk);
			session.getTransaction().commit();
			
		}catch(HibernateException hexp){
			session.getTransaction().rollback();
			throw new ForCodeDataException("There was a problem while trying to get entity from class: " + Language.class + "Hibernate says: " + hexp.getMessage());
		
		}finally{
			session.close();
		}

		return language;
	}

	@SuppressWarnings("unchecked")
	public Language getByName(String languageName) throws ForCodeDataException {
		
		Session session = JPAUtil.getSessionFactory().openSession();
		List<Language> languages;
		Query query;
		Language language;
		
		try{
			session.beginTransaction();	
			query = session.createQuery("from Language where name like :name");
			query.setString("name", "%" + languageName + "%");
	
			languages = (List<Language>) query.list();
			language = null;
	
			if (!languages.isEmpty()) {
				language = languages.get(0);
			}
	
			session.getTransaction().commit();
			
		}catch(HibernateException hexp){
			session.getTransaction().rollback();
			throw new ForCodeDataException("There was a problem while trying to get entity from class: " + Language.class + "Hibernate says: " + hexp.getMessage());
		
		}finally{
			session.close();
		}

		return language;
	}
}
