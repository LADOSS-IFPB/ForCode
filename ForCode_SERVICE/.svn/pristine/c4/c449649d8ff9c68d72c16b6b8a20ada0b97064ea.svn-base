package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.contests.Language;
import br.edu.service.forcode.util.JPAUtil;

public class LanguageDAO extends GenericDAO<Language>{
	
	@Override
	public int insert(Language entity) {
		super.insert(entity);
		return entity.getIdLanguage();
	}
	
	@Override
	public List<Language> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.getNamedQuery("Language.getAll");
		
		@SuppressWarnings("unchecked")
		List<Language> languages = query.list();
		
		session.getTransaction().commit();
		session.close();
		
		return languages;
	}

	@Override
	public Language getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Language language = (Language) session.get(Language.class, pk);
		
		session.getTransaction().commit();
		session.close();
		
		return language;
	}
}
