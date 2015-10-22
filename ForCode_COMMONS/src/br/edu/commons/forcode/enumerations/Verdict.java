package br.edu.commons.forcode.enumerations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "verdict")
public enum Verdict {
	ACCEPTED(1, "Accepted", "AC"), 
	WRONG_ANSWER(2, "Wrong Answer", "WA"), 
	COMPILATION_ERROR(3, "Compilation Error", "CE"),
	TIME_LIMIT_EXCEEDED(4, "Time Limit Exceeded", "TLE"),
	RUNTIMER_ERROR(5, "Runtimer Error", "RTE"),
	UNKNOWN_ERROR(6, "Unknown Error", "UE"),
	IN_QUEUE(7, "In Queue", "IE");

	private final int typeValue;
	private final String typeName;
	private final String typeNameInitials;	  

	Verdict(int typeValue, String typeName, String typeNameInitials) {
		this.typeValue = typeValue;
		this.typeName = typeName;
		this.typeNameInitials = typeNameInitials;
	}
	
	@XmlElement
	public int getTypeValue() {
		return this.typeValue;
	}

	@XmlElement
	public String getTypeName() {
		return this.typeName;
	}
	
	@XmlElement
	public String getTypeNameInitials() {
		return typeNameInitials;
	}

	public static final Verdict getTypeVerdictById(int typeId)
			throws IllegalArgumentException {
		for (Verdict typeVerdict : Verdict.values()) {
			if (typeVerdict.typeValue == typeId)
				return typeVerdict;
		}
		throw new IllegalArgumentException(
				"There is no TypeVerdict for the specified typeCode.");
	}

	public static final  Verdict getTypeVerdictByTypeName(String typeName)
			throws IllegalArgumentException {
		for (Verdict TypeVerdict : Verdict.values()) {
			if (TypeVerdict.typeName.equalsIgnoreCase(typeName))
				return TypeVerdict;
		}
		throw new IllegalArgumentException(
				"There is no TypeVerdict for the specified typeName.");
	}

	public static final Verdict getTypeVerdictByTypeNameInitials(String typeNameInitials)
			throws IllegalArgumentException {
		for (Verdict TypeVerdict : Verdict.values()) {
			if (TypeVerdict.typeNameInitials.equalsIgnoreCase(typeNameInitials))
				return TypeVerdict;
		}
		throw new IllegalArgumentException(
				"There is no TypeVerdict for the specified typeClass.");
	}
}
