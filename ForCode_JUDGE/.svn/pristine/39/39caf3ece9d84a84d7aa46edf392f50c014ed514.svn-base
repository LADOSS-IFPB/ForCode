package br.edu.judge.forcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.contests.TestCase;
import br.edu.commons.forcode.enumerations.Verdict;
import br.edu.judge.forcode.util.FileUtils;
import br.edu.judge.forcode.util.ProcessUtils;
import br.edu.judge.forcode.util.TimeControl;

@Path("/judge")
public class JudgeService {

	@POST
	@Path("/submission")
	@Consumes("application/json")
	@Produces("application/json")
	public Verdict judgeSubmission(Submission submission) {
		if (JudgeService.compileSubmission(submission) == false) {
			return Verdict.COMPILATION_ERROR;
		} else {

			for (TestCase testCase : submission.getProblem().getTestcases()) {
				Verdict verdict = runTestCase(submission, testCase);
				
				if (verdict.equals(Verdict.ACCEPTED) == false) {
					return verdict;
				}
			}

			return Verdict.ACCEPTED;
		}
	}

	// Returns true if there is no compilation errors, returns false otherwise
	public static boolean compileSubmission(Submission submission) {
		try {
			File run = new File(submission.getPath() + "/compile.sh");

			Language language = submission.getLanguage();
			String compilationCommand;
			String submissionPath = submission.getFileSubmission()
					.getAbsolutePath();

			if (language.getName().equalsIgnoreCase("CPP")
					|| language.getName().equalsIgnoreCase("C")) {

				compilationCommand = String.format(
						language.getCompilationCommand(),
						submissionPath,
						submissionPath.substring(0,
								submissionPath.lastIndexOf(".")));
			} else {
				compilationCommand = String.format(
						language.getCompilationCommand(),
						submissionPath.substring(0,
								submissionPath.lastIndexOf(".")));
			}

			String content = "cd \"" + submission.getPath() + "\"\n"
					+ compilationCommand + " 2> " + submission.getPath()
					+ "/errors.err";
			FileUtils.addContent(run, content);

			ProcessUtils.giveExecutePermission(run);
			ProcessUtils.runScript(run);

			File executable;

			if (language.getName().equalsIgnoreCase("JAVA")) {
				executable = new File(submissionPath.substring(0,
						submissionPath.lastIndexOf(".")) + ".class");
			} else {
				executable = new File(submissionPath.substring(0,
						submissionPath.lastIndexOf(".")));
			}

			return executable.exists();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Verdict runTestCase(Submission submission, TestCase testCase) {
		try {
			File run = new File(submission.getPath() + "/run.sh");

			String executionCommand = submission.getLanguage()
					.getExecutionCommand();
			String submissionPath = submission.getFileSubmission()
					.getAbsolutePath();

			String content = "cd \""
					+ submission.getPath()
					+ "\"\n"
					+ String.format(executionCommand, submissionPath.substring(
							0, submissionPath.lastIndexOf(".")), testCase
							.getInput().getAbsolutePath(), submission.getPath());
			
			FileUtils.addContent(run, content);

			ProcessUtils.giveExecutePermission(run);

			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(run.getAbsolutePath());

			TimeControl timeControl = new TimeControl(process, submission
					.getProblem().getTimeLimit());

			timeControl.start();
			process.waitFor();

			if (timeControl.isTimeOut()) {
				return Verdict.TIME_LIMIT_EXCEEDED;
			}

			if (FileUtils
					.fileDiff(new File(submission.getPath()), new File(
							submission.getPath() + "/output.txt"), testCase
							.getOutput()) == true) {
				return Verdict.WRONG_ANSWER;
			} else {
				return Verdict.ACCEPTED;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Verdict.UNKNOWN_ERROR;
	}
}
