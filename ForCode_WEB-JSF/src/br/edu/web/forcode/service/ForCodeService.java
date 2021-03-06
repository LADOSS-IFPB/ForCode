package br.edu.web.forcode.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.edu.commons.forcode.contests.Clarification;
import br.edu.commons.forcode.contests.Contest;
import br.edu.commons.forcode.contests.ForCodeUploadFile;
import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.contests.UserContest;
import br.edu.commons.forcode.entities.Admin;
import br.edu.commons.forcode.entities.Contestant;
import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.entities.User;

public interface ForCodeService {

	/**
	 * User services
	 * */

	@POST
	@RolesAllowed(value = { "Admin" })
	@Path("/user/createadmin")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertAdmin(Admin admin);

	@POST
	@RolesAllowed(value = { "Admin" })
	@Path("/user/updateadmin")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateAdmin(Admin admin);

	@POST
	@RolesAllowed(value = { "Admin" })
	@Path("/user/createmanager")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createManager(Manager manager);

	@POST
	@RolesAllowed(value = { "Manager" })
	@Path("/user/updatemanager")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateManager(Manager manager);

	@PermitAll
	@POST
	@Path("/user/createcontestant")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createContestant(Contestant contestant);

	@POST
	@RolesAllowed(value = { "Contestant" })
	@Path("/user/updatecontestant")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateContestant(Contestant contestant);

