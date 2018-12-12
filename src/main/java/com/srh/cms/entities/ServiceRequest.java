package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the servicerequest database table.
 * 
 */
@Entity
@NamedQuery(name="ServiceRequest.findAll", query="SELECT s FROM ServiceRequest s")
public class ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int serviceRequestId;

	@Temporal(TemporalType.DATE)
	private Date creationDate;

	private Time creationTime;

	@Lob
	private byte[] fileUpload;

	private byte isFastTrack;

	private String issueDescription;

	private String status;
	
	private String updatedComment;

	//bi-directional many-to-one association to AssignmentJunction
	@OneToMany(mappedBy="servicerequest",cascade = CascadeType.PERSIST)
	private List<AssignmentJunction> assignmentjunctions;

	//bi-directional many-to-one association to Resolution
	@OneToMany(mappedBy="servicerequest",cascade = CascadeType.PERSIST)
	private List<Resolution> resolutions;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;

	//bi-directional many-to-one association to CustomerUser
	@ManyToOne
	@JoinColumn(name="customerId")
	private CustomerUser customeruser;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;

	public ServiceRequest() {
	}

	public int getServiceRequestId() {
		return this.serviceRequestId;
	}

	public void setServiceRequestId(int serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Time getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Time creationTime) {
		this.creationTime = creationTime;
	}

	public byte[] getFileUpload() {
		return this.fileUpload;
	}

	public void setFileUpload(byte[] fileUpload) {
		this.fileUpload = fileUpload;
	}

	public byte getIsFastTrack() {
		return this.isFastTrack;
	}

	public void setIsFastTrack(byte isFastTrack) {
		this.isFastTrack = isFastTrack;
	}

	public String getIssueDescription() {
		return this.issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
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
		assignmentjunction.setServicerequest(this);

		return assignmentjunction;
	}

	public AssignmentJunction removeAssignmentjunction(AssignmentJunction assignmentjunction) {
		getAssignmentjunctions().remove(assignmentjunction);
		assignmentjunction.setServicerequest(null);

		return assignmentjunction;
	}

	public List<Resolution> getResolutions() {
		return this.resolutions;
	}

	public void setResolutions(List<Resolution> resolutions) {
		this.resolutions = resolutions;
	}

	public Resolution addResolution(Resolution resolution) {
		getResolutions().add(resolution);
		resolution.setServicerequest(this);

		return resolution;
	}

	public Resolution removeResolution(Resolution resolution) {
		getResolutions().remove(resolution);
		resolution.setServicerequest(null);

		return resolution;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CustomerUser getCustomeruser() {
		return this.customeruser;
	}

	public void setCustomeruser(CustomerUser customeruser) {
		this.customeruser = customeruser;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getUpdatedComment() {
		return updatedComment;
	}

	public void setUpdatedComment(String updatedComment) {
		this.updatedComment = updatedComment;
	}
	
	

}