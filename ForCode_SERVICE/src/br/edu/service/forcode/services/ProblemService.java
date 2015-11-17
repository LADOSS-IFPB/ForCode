package br.edu.service.forcode.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Score;
import br.edu.commons.forcode.contests.TestCase;
import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.service.forcode.database.dao.ProblemDAO;
import br.edu.service.forcode.database.dao.ScoreDAO;
import br.edu.service.forcode.database.dao.TestCaseDAO;
import br.edu.service.forcode.util.ErrorFactory;

/**
 * This class contains the services related to the Contest Problems.
 * @author rerissondaniel
 */

@Path("problem")
public class ProblemService {
	
	private static final Logger logger = LogManager.getLogger(ProblemService.class);
	
	@RolesAllowed(value = {"Manager"})
	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response makeProblem(Problem problem) {
		
		logger.info("Inserting problem " + problem.getTitle());
		
		ProblemDAO problemDao = new ProblemDAO();
		problemDao.insert(problem);
		
		ResponseBuilder builder = Response.status(Response.Status.CREATED).entity(problem);
		
		logger.info("Problem " + problem.getTitle() + " inserted");
		
		return builder.build();
	}

	@RolesAllowed(value = {"Manager"})
	@POST
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateProblem(Problem problem) {
		
		logger.info("Updating problem " + problem.getTitle());
		ProblemDAO problemDao = new ProblemDAO();
		
		problemDao.update(problem);
		
		ResponseBuilder builder = Response.status(Response.Status.ACCEPTED);
		logger.info("Problem " + problem.getTitle() + " updated");
		
		return builder.build();
	}
	
	@RolesAllowed(value = {"Manager"})
	@POST
	@Path("/delete")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteProblem(Problem problem) {
		ScoreDAO scoreDao = new ScoreDAO();
		List<Score> listScore = scoreDao.getByProblem(problem);
		
		ProblemDAO problemDao = new ProblemDAO();
		ResponseBuilder builder;
		
		ForCodeError error = this.deleteTestCaseDataImpl(problem);

		logger.info("Deleting problem " + problem.getTitle());
		
		if(!listScore.isEmpty()){
			
			error = ErrorFactory.getErrorFromIndex(ErrorFactory.PROBLEM_NOT_DELETABLE);
			logger.info("Problem " + problem.getTitle() + " could not be deleted, " + error.getMessage());
			
			builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(error);
			
		}else{
			
			problemDao.delete(problem);
			logger.info("Problem " + problem.getTitle() + " deleted");
			builder = Response.status(Response.Status.ACCEPTED);
		}
		
		return builder.build();
	}
	
	@RolesAllowed(value = {"Manager"})
	@POST
	@Path("/deletetestcasedata")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteTestCaseData(Problem problem){
		
		ResponseBuilder builder;

		ForCodeError error = this.deleteTestCaseDataImpl(problem);
		
		if(error == null)
			builder = Response.status(Response.Status.ACCEPTED);
		else
			builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(error);
		
		return builder.build();
	}
	
	private ForCodeError deleteTestCaseDataImpl(Problem problem){
		
		TestCaseDAO testCaseDao = new TestCaseDAO();
		ForCodeError error = null;
		StringTokenizer tokenizer;
		List<TestCase> testCases;

		testCases = problem.getTestcases();

		if(!testCases.isEmpty()){
			try{
				tokenizer = new StringTokenizer(testCases.get(0).getPath(), "/");
				String str, aux = "";
				while(!(str = tokenizer.nextToken()).equals(String.format("%d", problem.getIdProblem()))){
					aux += "/"+str;
				}
				aux += "/"+str;
				FileUtils.deleteDirectory(new File(aux));
				
				for(TestCase testCase : problem.getTestcases()){
					testCaseDao.delete(testCase);
				}
				
				logger.info("TestCase data deleted");
			}catch (IOException ioex) {
				
				logger.warn("Error while trying to delete testCase data");
				error = ErrorFactory.getErrorFromIndex(ErrorFactory.DATA_NOT_DELETABLE);
			}
		}
		return error;
	}
	
	
	@PermitAll
	@GET
	@Path("/search/id/{idProblem}")
	@Produces("application/json")
	public Problem getById(@PathParam("idProblem")Integer idProblem){
		ProblemDAO problemDao = new ProblemDAO();
		
		Problem problem = problemDao.getById(idProblem);
		problem.getProblemSetter().setUserKey(null);
		
		return problem;
	}
	
	@PermitAll
	@GET
	@Path("/search/{title}")
	@Consumes("application/json")
	@Produces("application/json")
	public Problem getByName(@PathParam("title") String problemTitle){
		ProblemDAO problemDao = new ProblemDAO();
		return problemDao.getByTitle(problemTitle);
	}
}