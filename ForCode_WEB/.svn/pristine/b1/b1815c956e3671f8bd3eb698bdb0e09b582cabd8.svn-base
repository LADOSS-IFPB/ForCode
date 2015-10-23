package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.entities.Admin;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name="adminBean")
public class AdminBean {
	
	private Admin admin = new Admin();
	private static final Logger logger = LogManager.getLogger(AdminBean.class.getName());
	private static ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	
	public void createAdmin(){
		admin.setPassword(EncodingUtil.encode(admin.getPassword()));
		logger.info("Requesting creation of a new Admin");
		service.insertAdmin(admin);
		logger.info("Admin creation successfull");
	}
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
}
