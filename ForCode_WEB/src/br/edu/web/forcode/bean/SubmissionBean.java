package br.edu.web.forcode.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.commons.forcode.contests.ForCodeUploadFile;
import br.edu.commons.forcode.contests.Language;
import br.edu.commons.forcode.contests.Submission;
import br.edu.commons.forcode.enumerations.FileType;
import br.edu.web.forcode.service.ForCodeService;
import br.edu.web.forcode.service.ProviderServiceFactory;

@ManagedBean(name = "submissionBean")
@RequestScoped
public class SubmissionBean {
	
	private static final Logger logger = LogManager.getLogger(AdminBean.class
			.getName());
	
	private static final ForCodeService service = ProviderServiceFactory
			.createServiceClient(ForCodeService.class);
	
	private Language language;
	private Part uploadedFile;
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public String submit(String idContest, String idProblem, String idUser){
		Submission submission = new Submission();
		
		submission.setProblem(service.getById(Integer.parseInt(idProblem)));
		submission.setUser(service.getUserContest(Integer.parseInt(idContest), Integer.parseInt(idUser)));
		submission.setLanguage(language);
		
		submission = (Submission) service.createSubmission(submission).readEntity(Submission.class);
		
		ForCodeUploadFile form = new ForCodeUploadFile();
		try{
			form.setFile(IOUtils.toByteArray(uploadedFile.getInputStream()));	
			form.setFileType(FileType.SUBMISSION_FILE);
			form.setFileExtension(language.getFileExtension());
			form.setFileName(uploadedFile.getName());
			
			service.judgeSubmissionFile(submission.getIdSubmission(), form);
		}catch(IOException ioex){
			logger.warn("Could not convert inputStream from uploaded file to byte array, there's something wrong.");
		}
		
		return "submission-queue.xhtml";
	}
}
