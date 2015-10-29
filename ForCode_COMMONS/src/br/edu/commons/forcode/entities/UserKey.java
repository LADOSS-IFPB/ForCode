package br.edu.commons.forcode.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="userkey")
@Entity
@Table(name="tb_user_key")
@NamedQuery(name = "UserKey.getAll", query = "from UserKey")
public class UserKey {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user_key")
	private Integer id;
	
	@Column(name="header_key", nullable=false)
	private String key;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_timestamp", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar creationTimestamp;

	public UserKey(String key) {
		this.setKey(key);
	}
	
	public UserKey(){}
	
	@XmlElement
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@XmlElement
	public Calendar getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Calendar creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	
}