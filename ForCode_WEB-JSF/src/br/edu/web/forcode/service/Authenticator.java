package br.edu.web.forcode.service;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

import br.edu.commons.forcode.entities.UserKey;

public class Authenticator implements ClientRequestFilter {

	private final UserKey userKey;

    public Authenticator(UserKey userKey) {
        this.userKey = userKey;
    }
    
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
    	MultivaluedMap<String, Object> headers = requestContext.getHeaders();
        headers.add("Authorization", this.userKey.getKey());
    }
}
