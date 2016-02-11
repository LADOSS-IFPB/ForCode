package br.edu.service.forcode.rest.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethodInvoker;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements ContainerRequestFilter {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	
	static final Logger logger = LogManager.getLogger(SecurityInterceptor.class.getName());
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		//TODO Fix bugs with UserKey
		
		/*ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		
		if (!method.isAnnotationPresent(PermitAll.class)) {
			
			if(method.isAnnotationPresent(DenyAll.class)){
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				return;
			}
			
			if(method.isAnnotationPresent(RolesAllowed.class)){
				
				RolesAllowed annotation = method.getAnnotation(RolesAllowed.class);
				
				final MultivaluedMap<String, String> headers = requestContext.getHeaders();
				final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
	
				if (authorization == null || authorization.isEmpty()) {
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
					return;
				}
				
				final String key = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
				
				Authorizator authorizator = new Authorizator();
				
				if(!authorizator.isAuthorized(annotation.value(), key)){
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
					logger.info("Someone is unauthorized");
					
					return;
				}
			}
		}*/
	}
}
