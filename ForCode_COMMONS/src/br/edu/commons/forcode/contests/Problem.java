package br.edu.commons.forcode.contests;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.edu.commons.forcode.entities.Manager;

@XmlRootElement(name = "problem")
@Entity
@Table(name = "tb_problem")
@NamedQuery(name = "Problem.getAll", query = "from Problem")
public class Problem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_problem")
	private Integer idProblem;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "creation_progress")
	private Integer creationProgress;

	@Column(name = "time_limit")
	private Long timeLimit; // milliseconds

	@Lob
	@Fetch(FetchMode.JOIN)
	@Column(name = "description")
	private String description;

	@Lob
	@Fetch(FetchMode.JOIN)
	@Column(name = "input")
	private String input;

	@Lob
	@Fetch(FetchMode.JOIN)
	@Column(name = "output")
	private String output;

	@Column(name = "is_private")
	private boolean isPrivate;

	@ManyToOne
	@JoinColumn(name = "fk_id_problem_setter")
	private Manager problemSetter;
 
	@OneToMany
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "fk_id_problem", nullable = false)
	@Cascade({CascadeType.ALL})
 	private List<TestCase> testcases;
 
	@OneToMany
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "fk_id_problem", nullable = false)
	@Cascade({CascadeType.ALL})
 	private List<TestCaseSample> testcaseSamples;
	
	public Problem(){
	}

	public Problem(Integer idProblem, String title, Long timeLimit, String description,
			String input, String output, boolean isPrivate, Manager problemSetter,
			List<TestCase> testcases, List<TestCaseSample> testcaseSamples, Integer creationProgress) {
		super();
		this.idProblem = idProblem;
		this.timeLimit = timeLimit;
		this.description = description;
		this.input = input;
		this.output = output;
		this.isPrivate = isPrivate;
		this.problemSetter = problemSetter;
		this.testcases = testcases;
		this.testcaseSamples = testcaseSamples;
		this.title = title;
		this.creationProgress = creationProgress;
	}
	
	@XmlElement
	public Integer getIdProblem() {
		return idProblem;
	}

	public void setIdProblem(Integer idProblem) {
		this.idProblem = idProblem;
	}
	
	@XmlElement
	public Long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@XmlElement
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	@XmlElement
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	
	@XmlElement
	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	
	@XmlElement
	public Manager getProblemSetter() {
		return problemSetter;
	}

	public void setProblemSetter(Manager problemSetter) {
		this.problemSetter = problemSetter;
	}

	@XmlElement
	public List<TestCase> getTestcases() {
		return testcases;
	}

	public void setTestcases(List<TestCase> testcases) {
		this.testcases = testcases;
	}

	@XmlElement
	public List<TestCaseSample> getTestcaseSamples() {
		return testcaseSamples;
	}

	public void setTestcaseSamples(List<TestCaseSample> testcaseSamples) {
		this.testcaseSamples = testcaseSamples;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public Integer getCreationProgress() {
		return creationProgress;
	}

	public void setCreationProgress(Integer creationProgress) {
		this.creationProgress = creationProgress;
	}

	@Override
	public String toString() {
		return "Problem [idProblem=" + idProblem + ", title=" + title
				+ ", creationProgress=" + creationProgress + ", timeLimit="
				+ timeLimit + ", description=" + description + ", input="
				+ input + ", output=" + output + ", isPrivate=" + isPrivate
				+ ", problemSetter=" + problemSetter + ", testcases="
				+ testcases + ", testcaseSamples=" + testcaseSamples + "]";
	}
}
