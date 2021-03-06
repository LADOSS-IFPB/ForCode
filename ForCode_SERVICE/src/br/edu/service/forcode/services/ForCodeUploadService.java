package br.edu.service.forcode.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.edu.commons.forcode.contests.ForCodeUploadFile;
import br.edu.commons.forcode.contests.Problem;
import br.edu.commons.forcode.contests.Score;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.contests.TestCase;
import br.edu.commons.forcode.contests.UserContest;
import br.edu.commons.forcode.entities.ForCodeError;
import br.edu.commons.forcode.enumerations.FileType;
import br.edu.commons.forcode.enumerations.Verdict;
import br.edu.commons.forcode.exceptions.ForCodeDataException;
import br.edu.service.forcode.database.dao.ProblemDAO;
import br.edu.service.forcode.database.dao.SubmissionDAO;
import br.edu.service.forcode.judgeServices.JudgeService;
import br.edu.service.forcode.judgeServices.ProviderServiceFactory;
import br.edu.service.forcode.util.ErrorFactory;

@Path("upload")
public class ForCodeUploadService {
	/**
	 * Is responsible for operations involving files manipulations, especially
	 * those related to submissions and problems.
	 * 
	 * @author: rerissondaniel
	 */

	private static final Logger logger = LogManager.getLogger(ForCodeUploadService.class);

	private static final String FORCODE_FOLDER = System.getProperty("user.home") + "/ForCode";

	private static final String SUBMISSION_PATH = System.getProperty("user.home")
			+ "/ForCode/Contests/";
	private static final String TESTCASE_PATH = System.getProperty("user.home")
			+ "/ForCode/Problems/";
	private static final String TEMP_FOLDER = System.getProperty("user.home") + "/ForCode/.tmp";

	private static final int MAX_SUBMISSION_FILE_SIZE_IN_BYTES = 2097152;
	private static final int MAX_TESTCASE_FILE_SIZE_IN_BYTES = 134217728;

	public ForCodeUploadService() {
		File file;
		file = new File(FORCODE_FOLDER);
		if (!file.exists())
			file.mkdirs();

		file = new File(SUBMISSION_PATH);
		if (!file.exists())
			file.mkdirs();

		file = new File(TESTCASE_PATH);
		if (!file.exists())
			file.mkdirs();

		file = new File(TEMP_FOLDER);
		if (!file.exists())
			file.mkdirs();
	}

	/**
	 * Creates a directory for the submission and requests a verdict for it from
	 * the judge service. Temporarily its necessary the JudgeService to be
	 * hosted in the same server.
	 * 
	 * @param idSubmission
	 *            The id of the submission to be judged.
	 * @param form
	 *            A form containing the byte array relative to the
	 *            submissionFile.
	 * @return A response signaling the success or not of the operation.
	 */

	@POST
	@Path("/submission/{idSubmission}")
	@Produces("application/json")
	@Consumes("multipart/form-data; charset=UTF-8")
	public Response judgeSubmissionFile(@PathParam("idSubmission") Integer idSubmission,
			@MultipartForm ForCodeUploadFile form) {

		ResponseBuilder builder;

		if (form.getFileType().getId() != FileType.SUBMISSION_FILE.getId()) {

			logger.info("File for submission not accepted.");
			builder = Response.status(Response.Status.NOT_ACCEPTABLE);

		} else if (form.getFile().length >= MAX_SUBMISSION_FILE_SIZE_IN_BYTES) {

			logger.info("Submission file is too large.");
			ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.FILE_TOO_LARGE);
			builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(error);

		} else {
			try {
				SubmissionDAO submissionDao = new SubmissionDAO();
				Submission submission = submissionDao.getById(idSubmission);
	
				JudgeService judgeService = ProviderServiceFactory
						.createServiceClient(JudgeService.class);
	
				File submissionFile = new File(SUBMISSION_PATH
						+ submission.getUser().getIdUserContest() + submission.getIdSubmission()
						+ form.getFileName() + "." + form.getFileExtension());

				FileUtils.writeByteArrayToFile(submissionFile, form.getFile());

				submission.setFileSubmission(submissionFile);
				submission.setVerdict(judgeService.judgeSubmission(submission).getTypeValue());

				if(submission.getVerdict() == Verdict.ACCEPTED.getTypeValue()){
					UserContest userContest = submission.getUser();
					
					List<Score> newScore = new ArrayList<Score>();
					
					for(Score score : userContest.getScore()){
						
						if(score.getProblem().getIdProblem().equals(submission.getProblem().getIdProblem())){
							score.setScore(1);
						}
						
						newScore.add(score);
					}
					
					userContest.setScore(newScore);
				}else{
					UserContest userContest = submission.getUser();
					List<Score> newScore = new ArrayList<Score>();
					
					for(Score score : userContest.getScore()){
						if(score.getProblem().getIdProblem().equals(submission.getProblem().getIdProblem())){
							if(score.getScore() <= 0)
								score.setScore(score.getScore() - 1);
						}
						newScore.add(score);
					}
					
					userContest.setScore(newScore);
				}
				
				submissionDao.update(submission);

				builder = Response.status(Response.Status.ACCEPTED).entity(submission);

			} catch (IOException ioException) {

				builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
			} catch (ForCodeDataException fde) {
				logger.warn(fde.getMessage());
				builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
			}
		}

