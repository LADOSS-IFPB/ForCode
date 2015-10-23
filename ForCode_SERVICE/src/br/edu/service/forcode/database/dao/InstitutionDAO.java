package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.entities.Institution;
import br.edu.service.forcode.util.JPAUtil;

public class InstitutionDAO extends GenericDAO<Institution> {

	@Override
	public int insert(Institution entity) {
		super.insert(entity);
		return entity.getIdInstitution();
	}

	@Override
	public List<Institution> getAll() {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.getNamedQuery("Institution.getAll");

		@SuppressWarnings("unchecked")
		List<Institution> institutions = query.list();

		session.getTransaction().commit();
		session.close();

		return institutions;
	}

	@Override
	public Institution getById(Integer pk) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Institution institutions = (Institution) session.get(Institution.class,
				pk);

		session.getTransaction().commit();
		session.close();

		return institutions;
	}
	
	public Institution getByName(String name) {
		Session session = JPAUtil.getSessionFactory().openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Institution where name = :name");
		query.setParameter("name", name);

		@SuppressWarnings("unchecked")
		List<Institution> list = (List<Institution>)query.list();

		Institution institution;
		
		if (list.isEmpty()) {
			institution = null;
		} else {
			institution = list.get(0);
		}
		
		session.getTransaction().commit();
		session.close();

		return institution;
	}
	
}
