package br.edu.commons.forcode.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import br.edu.commons.forcode.enumerations.TypeUser;

@XmlRootElement(name = "user")
@JsonTypeInfo(
use = JsonTypeInfo.Id.CLASS,
include = JsonTypeInfo.As.PROPERTY,
property = "typeUser")
@JsonSubTypes({
@JsonSubTypes.Type(value = Contestant.class, name = "br.edu.forcode.commons.Contestant"),
@JsonSubTypes.Type(value = Admin.class, name = "br.edu.forcode.commons.Admin"),
@JsonSubTypes.Type(value = Manager.class, name = "br.edu.forcode.commons.Manager"),
})

@Entity
@Table(name = "tb_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_user", discriminatorType = DiscriminatorType.STRING)
@NamedQuery(name = "User.getAll", query = "from User")
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Integer idUser;

	@Transient
	private TypeUser typeUser;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Basic(fetch = FetchType.LAZY)
 	@OneToOne(mappedBy = "user", targetEntity = UserKey.class)
	private UserKey userKey;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	public User() {
		super();
	}

	public User(Integer idUser, String typeName, String username, String email,
			String password) {
		super();
		this.idUser = idUser;
		this.typeUser = TypeUser.getTypeUserByTypeName(typeName);
		this.username = username;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", typeUser=" + typeUser
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userKey=" + userKey + ", username=" + username
				+ ", email=" + email + ", password=" + password + "]";
	}

	@XmlElement
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public abstract TypeUser getTypeUser();

	public void setTypeUser(TypeUser typeUser) {
		this.typeUser = typeUser;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserKey getUserKey() {
		return userKey;
	}

	public void setUserKey(UserKey userKey) {
		this.userKey = userKey;
	}
}
