package br.edu.commons.forcode.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;

import br.edu.commons.forcode.enumerations.TypeUser;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
	@Column(name = "phone_number")
	private String phoneNumber;

	public Admin() {
		super();
		super.setTypeUser(TypeUser.ADMIN);
	}

	public Admin(Integer idUser, TypeUser typeUser, String username,
			String email, String password, String phoneNumber) {
		super(idUser, "Admin", username, email, password);
		this.phoneNumber = phoneNumber;
	}
	
	@XmlElement
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	@XmlElement
	public TypeUser getTypeUser() {
		return TypeUser.ADMIN;
	}
}
