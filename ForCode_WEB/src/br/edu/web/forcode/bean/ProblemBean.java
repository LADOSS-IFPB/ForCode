package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Problem;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "problemBean")
@RequestScoped
public class ProblemBean {
	Problem problem = new Problem();
	
	private static final Logger logger = LogManager.getLogger(AdminBean.class
			.getName());
	
	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	public String makeProblem() {
		problem = (Problem) BeanUtil.getSessionValue("problemBean");

		logger.info("Requesting creation of a new Problem");

		if (service.makeProblem(problem).getStatusInfo() == Response.Status.ACCEPTED) {
			logger.info("Problem creation successfull");
		}

		return "/home.xhtml?faces-redirect=true";
	}

	public String updateProblem() {
		problem = (Problem) BeanUtil.getSessionValue("problemBean");

		logger.info("Requesting update of a Problem");

		if (service.updateProblem(problem).getStatusInfo() == Response.Status.ACCEPTED) {
			logger.info("Problem update successfull");
		}

		return "/home.xhtml?faces-redirect=true";
	}

	public String deleteProblem() {
		logger.info("Requesting deletion of a Problem");

		if (service.deleteProblem(problem).getStatusInfo() == Response.Status.ACCEPTED) {
			logger.info("Problem successfully deleted");
		}

		return "/home.xhtml?faces-redirect=true";
	}

	public String makeTestCase() {
		// TODO
		return null;
	}

	public String updateTestCase() {
		// TODO
		return null;
	}

	public String deleteTestCase() {
		// TODO
		return null;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public String showProblem(Problem problem){
		this.setProblem(problem);
		
		return "/webapp/contest/problem.xhtml";
	}
	
}
