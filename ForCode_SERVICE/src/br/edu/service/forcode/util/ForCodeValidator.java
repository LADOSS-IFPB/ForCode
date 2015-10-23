package br.edu.service.forcode.util;

import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.entities.User;
import br.edu.service.forcode.database.dao.InstitutionDAO;
import br.edu.service.forcode.database.dao.UserDAO;

public class ForCodeValidator {
	public static ForCodeError validateInsertion(User user) {
		UserDAO userDao = new UserDAO();

		if (userDao.getByUsername(user.getUsername()) != null) {
			ForCodeError error = ErrorFactory
					.getErrorFromIndex(ErrorFactory.DUPLICATE_USERNAME);
			error.setMessage(error.getMessage() + " '" + user.getUsername() + "'");
			
			return error;
		}
		
		if (userDao.getByEmail(user.getEmail()) != null) {
			ForCodeError error = ErrorFactory
					.getErrorFromIndex(ErrorFactory.DUPLICATE_EMAIL);
			error.setMessage(error.getMessage() + " '" + user.getEmail() + "'");
			
			return error;
		}
		
		return null;
	}
	
	public static ForCodeError validateUpdate(User user) {
		UserDAO userDao = new UserDAO();
		User aux = userDao.getByEmail(user.getEmail()); 
		if (aux != null && !aux.getUsername().equals(user.getUsername())) {
			ForCodeError error = ErrorFactory
					.getErrorFromIndex(ErrorFactory.DUPLICATE_EMAIL);
			error.setMessage(error.getMessage() + " '" + user.getEmail() + "'");
			
			return error;
		}
		
		return null;
	}

	
	public static ForCodeError validate(Institution institution) {
		InstitutionDAO institutionDao = new InstitutionDAO();

		if (institutionDao.getByName(institution.getName()) != null) {
			ForCodeError error = ErrorFactory
					.getErrorFromIndex(ErrorFactory.DUPLICATE_USERNAME);
			error.setMessage(error.getMessage() + " '" + institution.getName() + "'");
			
			return error;
		}
		
		return null;
	}
}