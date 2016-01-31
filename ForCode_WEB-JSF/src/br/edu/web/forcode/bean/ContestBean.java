package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.entities.Contestant;
import br.edu.commons.forcode.entities.Manager;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "contestBean")
@ViewScoped
public class ContestBean {

	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	private Contest contest = new Contest();

	public Contest getContest() {
		return contest;
	}

	public String enterContest() {
		Contestant contestant = (Contestant) BeanUtil.getSessionValue("user");
		service.enterContest(contestant, contest.getIdContest());
		BeanUtil.setSessionValue("contest", contest);
		return "problems.xhtml";
	}

	public void showContest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		this.contest = (Contest) service.seachContestById(id).readEntity(Contest.class);
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public void next() {
		if (contest.getIdContest() == null) {
			this.createContest();

		} else {
			this.updateContest();
		}
	}

	public void createContest() {
		this.contest.setContestManager((Manager) BeanUtil.getSessionValue("user"));
		this.contest = (Contest) service.makeContest(this.contest).readEntity(Contest.class);
	}

	public void updateContest() {
		this.contest = (Contest) service.updateContest(this.contest).readEntity(Contest.class);
	}

}
