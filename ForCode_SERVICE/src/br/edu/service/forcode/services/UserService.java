package br.edu.service.forcode.services;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.UserContest;
import br.edu.commons.forcode.entities.Admin;
import br.edu.commons.forcode.entities.Contestant;
import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.entities.Institution;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.entities.User;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.commons.forcode.util.EncodingUtil;
import br.edu.service.forcode.database.dao.InstitutionDAO;
import br.edu.service.forcode.database.dao.UserContestDAO;
import br.edu.service.forcode.database.dao.UserDAO;
import br.edu.service.forcode.rest.security.Authorizator;
import br.edu.service.forcode.util.ErrorFactory;
import br.edu.service.forcode.util.ForCodeValidator;

/**
 * This class contains services related to user actions.
 * @author rerissondaniel
 * @author joserenan
 * */

@Path("user")
public class UserService {

	private static final Logger logger = LogManager
			.getLogger(UserService.class.getName());
	
	@RolesAllowed(value = {"Admin"})
	@POST
	@Path("/createadmin")
	@Consumes("application/json")
	@Produces("application/json")
	public Response insertAdmin(Admin admin) {
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validateInsertion(admin);
		try{
			if (error == null) {
				userDAO.insert(admin);
				builder = Response.status(Response.Status.OK).entity(admin);
				logger.info("New Admin registered.");
			} else {
				logger.info(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to register new admin.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}
	
	@RolesAllowed(value = {"Admin"})
	@POST
	@Path("/updateadmin")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateAdmin(Admin admin) {
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validateUpdate(admin);
		try{
			if (error == null) {
				userDAO.update(admin);
				builder = Response.status(Response.Status.OK).entity(admin);
				logger.info("Admin updated.");
			} else {
				System.out.println(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to update Admin.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}
	
	@RolesAllowed(value = {"Admin"})
	@POST
	@Path("/createmanager")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createManager(Manager manager) {
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validateInsertion(manager);
		try{
			if (error == null) {
				userDAO.insert(manager);
				builder = Response.status(Response.Status.OK).entity(manager);
				logger.info("New Manager registered.");
			} else {
				logger.info(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to register new Manager.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}
	
	@RolesAllowed(value = {"Admin"})
	@POST
	@Path("/createinstitute")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createInstitution(Institution institution) {
		InstitutionDAO userDAO = new InstitutionDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validate(institution);
		
		try{
			if (error == null) {
				userDAO.insert(institution);
				builder = Response.status(Response.Status.OK).entity(institution);
				logger.info("New institution registered.");
			} else {
				System.out.println(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to register new institute.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}
	
	@PermitAll
	@POST
	@Path("/searchinstitution")
	@Consumes("application/json")
	@Produces("application/json")
	public Response searchInstitution(String institutionName) {
		
		logger.info("Searching for institution " + institutionName);
		
		Institution institution = this.findInstitution(institutionName);
		
		ResponseBuilder builder;
		
		if(institution == null){
			ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.INSTITUTION_NOT_FOUND);
			builder = Response.status(Response.Status.NOT_FOUND).entity(error);
		
			logger.info("Unsuccessful attempt to find institution.");
		}else{
			builder = Response.status(Response.Status.FOUND).entity(institution);
			
			logger.info("Successful attempt to find institution.");
		}
		
		return builder.build();
	}
	
	private Institution findInstitution(String institutionName) {	
		InstitutionDAO institutionDAO = new InstitutionDAO();
		try{
			Institution institution = institutionDAO.getByName(institutionName);
			return institution;
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}
	
	@RolesAllowed(value = {"Manager"})
	@POST
	@Path("/updatemanager")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateManager(Manager manager) {
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validateUpdate(manager);

		try{
			if (error == null) {
				userDAO.update(manager);
				builder = Response.status(Response.Status.OK).entity(manager);
				logger.info("Manager updated.");
			} else {
				System.out.println(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to update Manager.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		return builder.build();
	}

	@PermitAll
	@POST
	@Path("/createcontestant")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createContestant(Contestant contestant) {
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validateInsertion(contestant);
		try{
			if (error == null) {
				userDAO.insert(contestant);
				builder = Response.status(Response.Status.OK).entity(contestant);
				logger.info("New Contestant registered.");
			} else {
				System.out.println(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to register new contestant.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}
	
	@RolesAllowed(value = {"Contestant"})
	@POST
	@Path("/updatecontestant")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updateContestant(Contestant contestant) {
		UserDAO userDAO = new UserDAO();
		ResponseBuilder builder;

		ForCodeError error = ForCodeValidator.validateUpdate(contestant);
		try{
			if (error == null ) {
				userDAO.update(contestant);
				builder = Response.status(Response.Status.OK).entity(contestant);
				logger.info("Contestant updated.");
			} else {
				System.out.println(error.getMessage());
				builder = Response.status(Response.Status.CONFLICT).entity(error);
				logger.info("Conflict while trying to update contestant.");
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}

		return builder.build();
	}
	
	@PermitAll
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(@QueryParam("user") String username, String password){
		
		username = EncodingUtil.decode(username);
		
		logger.info(username + " is signing in");
		
		User user = this.findUser(username);
		ResponseBuilder builder;
		
		if(user == null){
			logger.info("Incorrect username: " + username);
			
			ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
			builder = Response.status(Response.Status.NOT_FOUND).entity(error);
		
		} else {
			if(!user.getPassword().equals(password)){
				logger.info("Incorrect password for this username: " + username);
				
				ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.INCORRECT_PASSWORD);
				builder = Response.status(Response.Status.NOT_FOUND).entity(error);
			
			} else {			
				Authorizator auth = new Authorizator();
				user.setUserKey(auth.generateKey(user));
				
				logger.info(username + " is logged");
				
				builder = Response.status(Response.Status.OK).entity(user);
				return builder.build();
			}
		}
			
		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/searchuser")
	@Consumes("application/json")
	@Produces("application/json")
	public Response searchUser(@QueryParam("q")String username) {
		
		logger.info("Searching for user " + username);
		
		User user = this.findUser(username);
		
		ResponseBuilder builder;
		
		if(user == null){
			ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.USER_NOT_FOUND);
			builder = Response.status(Response.Status.NOT_FOUND).entity(error);
		
			logger.info("Unsuccessful attempt to find user.");
		}else{
			
			user.setPassword(null);
			user.setUserKey(null);

			builder = Response.status(Response.Status.FOUND).entity(user);
			
			logger.info("Successful attempt to find user.");
		}
		
		return builder.build();
	}
	
	private User findUser(String username) {	
		UserDAO userDao = new UserDAO();
		try{
			User user  = userDao.getByUsername(username);
			return user;
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			return null;
		}
	}
	
	@RolesAllowed(value = {"Admin","Manager","Contestant"})
	@POST
	@Path("/logout")
	@Consumes("application/json")
	@Produces("application/json")
	public Response logout(User user){
		ResponseBuilder builder;
		if(user == null){
			logger.info("Null entry on logout");
			builder = Response.status(Response.Status.NO_CONTENT);
			return builder.build();
		}
		
		logger.info("Removing " + user.getUsername() + "'s key");
		
		Authorizator auth = new Authorizator();
			
		auth.deleteKey(user);
		
		builder = Response.status(Response.Status.OK);
		return builder.build();
	}
	
	@RolesAllowed(value={"Manager"})
	@POST
	@Path("/invalidatecontestant")
	@Consumes("application/json")
	@Produces("application/json")
	public Response invalidateContestant(UserContest contestant) {
		UserContestDAO userContestDao = new UserContestDAO();
		ResponseBuilder builder;
		
		try{
			contestant = userContestDao.getById(contestant.getIdUserContest());
			logger.info("Invalidating contestant");
			
			if(contestant == null){
				logger.info("Contestant not found");
				ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.USER_CONTEST_NOT_FOUND);
				builder = Response.status(Response.Status.NOT_FOUND).entity(error);
				
			}else{
				contestant.setValid(false);
				userContestDao.update(contestant);
				
				logger.info("Contestant invalidated " + contestant.getUser().getUsername());
				builder = Response.status(Response.Status.ACCEPTED).entity(contestant);
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		return builder.build();
	}
	
	@PermitAll
	@GET
	@Path("/user/getusercontest/{idContest}/{idUser}")
	@Consumes("application/json")
	@Produces("application/json")
	public UserContest getUserContest(@PathParam("idContest") Integer idContest, @PathParam("idUser") Integer idUser){
		UserContestDAO userContestDao = new UserContestDAO();
		try{
			return userContestDao.getUserContestByContest(idContest, idUser);
		}catch(ForCodeDataException fde){
			return null;
		}
	}
}
