package br.edu.commons.forcode.contests;

import java.io.File;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "submission")
@Entity
@Table(name = "tb_submission")
@NamedQuery(name = "Submission.getAll", query = "from Submission")
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_submission")
	private Integer idSubmission;

	@Column(name = "path")
	private String path;

	@Column(name = "verdict")
	private Integer verdict;

	@Transient
	private File submissionFile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "moment", columnDefinition = "TIMESTAMP DEFAULT current_timestamp", insertable = false, updatable = false)
	private Calendar timestamp;

	@ManyToOne
	@JoinColumn(name = "fk_id_problem")
	private Problem problem;

	@ManyToOne
	@JoinColumn(name = "fk_id_user_contest")
	private UserContest user;

	@ManyToOne
	@JoinColumn(name = "fk_id_language")
	private Language language;

	public Submission() {
	}

	public Submission(Integer idSubmission, String path, Integer verdict,
			File submissionFile, Calendar timestamp, Problem problem,
			UserContest user, Language language) {

		super();
		this.idSubmission = idSubmission;
		this.path = path;
		this.verdict = verdict;
		this.submissionFile = submissionFile;
		this.timestamp = timestamp;
		this.problem = problem;
		this.user = user;
		this.language = language;
	}

	@XmlElement
	public Integer getVerdict() {
		return verdict;
	}

	public void setVerdict(Integer verdict) {
		this.verdict = verdict;
	}

	@XmlElement
	public Integer getIdSubmission() {
		return idSubmission;
	}

	public void setIdSubmission(Integer idSubmission) {
		this.idSubmission = idSubmission;
	}

	@XmlElement
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@XmlElement
	public File getFileSubmission() {
		return submissionFile;
	}

	public void setFileSubmission(File submissionFile) {
		this.submissionFile = submissionFile;
	}

	@XmlElement
	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	@XmlElement
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@XmlElement
	public UserContest getUser() {
		return user;
	}

	public void setUser(UserContest user) {
		this.user = user;
	}

	@XmlElement
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
