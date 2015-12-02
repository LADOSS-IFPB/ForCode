package br.edu.service.forcode.rest.security;

import java.util.Random;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.entities.UserKey;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.commons.forcode.util.StringUtil;
import br.edu.service.forcode.database.dao.UserDAO;
import br.edu.service.forcode.database.dao.UserKeyDAO;
import br.edu.service.forcode.services.UserService;

public class Authorizator {

	private static final Logger logger = LogManager
			.getLogger(UserService.class.getName());
	
	public UserKey generateKey(User user){	
    	
    	UserKeyDAO userKeyDAO = new UserKeyDAO();
    	UserDAO userDAO = new UserDAO();
    	UserKey userKey;
    	
    	try{
    		userKey = userKeyDAO.getByUser(user);
    	}catch(ForCodeDataException fde){
			//TODO add an error to this.
			return null;
		}
    	
    	if(userKey == null){
    		Random rand = new Random();
    		userKey = new UserKey();
    		
    		String key = EncodingUtil.encode("" + rand.nextInt((999999999 - 0) + 1));
    		
    		try{
	    		
	    		userKey.setKey(key);
		    	
		    	userKeyDAO.insert(userKey);
		    	
		    	userKey.setKey(EncodingUtil.encode(userKey.getId() + ":" + user.getIdUser() + ":" + EncodingUtil.decode(userKey.getKey())));
		    	
		    	userKeyDAO.update(userKey);
		    	
		    	user.setUserKey(userKey);
		    	userDAO.update(user);
    		}catch(ForCodeDataException fde){
    			//TODO add an error to this.
    			return null;
    		}
    	}
    	
    	return userKey;
	}

	public void deleteKey(User user) {
		UserKeyDAO userKeyDAO = new UserKeyDAO();
		try{
			userKeyDAO.delete(user.getUserKey());
			
			UserDAO userDAO = new UserDAO();
			user.setUserKey(null);
			userDAO.update(user);
		}catch(ForCodeDataException fde){
			//TODO add an error to this.
			logger.warn(fde.getMessage());
		}
	}

	protected boolean isAuthorized(String[] authorizedUsers, String key) {
		key = EncodingUtil.decode(key);
		StringTokenizer tokenizer = new StringTokenizer(key, ":");

		String keyId = tokenizer.nextToken();
		String userId = tokenizer.nextToken();
		
		if (!StringUtil.isNum(keyId) || !StringUtil.isNum(userId))
			return false;
		try{
			boolean roleAccepted = false;
			User user = new UserDAO().getById(Integer.parseInt(userId));
	
			for (String s : authorizedUsers) {
				if (s.equalsIgnoreCase(user.getTypeUser().getTypeName())) {
					roleAccepted = true;
					break;
				}
			}
			
			UserKey userKey = new UserKeyDAO().getById(Integer.parseInt(keyId));
			
			if (userKey != null && userKey.getKey().equals(EncodingUtil.encode(key))
					&& roleAccepted)
				return true;
			else
				return false;
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return false;
		}
		
	}
}
