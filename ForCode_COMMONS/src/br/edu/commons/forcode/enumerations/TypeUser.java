package br.edu.commons.forcode.enumerations;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.edu.commons.forcode.entities.Admin;
import br.edu.commons.forcode.entities.Contestant;
import br.edu.commons.forcode.entities.Manager;
import br.edu.commons.forcode.entities.User;

@XmlRootElement (name = "typeuser")
public enum TypeUser {
	ADMIN(1, "ADMIN", Admin.class), 
	MANAGER(2, "MANAGER", Manager.class), 
	CONTESTANT(3, "CONTESTANT", Contestant.class);

	private final int typeValue;
	private final Class<User> typeClass;
	private final String typeName;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	TypeUser(int typeValue, String typeName, Class classType) {
		this.typeValue = typeValue;
		this.typeName = typeName;
		this.typeClass = classType;
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
	public Class<User> getTypeClass() {
		return typeClass;
	}

	public static final TypeUser getTypeUserByCode(int typeCode)
			throws IllegalArgumentException {
		for (TypeUser typeUser : TypeUser.values()) {
			if (typeUser.typeValue == typeCode)
				return typeUser;
		}
		throw new IllegalArgumentException(
				"There is no TypeUser for the specified typeCode.");
	}

	public static final  TypeUser getTypeUserByTypeName(String typeName)
			throws IllegalArgumentException {
		for (TypeUser typeUser : TypeUser.values()) {
			if (typeUser.typeName.equalsIgnoreCase(typeName))
				return typeUser;
		}
		throw new IllegalArgumentException(
				"There is no TypeUser for the specified typeName.");
	}

	public static final TypeUser getTypeUserByTypeClass(Class<User> typeClass)
			throws IllegalArgumentException {
		for (TypeUser typeUser : TypeUser.values()) {
			if (typeUser.typeClass.equals(typeClass))
				return typeUser;
		}
		throw new IllegalArgumentException(
				"There is no TypeUser for the specified typeClass.");
	}
}
