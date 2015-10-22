package br.edu.commons.forcode.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.edu.commons.forcode.enumerations.TypeUser;

@Entity
@XmlRootElement (name = "contestant")
@DiscriminatorValue("CONTESTANT")
public class Contestant extends User {
	@ManyToOne
	@JoinColumn(name = "id_institution")
	private Institution institution;
	
	public Contestant() {
		super();
		super.setTypeUser(TypeUser.CONTESTANT);
	}

	public Contestant(Integer idUser, TypeUser typeUser, String username,
			String email, String password, Institution institution) {
		super(idUser, "CONTESTANT", username, email, password);
		this.institution = institution;
	}

	@Override
	public String toString() {
		return super.toString() + "Contestant [institution=" + institution + "]";
	}

	@XmlElement
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@Override
	@XmlElement
	public TypeUser getTypeUser() {
		return TypeUser.CONTESTANT;
	}
}
