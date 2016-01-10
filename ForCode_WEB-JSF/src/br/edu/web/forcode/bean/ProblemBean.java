package br.edu.web.forcode.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.ForCodeUploadFile;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.enumerations.FileType;
import br.edu.web.forcode.bean.util.BeanUtil;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ViewScoped
@ManagedBean(name = "problemBean")
public class ProblemBean implements Serializable {

	private static final long serialVersionUID = 697596035039862344L;

	private Problem problem = new Problem();

	private Part testCasesUpFile = null;

	private static final Logger logger = LogManager.getLogger(ProblemBean.class
			.getName());

	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);

	public void next() {

		if (problem.getCreationProgress() != null) {
			problem.setCreationProgress(problem.getCreationProgress() + 20);

			updateProblem();

		} else {
			problem.setProblemSetter((Manager) BeanUtil.getSessionValue("user"));
			problem.setCreationProgress(20);

			this.makeProblem();

		}

		if (this.testCasesUpFile != null) {
			this.makeTestCase();
		}
	}

	public String makeProblem() {
		logger.info("Requesting creation of a new Problem");

		Response response = service.makeProblem(problem);

		this.problem = response.readEntity(Problem.class);
		response.close();

		logger.info("Problem creation successfull");

		return "/home.xhtml?faces-redirect=true";
	}

	public String updateProblem() {
		logger.info("Requesting update of a Problem");

		Response response = service.updateProblem(problem);

		if (response.getStatusInfo() == Response.Status.ACCEPTED) {
			logger.info("Problem update successfull");
		} else {
			logger.info("Problem update unsuccessfull");
		}

		response.close();

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

	public void makeTestCase() {

		ForCodeUploadFile forCodeUpFile = new ForCodeUploadFile();
		forCodeUpFile.setFileType(FileType.PROBLEM_TEST_CASE_ZIP);

		try {
			forCodeUpFile.setFile(IOUtils.toByteArray(testCasesUpFile
					.getInputStream()));

		} catch (IOException ioexcep) {

			logger.warn("Could not convert inputStream from uploaded file to byte array, there's something wrong.");
			// TODO add a message to the view.
		}

		service.uploadTestCaseFile(problem.getIdProblem(), forCodeUpFile);

	}

	public String updateTestCase() {
		// TODO
		return null;
	}

	public String deleteTestCase() {
		// TODO
		return null;
	}

	public String showProblem(Problem problem) {
		this.setProblem(problem);
		return "/webapp/contest/problem.xhtml";
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.setProblem(problem);
	}

	public Part getTestCasesFile() {
		return testCasesUpFile;
	}

	public void setTestCasesFile(Part testCasesFile) {
		this.testCasesUpFile = testCasesFile;
	}

}
