package br.edu.service.forcode.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.database.dao.InstitutionDAO;
import br.edu.service.forcode.database.dao.UserDAO;
import br.edu.service.forcode.services.UserService;

public class ForCodeValidator {
	
	private static final Logger logger = LogManager
			.getLogger(UserService.class.getName());
	
	public static ForCodeError validateInsertion(User user) {
		UserDAO userDao = new UserDAO();
		try{
			if (userDao.getByUsername(user.getUsername()) != null) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.DUPLICATE_USERNAME);
				error.setMessage(error.getMessage() + " '" + user.getUsername() + "'");
				
				return error;
			}
		}catch(ForCodeDataException fde){
			//TODO add an error for this.
			logger.warn(fde.getMessage());
			return null;
		}
		try{
			if (userDao.getByEmail(user.getEmail()) != null) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.DUPLICATE_EMAIL);
				error.setMessage(error.getMessage() + " '" + user.getEmail() + "'");
				
				return error;
			}
		}catch(ForCodeDataException fde){
			//TODO add an error for this.
			logger.warn(fde.getMessage());
			return null;
		}
		
		return null;
	}
	
	public static ForCodeError validateUpdate(User user) {
		UserDAO userDao = new UserDAO();
		try{
			User aux = userDao.getByEmail(user.getEmail()); 
			if (aux != null && !aux.getUsername().equals(user.getUsername())) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.DUPLICATE_EMAIL);
				error.setMessage(error.getMessage() + " '" + user.getEmail() + "'");
				
				return error;
			}
		}catch(ForCodeDataException fde){
			//TODO add an error for this.
			logger.warn(fde.getMessage());
			return null;
		}
		
		return null;
	}

	
	public static ForCodeError validate(Institution institution) {
		InstitutionDAO institutionDao = new InstitutionDAO();
		try{
			if (institutionDao.getByName(institution.getName()) != null) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.DUPLICATE_USERNAME);
				error.setMessage(error.getMessage() + " '" + institution.getName() + "'");
				
				return error;
			}
		}catch(ForCodeDataException fde){
			//TODO add an error for this.
			logger.warn(fde.getMessage());
			return null;
		}
		
		return null;
	}
}