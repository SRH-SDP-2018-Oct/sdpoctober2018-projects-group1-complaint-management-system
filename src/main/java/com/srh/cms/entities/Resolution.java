package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the resolution database table.
 * 
 */
@Entity
@NamedQuery(name="Resolution.findAll", query="SELECT r FROM Resolution r")
public class Resolution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int resolutionId;
	
	@Temporal(TemporalType.DATE)
	private Date resolutiondate;

	private Time finishedTime;

	private String resolutionTime;
	
	private String feedback;

	private String solution;

	private String status;

	//bi-directional many-to-one association to AssignmentJunction
	@OneToMany(mappedBy="resolution")
	private List<AssignmentJunction> assignmentjunctions;

	//bi-directional many-to-one association to ServiceRequest
	@ManyToOne
	@JoinColumn(name="serviceRequestId")
	private ServiceRequest servicerequest;

	public Resolution() {
	}

	public int getResolutionId() {
		return this.resolutionId;
	}

	public void setResolutionId(int resolutionId) {
		this.resolutionId = resolutionId;
	}

	public Time getFinishedTime() {
		return this.finishedTime;
	}

	public void setFinishedTime(Time finishedTime) {
		this.finishedTime = finishedTime;
	}

	public String getResolutionTime() {
		return this.resolutionTime;
	}

	public void setResolutionTime(String resolutionTime) {
		this.resolutionTime = resolutionTime;
	}

	public String getSolution() {
		return this.solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<AssignmentJunction> getAssignmentjunctions() {
		return this.assignmentjunctions;
	}

	public void setAssignmentjunctions(List<AssignmentJunction> assignmentjunctions) {
		this.assignmentjunctions = assignmentjunctions;
	}

	public AssignmentJunction addAssignmentjunction(AssignmentJunction assignmentjunction) {
		getAssignmentjunctions().add(assignmentjunction);
		assignmentjunction.setResolution(this);

		return assignmentjunction;
	}

	public AssignmentJunction removeAssignmentjunction(AssignmentJunction assignmentjunction) {
		getAssignmentjunctions().remove(assignmentjunction);
		assignmentjunction.setResolution(null);

		return assignmentjunction;
	}

	public ServiceRequest getServicerequest() {
		return this.servicerequest;
	}

	public void setServicerequest(ServiceRequest servicerequest) {
		this.servicerequest = servicerequest;
	}

	public Date getResolutiondate() {
		return resolutiondate;
	}

	public void setResolutiondate(Date resolutiondate) {
		this.resolutiondate = resolutiondate;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	
}