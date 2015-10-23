package br.edu.commons.forcode.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class ForCodeError {
	private int code;
	private String message;

	public ForCodeError() {}
	
	public ForCodeError(int codigo, String mensagem) {
		this.code = codigo;
		this.message = mensagem;
	}
	
	@XmlElement
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@XmlElement
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
