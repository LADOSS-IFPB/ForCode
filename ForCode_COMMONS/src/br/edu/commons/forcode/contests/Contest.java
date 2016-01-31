package br.edu.commons.forcode.contests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.mapping.Array;

import br.edu.commons.forcode.entities.Manager;

@XmlRootElement(name = "contest")
@Entity
@Table(name = "tb_contest")
@NamedQuery(name = "Contest.getAll", query = "from Contest")
public class Contest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_contest")
	private Integer idContest;

	@Column(name = "name")
	private String name;

	@Lob
	@Fetch(FetchMode.JOIN)
	@Column(name = "description")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	private Calendar startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false)
	private Calendar endDate;

	@ManyToOne
	@JoinColumn(name = "fk_id_contest_manager")
	private Manager contestManager;

	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "tb_contest_problem", 
		joinColumns = { @JoinColumn(name = "fk_id_contest", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "fk_id_problem", nullable = false, updatable = false) })
	private List<Problem> problems;


	@ElementCollection
	@Fetch(FetchMode.JOIN)
	@CollectionTable(name = "tb_user_contest", joinColumns = @JoinColumn(name = "fk_id_user_contest", nullable = false))
	@Cascade({CascadeType.ALL})
	private List<UserContest> contestants = new ArrayList<UserContest>();

	public Contest() {
		contestants = new ArrayList<UserContest>();
		problems = new ArrayList<Problem>();
	}

	public Contest(Integer idContest, String description, Calendar startDate,
			Calendar endDate, Manager contestManager, String name) {
		super();
		this.idContest = idContest;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contestManager = contestManager;
		this.name = name;
	}

	@XmlElement
	public Integer getIdContest() {
		return idContest;
	}

	public void setIdContest(Integer idContest) {
		this.idContest = idContest;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	@XmlElement
	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@XmlElement
	public Manager getContestManager() {
		return contestManager;
	}

	public void setContestManager(Manager contestManager) {
		this.contestManager = contestManager;
	}

	@XmlElement
	public List<UserContest> getContestants() {
		return contestants;
	}

	public void setContestants(List<UserContest> contestants) {
		this.contestants = contestants;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}

	@Override
	public String toString() {
		return "Contest [idContest=" + idContest + ", name=" + name + ", description="
				+ description + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", contestManager=" + contestManager + ", problems=" + problems
				+ ", contestants=" + contestants + "]";
	}
	
}
