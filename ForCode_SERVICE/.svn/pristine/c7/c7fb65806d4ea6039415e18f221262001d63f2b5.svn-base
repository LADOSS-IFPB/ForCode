package br.edu.service.forcode.judgeServices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.enumerations.Verdict;

public interface JudgeService {

	@POST
	@Path("/judge/submission")
	@Consumes("application/json")
	@Produces("application/json")
	public Verdict judgeSubmission(Submission submission);

}
