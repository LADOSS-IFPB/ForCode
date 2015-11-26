package br.edu.commons.forcode.contests;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testcase")
@Entity
@Table(name = "tb_testcase")
@NamedQuery(name = "TestCase.getAll", query = "from TestCase")
public class TestCase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_testcase")
	private Integer idTestCase;

	@Column(name = "path")
	private String path;

	@Transient
	private File input;
	@Transient
	private File output;

	public TestCase() {
	}

	public TestCase(Integer idTestCase, String path, File input, File output) {
		super();
		this.idTestCase = idTestCase;
		this.path = path;
		this.input = input;
		this.output = output;
	}

	@XmlElement
	public Integer getIdTestCase() {
		return idTestCase;
	}

	public void setIdTestCase(Integer idTestCase) {
		this.idTestCase = idTestCase;
	}

	@XmlElement
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		this.setInput(new File(path + path.substring(path.lastIndexOf('/')) + ".in"));
		this.setOutput(new File(path + path.substring(path.lastIndexOf('/')) + ".out"));
	}

	@XmlElement
	public File getInput() {
		return input;
	}

	public void setInput(File input) {
		this.input = input;
	}

	@XmlElement
	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}
	
	

	@Override
	public String toString() {
		return "TestCase [idTestCase=" + idTestCase + ", path=" + path + ", input=" + input
				+ ", output=" + output + "]";
	}

}