	@PermitAll
	@POST
	@Path("/user/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(@QueryParam("user") String username, String password);

	@PermitAll
	@POST
	@Path("/user/searchuser")
	@Consumes("application/json")
	@Produces("application/json")
	public Response searchUser(String username);
	
	@POST
	@RolesAllowed(value = { "Admin", "Manager", "Contestant" })
	@Path("/user/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(User user);

	@POST
	@RolesAllowed(value = { "Manager" })
	@Path("/user/invalidatecontestant")
	@Consumes("application/json")
	@Produces("application/json")
	public Response invalidateContestant(UserContest contestant);

	@RolesAllowed(value = { "Admin" })
	@POST
	@Path("/user/createinstitute")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createInstitution(Institution institution);

	@PermitAll
	@POST
	@Path("/user/searchinstitution")
	@Consumes("application/json")
	@Produces("application/json")
	public Response searchInstitution(String value);
	
	@PermitAll
	@GET
	@Path("/user/getusercontest/{idContest}/{idUser}")
	@Consumes("application/json")
	@Produces("application/json")
	public UserContest getUserContest(@PathParam("idContest") Integer idContest, @PathParam("idUser") Integer idUser);

	/**
	 * Problem Services
	 */

	@RolesAllowed(value = { "Manager" })
	@POST
	@Path("/problem/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response makeProblem(Problem problem);

	@RolesAllowed(value = { "Manager" })
	@POST
	@Path("/problem/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateProblem(Problem problem);

	@RolesAllowed(value = { "Manager" })
	@POST
	@Path("/problem/delete")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteProblem(Problem problem);

	@RolesAllowed(value = { "Manager" })
	@POST
	@Path("/problem/deletetestcasedata")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteTestCaseData(Problem problem);

	/**
	 * List Services
	 */
	
	@PermitAll
	@POST
	@Path("list/problemsbymanager")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Problem> listProblemsByProblemSetter(Manager problemSetter);
	
	@PermitAll
	@GET
	@Path("/list/problems")
	@Produces("application/json")
	public List<Problem> listProblems();

	@PermitAll
	@GET
	@Path("/list/contests")
	@Produces("application/json")
	public List<Contest> listContests();
	
	@PermitAll
	@POST
	@Path("/list/contestsbymanager")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Contest> listContestsByProblemSetter(Manager manager);

	@PermitAll
	@POST
	@Path("/list/clarificationbycontest")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Clarification> listClarifications(Contest contest);

	@PermitAll
	@GET
	@Path("/list/submission")
	@Produces("application/json")
	public List<Submission> listSubmissions();

	@PermitAll
	@POST
	@Path("/list/submissionsbycontest")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Submission> listSubmissions(Contest contest);

	@PermitAll
	@GET
	@Path("/list/users")
	@Produces("application/json")
	public List<User> listUsers();
	
	@PermitAll
	@POST
	@Path("/list/usersbytype")
	@Consumes("application/json")
	@Produces("application/json")
	public List<User> listAllByType(String type);

	@PermitAll
	@GET
	@Path("/list/contestbyid")
	@Consumes("application/json")
	@Produces("application/json")
	public Response seachContestById(@QueryParam("q") Integer idContest);

	@PermitAll
	@GET
	@Path("/list/contestbyname")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Contest> seachContestByName(@QueryParam("q") String name);

	@PermitAll
	@GET
	@Path("/list/institutions")
	@Produces("application/json")
	public List<Institution> listInstitutions();
	
	@PermitAll
	@GET
	@Path("/clarification/{idProblem}")
	@Consumes("application/json")
	@Produces("application/json")
	public List<Clarification> listClarificationByProblem(@PathParam("idProblem") Integer idProblem);

	/**
	 * 
	 * Contest Services
	 */
	
	@POST
	@Path("/contest/createcontest")
	@Consumes("application/json")
	@Produces("application/json")
	public Response makeContest(Contest contest);

	@POST
	@Path("/contest/update")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateContest(Contest contest);

	@Path("/contest/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteContest(Contest contest);

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/contest/entercontest")
	@Consumes("application/json")
	@Produces("application/json")
	public Response enterContest(Contestant user, @QueryParam("id") Integer idContest);

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/contest/leavecontest")
	@Consumes("application/json")
	@Produces("application/json")
	public Response leaveContest(UserContest userContest);

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/contest/createsubmission")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createSubmission(Submission submission);

	@RolesAllowed(value = { "Contestant" })
	@POST
	@Path("/contest/clarification/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response makeClarification(Clarification clarification);

	@RolesAllowed(value = { "Manager" })
	@POST
	@Path("/contest/clarification/reply")
	@Consumes("application/json")
	@Produces("application/json")
	public Response replyClarification(Clarification clarification);

	@PermitAll
	@POST
	@Path("/contest/ranking")
	@Consumes("application/json")
	@Produces("application/json")
	public List<UserContest> getRanking(Contest contest);
	
	@PermitAll
	@POST
	@Path("/problem/searchproblem")
	@Consumes("application/json")
	@Produces("application/json")
	public Problem searchProblem(String problemTitle);
	
	@PermitAll
	@GET
	@Path("/problem/search/id/{idProblem}")
	@Produces("application/json")
	public Problem getById(@PathParam("idProblem")Integer idProblem);
	
	@PermitAll
	@GET
	@Path("/problem/{title}")
	@Produces("application/json")
	public Problem getByName(@PathParam("title") String problemTitle);
	
	@PermitAll
	@GET
	@Path("/list/search/{languageName}")
	@Produces("application/json")
	public Language searchLanguage(@PathParam("languageName") String languageName);
	
	@PermitAll
	@GET
	@Path("/list/search/listlanguages")
	@Produces("application/json")
	public List<Language> listLanguages();
	
	/**
	 * UploadFile services.
	 * 
	 */
	
	@POST
	@Path("/upload/submission/{idSubmission}")
	@Consumes("multipart/form-data; charset=UTF-8")
	@Produces("application/json")
	public Response judgeSubmissionFile(@PathParam("idSubmission") Integer idSubmission,
			@MultipartForm ForCodeUploadFile form);
	
	@POST
	@Path("/upload/testcase/{idProblem}")
	@Consumes("multipart/form-data; charset=UTF-8")
	@Produces("application/json")
	public Response uploadTestCaseFile(@PathParam("idProblem") Integer idProblem,
			@MultipartForm ForCodeUploadFile form);
	
	
}
