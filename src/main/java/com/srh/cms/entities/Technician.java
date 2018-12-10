package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the technician database table.
 * 
 */
@Entity
@NamedQuery(name="Technician.findAll", query="SELECT t FROM Technician t")
public class Technician implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int technicianId;

	private String email;

	private byte isActive;

	private String jobrole;

	private String password;

	private String phoneNo;

	private String technicalName;

	private String techusername;
	
	private int openTickets;
	
	private int totalTickets;

	//bi-directional many-to-one association to AssignmentJunction
	@OneToMany(mappedBy="technician")
	private List<AssignmentJunction> assignmentjunctions;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;

	public Technician() {
	}

	public int getTechnicianId() {
		return this.technicianId;
	}

	public void setTechnicianId(int technicianId) {
		this.technicianId = technicianId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getJobrole() {
		return this.jobrole;
	}

	public void setJobrole(String jobrole) {
		this.jobrole = jobrole;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getTechnicalName() {
		return this.technicalName;
	}

	public void setTechnicalName(String technicalName) {
		this.technicalName = technicalName;
	}

	public String getTechusername() {
		return this.techusername;
	}

	public void setTechusername(String techusername) {
		this.techusername = techusername;
	}

	public List<AssignmentJunction> getAssignmentjunctions() {
		return this.assignmentjunctions;
	}

	public void setAssignmentjunctions(List<AssignmentJunction> assignmentjunctions) {
		this.assignmentjunctions = assignmentjunctions;
	}

	public AssignmentJunction addAssignmentjunction(AssignmentJunction assignmentjunction) {
		getAssignmentjunctions().add(assignmentjunction);
		assignmentjunction.setTechnician(this);

		return assignmentjunction;
	}

	public AssignmentJunction removeAssignmentjunction(AssignmentJunction assignmentjunction) {
		getAssignmentjunctions().remove(assignmentjunction);
		assignmentjunction.setTechnician(null);

		return assignmentjunction;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getOpenTickets() {
		return openTickets;
	}

	public void setOpenTickets(int openTickets) {
		this.openTickets = openTickets;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

    

}