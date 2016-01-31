package br.edu.commons.forcode.exceptions;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "forCodeDataException")
public class ForCodeDataException extends Exception {

	/**
	 *v0.1
	 */
	private static final long serialVersionUID = 2794113774245425609L;
	
	public ForCodeDataException(String message){
		super(message);
	}
	@XmlElement
	public String getMessage(){
		return super.getMessage();
	}
}
