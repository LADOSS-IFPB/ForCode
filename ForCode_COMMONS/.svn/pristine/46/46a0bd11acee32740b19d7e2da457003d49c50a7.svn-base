package br.edu.commons.forcode.contests;

import javax.persistence.Column;
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

@XmlRootElement(name = "score")
@Entity
@Table(name = "tb_score")
@NamedQuery(name = "Score.getAll", query = "from Score")
public class Score {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_score")
	private Integer idScore;
	
	@ManyToOne
	@JoinColumn(name = "fk_id_user_contest")
	private UserContest user;
	
	@ManyToOne
	@JoinColumn(name = "fk_id_problem")
	private Problem problem;
	
	@Column(name = "score")
	private Integer score;
	
	public Score(UserContest user, Problem problem,
			Integer score) {
		super();
		this.user = user;
		this.problem = problem;
		this.score = score;
	}

	@XmlElement
	public Integer getIdScore() {
		return idScore;
	}

	public void setIdScore(Integer idScore) {
		this.idScore = idScore;
	}
	
	@XmlElement
	public UserContest getUser() {
		return user;
	}

	public void setUser(UserContest user) {
		this.user = user;
	}
	
	@XmlElement
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	@XmlElement
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}	
}
