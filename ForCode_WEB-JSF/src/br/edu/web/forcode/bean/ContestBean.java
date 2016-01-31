package br.edu.web.forcode.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Problem;
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
	private static final Logger logger = LogManager.getLogger(ContestBean.class.getName());

	private Contest contest = new Contest();
	
	private Map<Integer, Boolean> checkedProblems = new HashMap<Integer, Boolean>();

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
			ArrayList<Problem> values = new ArrayList<Problem>();
			
			
			for(Map.Entry<Integer, Boolean> entry: checkedProblems.entrySet()){
				if(entry.getValue()){
					values.add(this.getProblem(entry.getKey()));
				}
			}
			
			logger.info("Adding " + values.size() + " problems to contest #" + contest.getIdContest());
			 
			this.contest.setProblems(values);
			this.updateContest();
		}
	}

	public void createContest() {
		logger.info("Creating new contest.");
		this.contest.setContestManager((Manager) BeanUtil.getSessionValue("user"));
		this.contest = (Contest) service.makeContest(this.contest).readEntity(Contest.class);
		logger.info("Contest created with id #" + contest.getIdContest());
	}

	public void updateContest() {
		logger.info("Updating contest: #" + contest.getIdContest());
		this.contest = (Contest) service.updateContest(this.contest).readEntity(Contest.class);
		logger.info("Contest #" + contest.getIdContest() + " updated.");
	}
	
	public Problem getProblem(Integer id){
		return (Problem) service.getById(id);
	}

	public Map<Integer, Boolean> getCheckedProblems() {
		return checkedProblems;
	}

	public void setCheckedProblems(Map<Integer, Boolean> checkedProblems) {
		this.checkedProblems = checkedProblems;
	}

}
