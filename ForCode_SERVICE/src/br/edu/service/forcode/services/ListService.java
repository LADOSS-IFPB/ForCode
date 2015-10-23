package br.edu.service.forcode.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.contests.TestCaseSample;
import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.entities.User;
import br.edu.service.forcode.database.dao.ClarificationDAO;
import br.edu.service.forcode.database.dao.ContestDAO;
import br.edu.service.forcode.database.dao.InstitutionDAO;
import br.edu.service.forcode.database.dao.ProblemDAO;
import br.edu.service.forcode.database.dao.SubmissionDAO;
import br.edu.service.forcode.database.dao.TestCaseSampleDAO;
import br.edu.service.forcode.database.dao.UserDAO;
import br.edu.service.forcode.util.ErrorFactory;

@Path("list")
public class ListService {

	/**
	 * This class contains services related to listing things.
	 * */
	
	private static final Logger logger = LogManager
			.getLogger(ListService.class);

	@RolesAllowed(value = {"Manager", "Admin"})
	@GET
	@Path("/problems")
	@Produces("application/json")
	public List<Problem> listProblems() {
		ProblemDAO problemDao = new ProblemDAO();
		
		return problemDao.getAll();
	}

	@PermitAll
	@GET
	@Path("/contests")
	@Produces("application/json")
	public List<Contest> listContests() {
		ContestDAO contestDao = new ContestDAO();
		return contestDao.getAll();
	}

	@PermitAll
	@POST
	@Path("/clarificationbycontest")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Clarification> listClarifications(Contest contest) {
		ClarificationDAO clarificationDao = new ClarificationDAO();
		
		if(contest == null)return new ArrayList<Clarification>();
		
		return clarificationDao.getClarificationsByContest(contest);
	}

	@PermitAll
	@GET
	@Path("/submission")
	@Produces("application/json")
	public List<Submission> listSubmissions() {
		SubmissionDAO submissionDao = new SubmissionDAO();
		
		return submissionDao.getAll();
	}

	@PermitAll
	@POST
	@Path("/submissionsbycontest")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Submission> listSubmissions(Contest contest) {
		SubmissionDAO submissionDao = new SubmissionDAO();
		
		if(contest == null)return new ArrayList<Submission>();
		
		return submissionDao.getSubmissionsByContest(contest);
	}
	
	@PermitAll
	@GET
	@Path("/users")
	@Produces("application/json")
	public List<User> listUsers() {
		UserDAO userDao = new UserDAO();
		
		return userDao.getAll();
	}

	@PermitAll
	@GET
	@Path("/contestbyid")
	@Consumes("application/json")
	@Produces("application/json")
	public Response seachContestById(@QueryParam("q") Integer idContest) {
		ContestDAO contestDao = new ContestDAO();
		ResponseBuilder builder;

		logger.info("Searching for contest " + idContest);

		Contest contest = contestDao.getById(idContest);

		if (contest == null) {
			ForCodeError error = ErrorFactory
					.getErrorFromIndex(ErrorFactory.CONTEST_NOT_EXISTENT);
			builder = Response.status(Response.Status.NOT_FOUND).entity(error);
		} else {
			builder = Response.status(Response.Status.OK).entity(contest);
		}

		return builder.build();
	}

	@PermitAll
	@GET
	@Path("/contestbyname")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Contest> seachContestByName(@QueryParam("q") String name) {
		ContestDAO contestDao = new ContestDAO();

		logger.info("Searching for contest " + name);

		List<Contest> contests = contestDao.getByName(name);

		return contests;
	}

	@PermitAll
	@GET
	@Path("/institutions")
	@Produces("application/json")
	public List<Institution> listInstitutions() {
		InstitutionDAO institutionDAO = new InstitutionDAO();
		List<Institution> list = institutionDAO.getAll();

		return list;
	}
	
	@PermitAll
	@GET
	@Path("/problem/{idProblem}")
	@Produces("application/json")
	public Response listTestCaseSample(@QueryParam("idProblem") Integer idProblem){
		TestCaseSampleDAO testCaseSampleDao = new TestCaseSampleDAO();
		ResponseBuilder builder;
		
		List<TestCaseSample> list = testCaseSampleDao.getByProblem(idProblem);
		
		if(list.isEmpty())
			builder = Response.status(Response.Status.NOT_FOUND);
		else
			builder = Response.status(Response.Status.OK).entity(list);
		
		return builder.build();
	}
}