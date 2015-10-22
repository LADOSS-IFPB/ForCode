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

@XmlRootElement(name = "clarification")
@Entity
@Table(name = "tb_clarification")
@NamedQuery(name = "Clarification.getAll", query = "from Clarification")
public class Clarification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_clarification")
	private Integer idClarification;

	@Column(name = "question", nullable = false)
	private String question;

	@Column(name = "answer")
	private String answer;

	@ManyToOne
	@JoinColumn(name = "fk_id_user")
	private UserContest user;

	@ManyToOne
	@JoinColumn(name = "fk_id_problem")
	private Problem problem;

	public Clarification() {
	}

	public Clarification(Integer idClarification, String question,
			String answer, UserContest user, Problem problem) {
		super();
		this.idClarification = idClarification;
		this.question = question;
		this.answer = answer;
		this.user = user;
		this.problem = problem;
	}

	@XmlElement
	public Integer getIdClarification() {
		return idClarification;
	}

	public void setIdClarification(Integer idClarification) {
		this.idClarification = idClarification;
	}

	@XmlElement
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@XmlElement
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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
}
