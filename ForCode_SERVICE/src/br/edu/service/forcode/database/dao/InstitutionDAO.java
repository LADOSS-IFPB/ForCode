package br.edu.service.forcode.database.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.util.JPAUtil;

public class InstitutionDAO extends GenericDAO<Institution> {

	@Override
	public int insert(Institution entity) throws ForCodeDataException {
		super.insert(entity);
		return entity.getIdInstitution();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Institution> getAll() throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Query query;
		List<Institution> institutions;

		try {
			session.beginTransaction();
			query = session.getNamedQuery("Institution.getAll");
			institutions = query.list();
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entities from class: "
							+ Institution.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return institutions;
	}

	@Override
	public Institution getById(Integer pk) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		Institution institutions;

		try {

			session.beginTransaction();
			institutions = (Institution) session.get(Institution.class, pk);
			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ Institution.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return institutions;
	}

	@SuppressWarnings("unchecked")
	public Institution getByName(String name) throws ForCodeDataException {

		Session session = JPAUtil.getSessionFactory().openSession();
		List<Institution> institutions;
		Institution institution;
		Query query;

		try {
			session.beginTransaction();

			query = session.createQuery("from Institution where name = :name");
			query.setParameter("name", name);

			institutions = (List<Institution>) query.list();

			if (institutions.isEmpty()) {
				institution = null;
			} else {
				institution = institutions.get(0);
			}

			session.getTransaction().commit();

		} catch (HibernateException hexp) {
			session.getTransaction().rollback();
			throw new ForCodeDataException(
					"There was a problem while trying to get entity from class: "
							+ Institution.class + "Hibernate says: " + hexp.getMessage());

		} finally {
			session.close();
		}

		return institution;
	}

}
