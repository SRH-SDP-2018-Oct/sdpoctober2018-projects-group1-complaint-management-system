package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the assignmentjunction database table.
 * 
 */
@Entity
@NamedQuery(name="AssignmentJunction.findAll", query="SELECT a FROM AssignmentJunction a")
public class AssignmentJunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int assignmentJunctionId;

	//bi-directional many-to-one association to Resolution
	@ManyToOne
	@JoinColumn(name="resolutionId")
	private Resolution resolution;

	//bi-directional many-to-one association to ServiceRequest
	@ManyToOne
	@JoinColumn(name="serviceRequestId")
	private ServiceRequest servicerequest;

	//bi-directional many-to-one association to Technician
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="technicianId")
	private Technician technician;

	public AssignmentJunction() {
	}

	public int getAssignmentJunctionId() {
		return this.assignmentJunctionId;
	}

	public void setAssignmentJunctionId(int assignmentJunctionId) {
		this.assignmentJunctionId = assignmentJunctionId;
	}

	public Resolution getResolution() {
		return this.resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public ServiceRequest getServicerequest() {
		return this.servicerequest;
	}

	public void setServicerequest(ServiceRequest servicerequest) {
		this.servicerequest = servicerequest;
	}

	public Technician getTechnician() {
		return this.technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

}