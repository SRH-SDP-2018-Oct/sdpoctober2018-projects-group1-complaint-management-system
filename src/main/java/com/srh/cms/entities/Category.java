package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int categoryId;

	private String categoryDesc;

	private String categoryType;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="category")
	private List<Product> products;

	//bi-directional many-to-one association to ServiceRequest
	@OneToMany(mappedBy="category")
	private List<ServiceRequest> servicerequests;

	//bi-directional many-to-one association to Technician
	@OneToMany(mappedBy="category")
	private List<Technician> technicians;

	public Category() {
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategory(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);

		return product;
	}

	public List<ServiceRequest> getServicerequests() {
		return this.servicerequests;
	}

	public void setServicerequests(List<ServiceRequest> servicerequests) {
		this.servicerequests = servicerequests;
	}

	public ServiceRequest addServicerequest(ServiceRequest servicerequest) {
		getServicerequests().add(servicerequest);
		servicerequest.setCategory(this);

		return servicerequest;
	}

	public ServiceRequest removeServicerequest(ServiceRequest servicerequest) {
		getServicerequests().remove(servicerequest);
		servicerequest.setCategory(null);

		return servicerequest;
	}

	public List<Technician> getTechnicians() {
		return this.technicians;
	}

	public void setTechnicians(List<Technician> technicians) {
		this.technicians = technicians;
	}


}