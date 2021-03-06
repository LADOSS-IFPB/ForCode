package br.edu.web.forcode.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.Institution;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "institutionBean")
public class InstitutionBean {
	
	private Institution institution = new Institution();
	private static ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	private static final Logger logger = LogManager.getLogger(InstitutionBean.class.getName());
	
	public void createInstitution(){
		logger.info("Requesting creation of a new institution");
		service.createInstitution(institution);
		logger.info("Institution creation successfull");
	}
	
	public List<Institution> getAllInstitutions(){
		return service.listInstitutions();
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}
}
