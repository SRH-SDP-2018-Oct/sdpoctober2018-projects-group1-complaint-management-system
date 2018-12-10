package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the customeruser database table.
 * 
 */
@Entity
@NamedQuery(name="CustomerUser.findAll", query="SELECT c FROM CustomerUser c")
public class CustomerUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String custusername;

	private String email;

	private String firstName;

	private byte isActive;

	private byte isPremium;

	private String lastName;

	private String password;

	private String phoneNo;

	private int postalCode;

	//bi-directional many-to-one association to ServiceRequest
	@OneToMany(mappedBy="customeruser")
	private List<ServiceRequest> servicerequests;

	public CustomerUser() {
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCustusername() {
		return this.custusername;
	}

	public void setCustusername(String custusername) {
		this.custusername = custusername;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public byte getIsPremium() {
		return this.isPremium;
	}

	public void setIsPremium(byte isPremium) {
		this.isPremium = isPremium;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public int getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public List<ServiceRequest> getServicerequests() {
		return this.servicerequests;
	}

	public void setServicerequests(List<ServiceRequest> servicerequests) {
		this.servicerequests = servicerequests;
	}

	public ServiceRequest addServicerequest(ServiceRequest servicerequest) {
		getServicerequests().add(servicerequest);
		servicerequest.setCustomeruser(this);

		return servicerequest;
	}

	public ServiceRequest removeServicerequest(ServiceRequest servicerequest) {
		getServicerequests().remove(servicerequest);
		servicerequest.setCustomeruser(null);

		return servicerequest;
	}

}