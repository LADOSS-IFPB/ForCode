package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.Contestant;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name="contestantBean")
@SessionScoped
public class ContestantBean {
	
	private Contestant contestant = new Contestant();
	private static final Logger logger = LogManager.getLogger(ContestantBean.class.getName());
	private static ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	
	public void createUser(){	
		contestant.setPassword(EncodingUtil.encode(contestant.getPassword()));
		logger.info("Requesting creation of a new contestant");
		
		if(service.createContestant(contestant).getStatus() == Response.Status.OK.getStatusCode()){
			logger.info("Contestant creation successfull");
		}
	}
	
	public String updateContestant(){
		contestant = (Contestant)BeanUtil.getSessionValue("user");
		logger.info("Requesting update of a contestant");
		
		service.updateContestant(contestant);
		logger.info("Contestant update successfull");
		
		return "home.xhtml?faces-redirect=true";
	}
	
	public Contestant getContestant() {
		return contestant;
	}

	public void setContestant(Contestant contestant) {
		this.contestant = contestant;
	}
	
}
