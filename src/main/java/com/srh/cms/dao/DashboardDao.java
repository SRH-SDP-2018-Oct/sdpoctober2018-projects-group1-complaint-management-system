package com.srh.cms.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.srh.cms.entities.AssignmentJunction;
import com.srh.cms.entities.Brand;
import com.srh.cms.entities.Category;
import com.srh.cms.entities.CustomerUser;
import com.srh.cms.entities.Product;
import com.srh.cms.entities.Resolution;
import com.srh.cms.entities.ServiceRequest;
import com.srh.cms.entities.ServiceRequestStatus;
import com.srh.cms.entities.SupplierUser;
import com.srh.cms.entities.Technician;

public class DashboardDao {

	Scanner sc=new Scanner(System.in);
	EntityManagerFactory emFactory;
	EntityManager entityManager;

	public DashboardDao() {
		emFactory = Persistence.createEntityManagerFactory("ComplaintMngmtSystem");
		entityManager = emFactory.createEntityManager();

	}

	public Product getProduct(Brand brand, String model) {
		Query query = entityManager.createQuery("Select p from Product p where p.brand =:brandId AND p.model =:model ");
		query.setParameter("brandId", brand);
		query.setParameter("model", model);
		return (Product) query.getSingleResult();

	}

	public void createTicket(ServiceRequest serviceRequest) {
		try {

			entityManager.getTransaction().begin();
			entityManager.persist(serviceRequest);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			System.out.println("Ticket not created!"+e);
		}

	}

	public Brand getBrandId(String brandName) {
		Query query = entityManager.createQuery("Select b from Brand b WHERE b.brandName =:brandName");
		query.setParameter("brandName", brandName);
		return (Brand) query.getSingleResult();
	}

	public List<ServiceRequest> getAllOpenTickets() {
		Query query = entityManager.createQuery("Select s from ServiceRequest s where s.status =:status");
		query.setParameter("status", ServiceRequestStatus.OPEN.toString());
		return (List<ServiceRequest>) query.getResultList();
	}

	public List<ServiceRequest> getAllCutomerTickets(CustomerUser customer) {
		Query query = entityManager.createQuery("Select s from ServiceRequest s where s.customeruser =:customer");
		query.setParameter("customer", customer);
		return (List<ServiceRequest>) query.getResultList();
	}

	public List<ServiceRequest> getOpenCutomerTickets(CustomerUser customer) {
		Query query = entityManager.createQuery(
				"Select s from ServiceRequest s where s.customeruser =:customer and s.status !=:status");
		query.setParameter("customer", customer);
		query.setParameter("status", ServiceRequestStatus.CLOSED.toString());
		return (List<ServiceRequest>) query.getResultList();
	}



	public void closeTicket(int id) {
		entityManager.getTransaction().begin();
		ServiceRequest serviceRequest = entityManager.find(ServiceRequest.class, id);
		serviceRequest.setStatus(ServiceRequestStatus.CLOSED.toString());
		List<Resolution> resolutions = serviceRequest.getResolutions();
		for (Resolution resolution : resolutions) {
			resolution.setStatus(ServiceRequestStatus.CLOSED.toString());
			Date date=new Date();
			resolution.setFinishedTime(new Time(date.getTime()));
			resolution.setResolutiondate(date);
		}
		List<AssignmentJunction> junctions=serviceRequest.getAssignmentjunctions();
		for(AssignmentJunction as:junctions) {
			Technician technician=as.getTechnician();
			technician.setOpenTickets(technician.getOpenTickets()-1);
			technician.setTotalTickets(technician.getTotalTickets()+1);
		}
		entityManager.getTransaction().commit();
		System.out.println("Ticket closed");
		
	}

	public List<Category> getCategories() {

		Query query = entityManager.createNamedQuery("Category.findAll");
		return (List<Category>) query.getResultList();
	}

	public void registerProduct(Product product) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(product);
			entityManager.getTransaction().commit();
			System.out.println("Product Registered Successfully!");

		}

		catch (Exception e) {
			System.out.println("Product Registeration unsuccessful!" + e);
		}

	}

	public Brand checkBrand(String brandName) {

		Query query = entityManager.createQuery("Select b from Brand b where b.brandName =:brandName");
		query.setParameter("brandName", brandName);
		List<Brand> list = (List<Brand>) query.getResultList();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Product> getProductFromCategory(Category category) {
		Query query = entityManager.createQuery("Select p from Product p where p.category =:category and p.isActive =:active");
		query.setParameter("category", category);
		query.setParameter("active", (byte)1);
		List<Product> list = (List<Product>) query.getResultList();
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
		
	}

	public void unRegisterProduct(int productId) {
		entityManager.getTransaction().begin();
		Product product = entityManager.find(Product.class, productId);
		product.setIsActive((byte)0);
		entityManager.getTransaction().commit();
		System.out.println("Product Unregistered successfully");
		
		
	}

	public List<Product> getActiveSupplierProducts(SupplierUser supplier) {
		Query query = entityManager.createQuery("Select p from Product p where p.supplieruser =:supplier AND p.isActive =:active");
		query.setParameter("supplier", supplier);
		query.setParameter("active", (byte)1);
		List<Product> productList=(List<Product>) query.getResultList();
		if(productList.isEmpty()) {
		System.out.println("Sorry !No products registred yet!");	
		}
		return productList;
	}

	public void setTicketSolution(ServiceRequest sr) {
		try {
			
			System.out.println("ENTER YOUR SOLUTION: ");
			String solution = sc.nextLine();
			entityManager.getTransaction().begin();
			ServiceRequest serviceRequest = entityManager.find(ServiceRequest.class, sr.getServiceRequestId());
			serviceRequest.setStatus(ServiceRequestStatus.PROCESSING.toString());
			List<Resolution> resolutions = new ArrayList<Resolution>();
			Resolution resolution=new Resolution();
			resolution.setSolution(solution);
			resolution.setStatus(ServiceRequestStatus.OPEN.toString());
			resolution.setServicerequest(serviceRequest);
			resolutions.add(resolution);
			serviceRequest.setResolutions(resolutions);
			entityManager.getTransaction().commit();
			System.out.println("Ticket solution provided!");

		}

		catch (Exception e) {
			System.out.println("\n\n--- Ticket solution not updated ---\n\n");
		}
		
	}

	public void setFeedback(int resolutionId, String response) {
		entityManager.getTransaction().begin();
		Resolution resolution = entityManager.find(Resolution.class, resolutionId);
		resolution.setFeedback(response);
		entityManager.getTransaction().commit();
		System.out.println("\n\n--- Reply posted successfully! ---\n\n");
		
	}
	
	
	

}
