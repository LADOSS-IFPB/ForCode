package br.edu.commons.forcode.contests;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "language")
@Entity
@Table(name = "tb_language")
@NamedQuery(name = "Language.getAll", query = "from Language")
public class Language {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_language")
	private Integer idLanguage;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	//Sample: g++ -std=c++11 %s -o %s
	@Column(name = "compilation_command", nullable = false)
	private String compilationCommand;
	
	//Sample: ./%s < %s > %s/output.txt
	@Column(name = "executiontion_command", nullable = false)
	private String executionCommand;

	public Language() {
	}

	public Language(Integer idLanguage, String name, String compilationCommand, String executionCommand) {
		super();
		this.idLanguage = idLanguage;
		this.name = name;
		this.compilationCommand = compilationCommand;
		this.executionCommand = executionCommand;
	}

	@XmlElement
	public Integer getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(Integer idLanguage) {
		this.idLanguage = idLanguage;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getCompilationCommand() {
		return compilationCommand;
	}

	public void setCompilationCommand(String compilationCommand) {
		this.compilationCommand = compilationCommand;
	}

	@XmlElement
	public String getExecutionCommand() {
		return executionCommand;
	}

	public void setExecutionCommand(String executionCommand) {
		this.executionCommand = executionCommand;
	}
}
