package br.edu.web.forcode.bean;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.entities.User;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "listBean")
@ApplicationScoped
public class ListBean {
	
	public ListBean(){}
	ForCodeService service = ProviderServiceFactory.createServiceClient(ForCodeService.class);
	
	/*Get All Users*/
	public List<User> getUsers(){
		return service.listUsers();
	}
	
	/*Get All Contests*/
	public List<Contest> getContests(){
		return service.listContests();
	}
	
	/*Get All Problems*/
	public List<Problem> getProblems(){
		return service.listProblems();
	}
	
	/*Get All Problems of a Manager/Problemsetter*/
	public List<Problem> getProblemsByManager(Manager problemSetter){
		return service.listProblemsByProblemSetter(problemSetter);
	}
	
	/*Get all Clarifications of a Contest*/
	public List<Clarification> getClarifications(){
		Contest contest = (Contest)BeanUtil.getSessionValue("contest");
		return service.listClarifications(contest);
	}
	
	/*Get all Languages*/
	public List<Language> getLanguages(){
		return service.listLanguages();
	}
}
