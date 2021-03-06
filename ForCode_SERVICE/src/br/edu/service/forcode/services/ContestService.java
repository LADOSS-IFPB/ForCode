package br.edu.service.forcode.services;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
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
import br.edu.commons.forcode.contests.Score;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.contests.UserContest;
import br.edu.commons.forcode.entities.Contestant;
import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.enumerations.Verdict;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.database.dao.ClarificationDAO;
import br.edu.service.forcode.database.dao.ContestDAO;
import br.edu.service.forcode.database.dao.SubmissionDAO;
import br.edu.service.forcode.database.dao.UserContestDAO;
import br.edu.service.forcode.util.ErrorFactory;

/**
 * This classes contains the services related to Contests.
 * 
 * @author rerissondaniel
 */
@Path("contest")
public class ContestService {

	private static final Logger logger = LogManager
			.getLogger(ContestService.class.getName());

	@POST
	@Path("/createcontest")
	@Consumes("application/json")
	@Produces("application/json")
	public Response makeContest(Contest contest) {
		
		ResponseBuilder builder;

		ContestDAO contestDao = new ContestDAO();
		try{
			contestDao.insert(contest);
			builder = Response.status(Response.Status.CREATED).entity(contest);
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}


		return builder.build();
	}

	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateContest(Contest contest) {
		ResponseBuilder builder;

		ContestDAO contestDao = new ContestDAO();
		
		try{
			contestDao.update(contest);
			builder = Response.status(Response.Status.ACCEPTED).entity(contest);
		
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		return builder.build();
	}

	@Path("/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteContest(Contest contest) {
		//TODO ?
		return Response.status(Response.Status.NOT_IMPLEMENTED).build();
	}

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/entercontest")
	@Consumes("application/json")
	@Produces("application/json")
	/**
	 * This method assumes that the user exists.
	 * @author rerissondaniel
	 */
	public Response enterContest(Contestant user,
			@QueryParam("id") Integer idContest) {
		// TODO: test this.
		ContestDAO contestDao = new ContestDAO();
		UserContestDAO userContestDao = new UserContestDAO();
		ResponseBuilder builder;
		
		
		try{
			Contest contest = contestDao.getById(idContest);
			List<UserContest> list = userContestDao.getByUser(user);
			UserContest userContest = new UserContest();

			if (contest == null) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.CONTEST_NOT_EXISTENT);
	
				builder = Response.status(Response.Status.NOT_FOUND).entity(error);
	
				logger.info("Contestant " + user.getUsername()
						+ " tried to enter in a contest that doesn't exist");
				
			} else if (!list.isEmpty()) {
				ForCodeError error = ErrorFactory
						.getErrorFromIndex(ErrorFactory.USER_CONTEST_ALREADY_REGISTERED);
	
				builder = Response.status(Response.Status.CONFLICT).entity(error);
	
				logger.info("Contestant " + user.getUsername()
						+ " already entered in this contest ");
				
			}else{
				userContest.setUser(user);
				userContest.setValid(true);
	
				for (Problem problem : contest.getProblems()) {
					userContest.getScore().add(new Score(problem, 0));
				}
	
				userContestDao.insert(userContest);
	
				builder = Response.status(Response.Status.CREATED).entity(
						userContest);
				
				logger.info("Contestant " + user.getUsername()
						+ " entered in Contest " + contest.getName());
			}
			
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/leavecontest")
	@Consumes("application/json")
	@Produces("application/json")
	public Response leaveContest(UserContest userContest) {
		UserContestDAO userContestDao = new UserContestDAO();
		ResponseBuilder builder;
		
		try{
			userContestDao.delete(userContest);
			builder = Response.status(Response.Status.ACCEPTED).entity(userContest);
			
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		
		return builder.build();
	}

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/createsubmission")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createSubmission(Submission submission) {
		SubmissionDAO submissionDao = new SubmissionDAO();
		ResponseBuilder builder;
		try{
			submission.setVerdict(Verdict.IN_QUEUE.getTypeValue());
			submissionDao.insert(submission);
			
			builder = Response.status(Response.Status.CREATED).entity(submission);
			
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		
		return builder.build();
	}
	
	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/clarification/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response makeClarification(Clarification clarification) {
		ClarificationDAO clarificationDao = new ClarificationDAO();
		ResponseBuilder builder;
		
		try{
			clarificationDao.insert(clarification);
			builder = Response.status(Response.Status.CREATED).entity(clarification);
			
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		
		return builder.build();
	}

	@RolesAllowed(value = { "Manager" })
	@POST
	@Path("/clarification/reply")
	@Consumes("application/json")
	@Produces("application/json")
	public Response replyClarification(Clarification clarification) {
		ClarificationDAO clarificationDao = new ClarificationDAO();
		ResponseBuilder builder;
		
		try{
			clarificationDao.update(clarification);
			builder = Response.status(Response.Status.ACCEPTED).entity(clarification);
			
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		return builder.build();
	}

	@PermitAll
	@POST
	@Path("/ranking")
	@Consumes("application/json")
	@Produces("application/json")
	public List<UserContest> getRanking(Contest contest) {
		// TODO
		return null;
	}
}
