package com.srh.cms.entities;

public class UserTypes {
	
	private CustomerUser customerUser;
	private SupplierUser supplieruser;
	private Technician technician;
	public CustomerUser getCustomerUser() {
		return customerUser;
	}
	public void setCustomerUser(CustomerUser customerUser) {
		this.customerUser = customerUser;
	}
	public SupplierUser getSupplieruser() {
		return supplieruser;
	}
	public void setSupplieruser(SupplierUser supplieruser) {
		this.supplieruser = supplieruser;
	}
	public Technician getTechnician() {
		return technician;
	}
	public void setTechnician(Technician technician) {
		this.technician = technician;
	}
	
	

}
