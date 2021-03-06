package com.nuaa.ec.model;

import java.util.HashSet;
import java.util.Set;

/**
 * TfprofessionalProjectDeclareProject entity. @author MyEclipse Persistence
 * Tools
 */

public class TfprofessionalProjectDeclareProject implements
		java.io.Serializable {

	// Fields

	private String projectId;
	private TfprofessionalProjectDeclareLevel tfprofessionalProjectDeclareLevel;
	private Tfterm tfterm;
	private String projectName;
	private Double projectSumScore;
	private String spareTire;
	private String checkOut;
	private String chargePersonId;
	private String chargePersonName;
	private Set tfprofessionalProjectDeclarePerformances = new HashSet(0);
	private String departmentId;

	public TfprofessionalProjectDeclareProject(
			String projectId,
			TfprofessionalProjectDeclareLevel tfprofessionalProjectDeclareLevel,
			Tfterm tfterm, String projectName, Double projectSumScore,
			String spareTire, String checkOut, String chargePersonId,
			Set tfprofessionalProjectDeclarePerformances, String departmentId) {
		this.projectId = projectId;
		this.tfprofessionalProjectDeclareLevel = tfprofessionalProjectDeclareLevel;
		this.tfterm = tfterm;
		this.projectName = projectName;
		this.projectSumScore = projectSumScore;
		this.spareTire = spareTire;
		this.checkOut = checkOut;
		this.chargePersonId = chargePersonId;
		this.tfprofessionalProjectDeclarePerformances = tfprofessionalProjectDeclarePerformances;
		this.departmentId = departmentId;
	}

	// Constructors

	/** default constructor */
	public TfprofessionalProjectDeclareProject() {
	}

	/** minimal constructor */
	public TfprofessionalProjectDeclareProject(String projectId) {
		this.projectId = projectId;
	}

	/** full constructor */
	public TfprofessionalProjectDeclareProject(
			String projectId,
			TfprofessionalProjectDeclareLevel tfprofessionalProjectDeclareLevel,
			Tfterm tfterm, String projectName, Double projectSumScore,
			String spareTire, String checkOut,
			Set tfprofessionalProjectDeclarePerformances) {
		this.projectId = projectId;
		this.tfprofessionalProjectDeclareLevel = tfprofessionalProjectDeclareLevel;
		this.tfterm = tfterm;
		this.projectName = projectName;
		this.projectSumScore = projectSumScore;
		this.spareTire = spareTire;
		this.checkOut = checkOut;
		this.tfprofessionalProjectDeclarePerformances = tfprofessionalProjectDeclarePerformances;
	}

	// Property accessors

	public TfprofessionalProjectDeclareProject(
			String projectId,
			TfprofessionalProjectDeclareLevel tfprofessionalProjectDeclareLevel,
			Tfterm tfterm, String projectName, Double projectSumScore,
			String spareTire, String checkOut, String chargePersonId,
			Set tfprofessionalProjectDeclarePerformances) {
		super();
		this.projectId = projectId;
		this.tfprofessionalProjectDeclareLevel = tfprofessionalProjectDeclareLevel;
		this.tfterm = tfterm;
		this.projectName = projectName;
		this.projectSumScore = projectSumScore;
		this.spareTire = spareTire;
		this.checkOut = checkOut;
		this.chargePersonId = chargePersonId;
		this.tfprofessionalProjectDeclarePerformances = tfprofessionalProjectDeclarePerformances;
	}

	public TfprofessionalProjectDeclareProject(
			String projectId,
			TfprofessionalProjectDeclareLevel tfprofessionalProjectDeclareLevel,
			Tfterm tfterm, String projectName, Double projectSumScore,
			String spareTire, String checkOut, String chargePersonId,
			String chargePersonName, String departmentId) {
		super();
		this.projectId = projectId;
		this.tfprofessionalProjectDeclareLevel = tfprofessionalProjectDeclareLevel;
		this.tfterm = tfterm;
		this.projectName = projectName;
		this.projectSumScore = projectSumScore;
		this.spareTire = spareTire;
		this.checkOut = checkOut;
		this.chargePersonId = chargePersonId;
		this.chargePersonName = chargePersonName;
		this.departmentId = departmentId;
	}

	public String getChargePersonName() {
		return chargePersonName;
	}

	public void setChargePersonName(String chargePersonName) {
		this.chargePersonName = chargePersonName;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public TfprofessionalProjectDeclareLevel getTfprofessionalProjectDeclareLevel() {
		return this.tfprofessionalProjectDeclareLevel;
	}

	public void setTfprofessionalProjectDeclareLevel(
			TfprofessionalProjectDeclareLevel tfprofessionalProjectDeclareLevel) {
		this.tfprofessionalProjectDeclareLevel = tfprofessionalProjectDeclareLevel;
	}

	public Tfterm getTfterm() {
		return this.tfterm;
	}

	public void setTfterm(Tfterm tfterm) {
		this.tfterm = tfterm;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getProjectSumScore() {
		return this.projectSumScore;
	}

	public void setProjectSumScore(Double projectSumScore) {
		this.projectSumScore = projectSumScore;
	}

	public String getChargePersonId() {
		return chargePersonId;
	}

	public void setChargePersonId(String chargePersonId) {
		this.chargePersonId = chargePersonId;
	}

	public String getSpareTire() {
		return this.spareTire;
	}

	public void setSpareTire(String spareTire) {
		this.spareTire = spareTire;
	}

	public String getCheckOut() {
		return this.checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public Set getTfprofessionalProjectDeclarePerformances() {
		return this.tfprofessionalProjectDeclarePerformances;
	}

	public void setTfprofessionalProjectDeclarePerformances(
			Set tfprofessionalProjectDeclarePerformances) {
		this.tfprofessionalProjectDeclarePerformances = tfprofessionalProjectDeclarePerformances;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

}