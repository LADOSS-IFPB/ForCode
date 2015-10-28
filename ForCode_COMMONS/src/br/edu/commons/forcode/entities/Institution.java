package br.edu.commons.forcode.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "institution")
@Entity
@Table(name = "tb_institution")
@NamedQuery(name = "Institution.getAll", query = "from Institution")
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idInstitution;

	@Column(name = "name")
	private String name;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "city")
	private String city;

	public Institution() {
		super();
	}

	public Institution(Integer idInstitution, String name) {
		super();
		this.idInstitution = idInstitution;
		this.name = name;
	}

	@XmlElement
	public Integer getIdInstitution() {
		return idInstitution;
	}

	public void setIdInstitution(Integer idInstitution) {
		this.idInstitution = idInstitution;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Institution [idInstitution=" + idInstitution + ", name=" + name + ", country="
				+ country + ", city=" + city + "]";
	}
}
