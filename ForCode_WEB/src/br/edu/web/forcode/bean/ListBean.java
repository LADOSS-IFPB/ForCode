package br.edu.web.forcode.bean;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.entities.User;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "listBean")
@ApplicationScoped
public class ListBean {
	
	public ListBean(){}
	ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	
	public List<User> getUsers(){
		return service.listUsers();
	}
	
	public List<Contest> getContests(){
		return service.listContests();
	}
	
	public List<Problem> getProblems(){
		return service.listProblems();
	}
	
}
