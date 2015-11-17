package br.edu.commons.forcode.contests;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import br.edu.commons.forcode.enumerations.FileType;

@XmlRootElement(name = "forcodefile")
public class ForCodeUploadFile {
	@FormParam("fileName")
	@PartType("text/plain")
	private String fileName;
	
	@FormParam("fileExtension")
	@PartType("text/plain")
	private String fileExtension;
	
	@FormParam("fileType")
	@PartType("application/json")
	private FileType fileType;
	
	@FormParam("uploadedFile")
	@PartType("application/octet-stream")
	private byte[] file;

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@XmlElement
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@XmlElement
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String extension) {
		this.fileExtension = extension;
	}

	@XmlElement
	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

}
