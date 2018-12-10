package com.srh.cms.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int productId;

	@Column(name="detail_specification")
	private String detailSpecification;

	private byte isActive;

	private String model;

	private String productName;

	//bi-directional many-to-one association to Brand
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="brandId")
	private Brand brand;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;

	//bi-directional many-to-one association to SupplierUser
	@ManyToOne
	@JoinColumn(name="supplierId")
	private SupplierUser supplieruser;

	//bi-directional many-to-one association to ServiceRequest
	@OneToMany(mappedBy="product")
	private List<ServiceRequest> servicerequests;

	public Product() {
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDetailSpecification() {
		return this.detailSpecification;
	}

	public void setDetailSpecification(String detailSpecification) {
		this.detailSpecification = detailSpecification;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SupplierUser getSupplieruser() {
		return this.supplieruser;
	}

	public void setSupplieruser(SupplierUser supplieruser) {
		this.supplieruser = supplieruser;
	}

	public List<ServiceRequest> getServicerequests() {
		return this.servicerequests;
	}

	public void setServicerequests(List<ServiceRequest> servicerequests) {
		this.servicerequests = servicerequests;
	}

	public ServiceRequest addServicerequest(ServiceRequest servicerequest) {
		getServicerequests().add(servicerequest);
		servicerequest.setProduct(this);

		return servicerequest;
	}

	public ServiceRequest removeServicerequest(ServiceRequest servicerequest) {
		getServicerequests().remove(servicerequest);
		servicerequest.setProduct(null);

		return servicerequest;
	}

}