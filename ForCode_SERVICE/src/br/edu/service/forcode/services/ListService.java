package br.edu.service.forcode.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.contests.TestCaseSample;
import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.database.dao.ClarificationDAO;
import br.edu.service.forcode.database.dao.ContestDAO;
import br.edu.service.forcode.database.dao.InstitutionDAO;
import br.edu.service.forcode.database.dao.LanguageDAO;
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

	@PermitAll
	@POST
	@Path("/problemsbymanager")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Problem> listProblemsByProblemSetter(Manager problemSetter) {
		ProblemDAO problemDao = new ProblemDAO();
		try{
			return problemDao.getAllByProblemSetter(problemSetter);
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}

	@PermitAll
	@GET
	@Path("/problems")
	@Produces("application/json")
	public List<Problem> listProblems() {
		ProblemDAO problemDao = new ProblemDAO();
		try{
			return problemDao.getAll();
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}

	@PermitAll
	@GET
	@Path("/contests")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Contest> listContests() {
		ContestDAO contestDao = new ContestDAO();
		try{
			return contestDao.getAll();
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}

	@PermitAll
	@POST
	@Path("/contestsbymanager")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Contest> listContestsByProblemSetter(Manager manager) {
		ContestDAO contestDao = new ContestDAO();
		try{
			return contestDao.getAllByProblemSetter(manager);
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}

	@PermitAll
	@POST
	@Path("/clarificationbycontest")
	@Consumes("application/json")
	@Produces("application/json")
	/**@see clarificationDao.getClarificationsByContest(contest);
	 * */
	public List<Clarification> listClarifications(Contest contest) {
		ClarificationDAO clarificationDao = new ClarificationDAO();

		if (contest == null)
			return new ArrayList<Clarification>();

		return clarificationDao.getClarificationsByContest(contest);
	}

	@PermitAll
	@GET
	@Path("/submission")
	@Produces("application/json")
	public List<Submission> listSubmissions() {
		SubmissionDAO submissionDao = new SubmissionDAO();
		try{
			return submissionDao.getAll();
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}

	@PermitAll
	@POST
	@Path("/submissionsbycontest")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Submission> listSubmissions(Contest contest) {
		SubmissionDAO submissionDao = new SubmissionDAO();

		if (contest == null)
			return new ArrayList<Submission>();
		try{
			return submissionDao.getSubmissionsByContest(contest);
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return new ArrayList<Submission>();
		}
	}

	@PermitAll
	@GET
	@Path("/users")
	@Produces("application/json")
	public List<User> listUsers() {
		UserDAO userDao = new UserDAO();
		try{
			return userDao.getAll();
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}
	
	@PermitAll
	@POST
	@Path("/usersbytype")
	@Consumes("application/json")
	@Produces("application/json")
	public List<User> listAllByType(String type) {
		UserDAO userDAO = new UserDAO();
		try{
			return userDAO.getAllByTypeUser(type);
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
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
		try{
			Contest contest = contestDao.getById(idContest);
	
			if (contest == null) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.CONTEST_NOT_EXISTENT);
				builder = Response.status(Response.Status.NOT_FOUND).entity(error);
			} else {
				builder = Response.status(Response.Status.OK).entity(contest);
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
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
		try{
			List<Contest> contests = contestDao.getByName(name);

			return contests;
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}

	@PermitAll
	@GET
	@Path("/institutions")
	@Produces("application/json")
	public List<Institution> listInstitutions() {
		InstitutionDAO institutionDAO = new InstitutionDAO();
		try{
			List<Institution> list = institutionDAO.getAll();
	
			return list;
		}catch(ForCodeDataException fed){
			logger.warn(fed);
			return null;
		}
	}

	@PermitAll
	@GET
	@Path("/problem/{idProblem}")
	@Produces("application/json")
	public Response listTestCaseSample(@PathParam("idProblem") Integer idProblem) {
		TestCaseSampleDAO testCaseSampleDao = new TestCaseSampleDAO();
		ResponseBuilder builder;
		try{
			List<TestCaseSample> list = testCaseSampleDao.getByProblem(idProblem);
	
			if (list.isEmpty())
				builder = Response.status(Response.Status.NOT_FOUND);
			else
				builder = Response.status(Response.Status.OK).entity(list);
		}catch(ForCodeDataException fde){
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		return builder.build();
	}

	@PermitAll
	@GET
	@Path("/clarification/{idProblem}")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Clarification> listClarificationByProblem(
			@PathParam("idProblem") Integer idProblem) {
		ClarificationDAO clarificationDao = new ClarificationDAO();
		try{			
			return clarificationDao.getClarificationsByProblem(idProblem);
		}catch(ForCodeDataException fed){
			logger.warn(fed);
			return null;
		}
	}

	@PermitAll
	@GET
	@Path("/search/{languageName}")
	@Consumes("application/json")
	@Produces("application/json")
	public Language searchLanguage(
			@PathParam("languageName") String languageName) {
		LanguageDAO languageDao = new LanguageDAO();
		try{
			return languageDao.getByName(languageName);
		}catch(ForCodeDataException fed){
			logger.warn(fed);
			return null;
		}
	}

	@PermitAll
	@GET
	@Path("/search/listlanguages")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Language> listLanguages() {
		LanguageDAO languageDao = new LanguageDAO();
		try{
			return languageDao.getAll();
		}catch(ForCodeDataException fed){
			logger.warn(fed);
			return null;
		}
	}
}