		return builder.build();
	}

	/**
	 * Creates the file that a problem needs in order to be valid: the
	 * testcases. There are issues here to be avaliated yet.
	 * 
	 * @param idProblem
	 *            The id of the problem to which the TestCases belongs.
	 * @param form
	 *            A form containing a zipped file with the data needed .
	 * @return A response signaling the success or not of the operation.
	 */
	
	@POST
	@Path("/testcase/{idProblem}")
	@Produces("application/json")
	@Consumes("multipart/form-data; charset=UTF-8")
	public Response uploadTestCaseFile(@PathParam("idProblem") Integer idProblem,
			@MultipartForm ForCodeUploadFile form) {
		
		ResponseBuilder builder;
		
		try{
			ProblemDAO problemDao = new ProblemDAO();
			Problem problem = problemDao.getById(idProblem);
			
			File tempZip = new File(TEMP_FOLDER + "/temptestcase.zip");
	
	
			if (form.getFile().length >= MAX_TESTCASE_FILE_SIZE_IN_BYTES) {
	
				ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.FILE_TOO_LARGE);
				builder = Response.status(Response.Status.NOT_ACCEPTABLE).entity(error);
	
				return builder.build();
			} else {
	
				logger.info("Creating new temporary zip file containing testCases in "
						+ tempZip.getAbsolutePath());
	
				try {
					tempZip.createNewFile();
	
					FileOutputStream fos = new FileOutputStream(tempZip);
					
					fos.write(form.getFile());
					fos.flush();
					fos.close();
	
				} catch (IOException ioex) {
					logger.fatal("Error while trying to create new file");
					logger.fatal(ioex.getMessage());
	
					builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
	
					return builder.build();
				}
	
				logger.info("Zip file created.");
	
				try {
					final File finalTestCasePath;
					ZipFile zipFile = new ZipFile(tempZip);
	
					finalTestCasePath = new File(TESTCASE_PATH + problem.getIdProblem() + "/testcases/");
	
					logger.info("Extracting Testcases into " + finalTestCasePath.getAbsolutePath());
	
					zipFile.extractAll(finalTestCasePath.getAbsolutePath());
					tempZip.delete();
	
					File aux;
					TestCase testCase;
					
					for (String file : finalTestCasePath.list()) {
						aux = new File(finalTestCasePath + "/" + file);
						
						if (aux.isDirectory()) {
							
							testCase = new TestCase();
							
							testCase.setIdTestCase(0);
							testCase.setPath(aux.getAbsolutePath());
							testCase.setInput(new File(aux.list()[0]));
							testCase.setOutput(new File(aux.list()[1]));
	
							problem.getTestcases().add(testCase);
						}
					}
	
					logger.info("Updating problem data");
					
					problemDao.update(problem);
	
					builder = Response.status(Response.Status.ACCEPTED);
	
				} catch (ZipException zipEx) {
	
					logger.fatal("Error while trying to unzip the testcases file");
					logger.fatal(zipEx.getMessage());
	
					ForCodeError error = ErrorFactory.getErrorFromIndex(ErrorFactory.UNZIP_ERROR);
					builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error);
	
				}
	
			}
		}catch(ForCodeDataException fde){
			logger.warn(fde.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(fde);
		}
		return builder.build();
	}

}
