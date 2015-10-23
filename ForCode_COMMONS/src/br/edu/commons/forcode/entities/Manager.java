package br.edu.commons.forcode.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.edu.commons.forcode.enumerations.TypeUser;

@XmlRootElement (name = "manager")
@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {
	@ManyToOne
	@JoinColumn(name = "id_institution")
	private Institution institution;

	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToOne
	@JoinColumn(name = "id_created_by")
	private Admin createdBy;

	public Manager() {
		super();
		super.setTypeUser(TypeUser.MANAGER);
	}

	public Manager(Integer idUser, TypeUser typeUser, String username,
			String email, String password, Institution institution,
			String phoneNumber, Admin createdBy) {
		super(idUser, "Manager", username, email, password);
		this.institution = institution;
		this.phoneNumber = phoneNumber;
		this.createdBy = createdBy;
	}

	@XmlElement
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@XmlElement
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@XmlElement
	public Admin getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Admin createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	@XmlElement
	public TypeUser getTypeUser() {
		return TypeUser.MANAGER;
	}

}
