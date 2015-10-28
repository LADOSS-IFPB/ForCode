package br.edu.web.forcode.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Submission;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "problemBean")
@RequestScoped
public class ProblemBean {
	Problem problem = new Problem();
	Language language;
	Part uploadedFile;

	private static final Logger logger = LogManager.getLogger(AdminBean.class
			.getName());

	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

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
		this.setProblem(problem);
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void showProblem() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id")); 
		this.problem = service.getById(id);
	}

	public String submit(String idContest, String idProblem, String idUser) {
		Submission submission = new Submission();
		submission.setProblem(service.getById(Integer.parseInt(idProblem)));
		submission.setUser(service.getUserContest(Integer.parseInt(idContest),
				Integer.parseInt(idUser)));
		// submission.setLanguage();
		return null;
	}

}
