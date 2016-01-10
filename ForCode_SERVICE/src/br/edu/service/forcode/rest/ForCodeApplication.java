package br.edu.service.forcode.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import br.edu.service.forcode.services.ContestService;
import br.edu.service.forcode.services.ForCodeUploadService;
import br.edu.service.forcode.services.ListService;
import br.edu.service.forcode.services.ProblemService;
import br.edu.service.forcode.services.UserService;

public class ForCodeApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public ForCodeApplication() {
		
		CorsFilter filter = new CorsFilter();
		filter.getAllowedOrigins().add("*");
		filter.setAllowedMethods("POST, GET, DELETE, PUT, OPTIONS");
		filter.setAllowedHeaders("Content-Type");
		
		this.singletons.add(filter);
		this.singletons.add(new ProblemService());
		this.singletons.add(new ContestService());
		this.singletons.add(new ListService());
		this.singletons.add(new UserService());
		this.singletons.add(new ForCodeUploadService());
	}

	public Set<Class<?>> setSingletons() {
		return this.empty;
	}

	public Set<Object> getSingletons() {
		return this.singletons;
	}
}
