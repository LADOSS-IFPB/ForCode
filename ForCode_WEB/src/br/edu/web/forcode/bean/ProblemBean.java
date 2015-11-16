package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.entities.Manager;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ViewScoped
@ManagedBean(name = "problemBean")
public class ProblemBean {

	private Problem problem = new Problem();

	private static final Logger logger = LogManager.getLogger(ProblemBean.class
			.getName());

	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	public void next() {
		System.out.println(problem.getCreationProgress());

		if (problem.getCreationProgress() != null) {
			problem.setCreationProgress(problem.getCreationProgress() + 20);

			updateProblem();
		} else {
			problem.setProblemSetter((Manager) BeanUtil.getSessionValue("user"));
			problem.setCreationProgress(20);

			makeProblem();
		}
	}

	public String makeProblem() {
		logger.info("Requesting creation of a new Problem");

		service.makeProblem(problem);

		logger.info("Problem creation successfull");

		return "/home.xhtml?faces-redirect=true";
	}

	public String updateProblem() {
		logger.info("Requesting update of a Problem");

		if (service.updateProblem(problem).getStatusInfo() == Response.Status.ACCEPTED) {
			logger.info("Problem update successfull");
		} else {
			logger.info("Problem update unsuccessfull");
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

	public void showProblem() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		this.problem = service.getById(id);
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
		this.setProblem(problem);
	}

	public String showProblem(Problem problem) {
		this.setProblem(problem);
		return "/webapp/contest/problem.xhtml";
	}
}
