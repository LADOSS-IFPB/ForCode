package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.Admin;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@SessionScoped
@ManagedBean(name="managerBean")
public class ManagerBean {
	
	private Manager manager = new Manager();
	private static final Logger logger = LogManager.getLogger(ManagerBean.class.getName());
	private static ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	
	public void createManager(){
		manager.setCreatedBy((Admin)(BeanUtil.getSessionValue("user")));
		manager.setPassword(EncodingUtil.encode(manager.getPassword()));
		logger.info("Requesting creation of a new manager");
		service.createManager(manager);
		logger.info("Manager creation successfull");
	}
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
}
