package br.edu.service.forcode.rest.security;

import java.util.Random;
import java.util.StringTokenizer;

import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.entities.UserKey;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.commons.forcode.util.StringUtil;
import br.edu.service.forcode.database.dao.UserDAO;
import br.edu.service.forcode.database.dao.UserKeyDAO;

public class Authorizator {

	public UserKey generateKey(User user){	
    	
    	UserKeyDAO userKeyDAO = new UserKeyDAO();
    	UserDAO userDAO = new UserDAO();
    	
    	UserKey userKey = userKeyDAO.getByUser(user);
    	
    	if(userKey == null){
    		Random rand = new Random();
    		String key = EncodingUtil.encode("" + rand.nextInt((999999999 - 0) + 1));
    		
	    	userKey = new UserKey();
	    	
    		userKey.setKey(key);
	    	
	    	userKey = userKeyDAO.getById(userKeyDAO.insert(userKey));
	    	userKey.setKey(EncodingUtil.encode(userKey.getId() + ":" + user.getIdUser() + ":" + EncodingUtil.decode(userKey.getKey())));
	    	
	    	userKeyDAO.update(userKey);
	    	
	    	user.setUserKey(userKey);
	    	userDAO.update(user);
    	}
    	
    	return userKey;
	}

	public void deleteKey(User user) {
		UserKeyDAO userKeyDAO = new UserKeyDAO();
		userKeyDAO.delete(user.getUserKey());
		
		UserDAO userDAO = new UserDAO();
		user.setUserKey(null);
		userDAO.update(user);
	}

	protected boolean isAuthorized(String[] authorizedUsers, String key) {
		key = EncodingUtil.decode(key);
		StringTokenizer tokenizer = new StringTokenizer(key, ":");

		String keyId = tokenizer.nextToken();
		String userId = tokenizer.nextToken();
		
		if (!StringUtil.isNum(keyId) || !StringUtil.isNum(userId))
			return false;
		
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
		
	}
}
