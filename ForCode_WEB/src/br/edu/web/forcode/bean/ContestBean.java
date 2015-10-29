package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.entities.Contestant;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "contestBean")
@RequestScoped
public class ContestBean {
	
	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);
	
	private Contest contest = new Contest();

	public Contest getContest() {
		return contest;
	}

	public String enterContest(){
		Contestant contestant = (Contestant)BeanUtil.getSessionValue("user");
		service.enterContest(contestant, contest.getIdContest());
		BeanUtil.setSessionValue("contest", contest);
		return "problems.xhtml";
	}
	
	public void showContest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id")); 
		this.contest = (Contest)service.seachContestById(id).readEntity(Contest.class);
	}
	
	
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
}
