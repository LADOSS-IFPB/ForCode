package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name="clarificationBean")
@SessionScoped
public class ClarificationBean {
	
	private Clarification clarification = new Clarification();
	private static final Logger logger = LogManager.getLogger(ContestantBean.class.getName());
	private static ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	
	public String showClarifiation(Clarification clarification){
		this.setClarification(clarification);
		return "/webapp/contest/problem.xhtml";
	}
	
	public Clarification getClarification() {
		return clarification;
	}
	
	public void setClarification(Clarification clarification) {
		this.clarification = clarification;
	}
}
