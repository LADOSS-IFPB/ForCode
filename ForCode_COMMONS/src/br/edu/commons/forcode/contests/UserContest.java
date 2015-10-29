package br.edu.commons.forcode.contests;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.edu.commons.forcode.entities.Contestant;

@XmlRootElement(name = "usercontest")
@Entity
@Table(name = "tb_user_contest")
@NamedQuery(name = "UserContest.getAll", query = "from UserContest")
public class UserContest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_contest")
	private Integer idUserContest;

	@Column(name = "is_valid", nullable = false)
	private boolean isValid;

	@ManyToOne
	@JoinColumn(name = "fk_id_user")
	private Contestant user;

	@ElementCollection
	@Fetch(FetchMode.JOIN)
	@CollectionTable(name = "tb_score", joinColumns = @JoinColumn(name = "fk_id_user_contest", nullable = false))
	@Cascade({CascadeType.ALL})
	private List<Score> score;

	public UserContest() {
	}

	public UserContest(Integer idUserContest, Contestant user,
			List<Score> score) {
		super();
		this.user = user;
		this.score = score;
	}

	@XmlElement
	public Integer getIdUserContest() {
		return idUserContest;
	}

	public void setIdUserContest(Integer idUserContest) {
		this.idUserContest = idUserContest;
	}

	@XmlElement
	public Contestant getUser() {
		return user;
	}

	public void setUser(Contestant user) {
		this.user = user;
	}

	@XmlElement
	public List<Score> getScore() {
		return score;
	}

	public void setScore(List<Score> score) {
		this.score = score;
	}

	@XmlElement
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
