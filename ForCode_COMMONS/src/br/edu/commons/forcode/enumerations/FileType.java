package br.edu.commons.forcode.enumerations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;

public enum FileType {
	@XmlEnumValue("1") SUBMISSION_FILE(1),
	@XmlEnumValue("2") PROBLEM_TEST_ZIP(2);
	
	private final int id;
	
	FileType(int id){
		this.id = id;
	}
	
	@XmlElement
	public int getId(){
		return id;
	}
}
