package br.edu.commons.forcode.contests;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@XmlRootElement(name = "testcasesample")
@Entity
@Table(name = "tb_testcase_sample")
@NamedQuery(name = "TestCaseSample.getAll", query = "from TestCaseSample")
public class TestCaseSample {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_testcase_sample")
	private Integer idTestCaseSample;

	@Lob
	@Fetch(FetchMode.JOIN)
	@Column(name = "input")
	private String input;

	@Lob
	@Fetch(FetchMode.JOIN)
	@Column(name = "output")
	private String output;

	public TestCaseSample() {

	}

	public TestCaseSample(Integer idTestCaseSample, String input, String output) {
		super();
		this.idTestCaseSample = idTestCaseSample;
		this.input = input;
		this.output = output;
	}

	public Integer getIdTestCaseSample() {
		return idTestCaseSample;
	}

	public void setIdTestCaseSample(Integer idTestCaseSample) {
		this.idTestCaseSample = idTestCaseSample;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "TestCaseSample [idTestCaseSample=" + idTestCaseSample + ", input=" + input
				+ ", output=" + output + "]";
	}
	
}
