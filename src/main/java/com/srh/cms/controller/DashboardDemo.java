package com.srh.cms.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.srh.cms.dao.DashboardDao;
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

public class DashboardDemo {

	DesignerDemo desg = new DesignerDemo();
	DashboardDao dashboardDao = new DashboardDao();
	Scanner scanner = new Scanner(System.in);
	int input = 0;

	public void getCustomerDashboard(CustomerUser customer) {
		desg.separator();
		System.out.println("1.Create Ticket\t2.Update Ticket\t3.Close Ticket\t4.Show my Ticket 5.Logout");
		input = Integer.parseInt(scanner.nextLine());
		switch (input) {
		case 1:
			createTicket(customer);
			assignTicket();
			getCustomerDashboard(customer);
			break;
		case 2:
			updateTicketComment(customer);
			getCustomerDashboard(customer);
			break;
		case 3:
			closeTicket(customer);
			assignTicket();
			getCustomerDashboard(customer);
			break;
		case 4:
			List<ServiceRequest> list=displayAllCustomerTickets(customer);
			replyToTechnician(customer,list);
			getCustomerDashboard(customer);
			break;

		case 5:
			break;
		default:
			System.out.println("Incorrect Option! Please select a valid number");
			getCustomerDashboard(customer);

		}

	}
	
	
	
	private void replyToTechnician(CustomerUser customer,List<ServiceRequest> list) {
		
		
		
		
	}



	private void createTicket(CustomerUser customer) {
		try {
		ServiceRequest serviceRequest = new ServiceRequest();
		Category category=getCategory();
		List<Product> list=dashboardDao.getProductFromCategory(category);
		int i = 1;
		for (Product c : list) {
			System.out.println(i + " " + c.getProductName()+" "+c.getBrand().getBrandName()+" "+c.getModel()+" "+c.getDetailSpecification());
			i++;
		}
		System.out.println("Enter product number");
		int choice = Integer.parseInt(scanner.nextLine());
		serviceRequest.setProduct(list.get(choice - 1));
		serviceRequest.setCategory(category);
		System.out.println("Issue with the product");
		serviceRequest.setIssueDescription(scanner.nextLine());
		serviceRequest.setCustomeruser(customer);
		Date currentDate = new Date();
		serviceRequest.setCreationDate(currentDate);
		serviceRequest.setCreationTime(new Time(currentDate.getTime()));
		serviceRequest.setIsFastTrack((byte) 0);
		serviceRequest.setStatus(ServiceRequestStatus.OPEN.toString());
		dashboardDao.createTicket(serviceRequest);
		System.out.println("Ticket creation successful!");
		}
		catch (NullPointerException e) {
			System.out.println("Sorry!Products do not exist in this category!");
		}
		catch (Exception e) {
			System.out.println("Ticket not created!");
		}

		
	}
	

	private Category getCategory() {
		System.out.println("Enter the category of the product");
		List<Category> categoryList = dashboardDao.getCategories();
		int i = 1;
		for (Category c : categoryList) {
			System.out.println(i + " " + c.getCategoryType());
			i++;
		}
		int choice = Integer.parseInt(scanner.nextLine());
		return categoryList.get(choice - 1);
		
	}

	
	
	
	private void assignTicket() {
		List<ServiceRequest> serviceRequestList = dashboardDao.getAllOpenTickets();
		for (ServiceRequest serviceRequest : serviceRequestList) {
			List<Technician> list = serviceRequest.getCategory().getTechnicians();
			Map<Technician,Integer> map=new HashMap<Technician,Integer>();
			for (Technician t : list) {
				map.put(t, t.getOpenTickets());
			}
				
				
			Entry<Technician,Integer> min = null;
			for (Entry<Technician,Integer> entry : map.entrySet()) {
			    if (min == null || min.getValue() > entry.getValue()) {
			        min = entry;
			    }
			}	
			
			Technician t=min.getKey();
			int openTicketCount=min.getValue();
				if (openTicketCount < 10) {
					System.out.println("setting ticket");
					List<Resolution> resolutions = new ArrayList<Resolution>();
					Resolution resolution = new Resolution();
					resolution.setFinishedTime(new Time(new Date(0).getTime()));// set to 00:00:00
					resolution.setServicerequest(serviceRequest);
					resolution.setStatus(ServiceRequestStatus.OPEN.toString());
					resolutions.add(resolution);
					
					List<AssignmentJunction> assignments = new ArrayList<AssignmentJunction>();
					AssignmentJunction junction = new AssignmentJunction();
					junction.setServicerequest(serviceRequest);
					t.setOpenTickets(t.getOpenTickets()+1);
					junction.setTechnician(t);
					junction.setResolution(resolution);
					assignments.add(junction);
					serviceRequest.setAssignmentjunctions(assignments);
					
					//resolution.setAssignmentjunctions(assignments);
					serviceRequest.setResolutions(resolutions);
					serviceRequest.setStatus(ServiceRequestStatus.ASSIGNED.toString());
					dashboardDao.createTicket(serviceRequest);
				}
			
			
		}

	}

	
	private void updateTicketComment(CustomerUser customer) {

		List<ServiceRequest> list=displayOpenCustomerTickets(customer);
		System.out.println("Enter the ticket number to update comment");
		int choice=Integer.parseInt(scanner.nextLine());
		if(!list.isEmpty()) {
			ServiceRequest sr=list.get(choice-1);
			int resolutionId=sr.getResolutions().get(sr.getResolutions().size() - 1).getResolutionId();
			System.out.println("Enter response to solution");
			String response=scanner.nextLine();
			dashboardDao.setFeedback(resolutionId,response);
			
		}
	
		
	}
	
	
	
	private void closeTicket(CustomerUser customer) {
		List<ServiceRequest> serviceRequests=displayCustomerTicketsToClose(customer);
		System.out.println("Enter the ticket number to close ticket");
		int choice = Integer.parseInt(scanner.nextLine());
		dashboardDao.closeTicket(serviceRequests.get(choice-1).getServiceRequestId());

	}

	
	private List<ServiceRequest> displayCustomerTicketsToClose(CustomerUser customer) {
		List<ServiceRequest> list = dashboardDao.getOpenCutomerTickets(customer);
		int i=1;
		for (ServiceRequest sr : list) { 
				System.out.println(i + ". " +sr.getProduct().getProductName()+" "+ sr.getIssueDescription() + " " + sr.getStatus());
			i++;
		}
		return list;
	}



	private List<ServiceRequest> displayOpenCustomerTickets(CustomerUser customer) {
		List<ServiceRequest> list = dashboardDao.getOpenCutomerTickets(customer);

		int i=1;
		for (ServiceRequest sr : list) {
				System.out.println(i + ". " + sr.getIssueDescription() + "  -  "+sr.getStatus());
				for(Resolution resolution:sr.getResolutions().subList( 1, sr.getResolutions().size())){
					System.out.println(resolution.getSolution()+"   "+resolution.getFeedback());
				}
			
			i++;

		}
		return list;
	}

	
	
	
	private List<ServiceRequest> displayAllCustomerTickets(CustomerUser customer) {
		List<ServiceRequest> list = dashboardDao.getAllCutomerTickets(customer);

		int i=1;
		for (ServiceRequest sr : list) { 
				System.out.println(i + ". " +sr.getProduct().getProductName()+" "+ sr.getIssueDescription() + " " + sr.getStatus());
			i++;
		}
		
		
		return list;
	}

	

	
	
	public void getSupplierDashboard(SupplierUser supplier) throws ClassNotFoundException, SQLException {

		System.out.println(
				"\nWelcome to Supplier Dashboard\n1. Register Product \n2. De-Register Product \n3. View My Products\n4. Logout \nEnter Your Choice:");

		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();

		switch (choice) {

		case 1:
			productRegister(supplier);
			break;
		case 2:
			unRegisteProduct(supplier);
			break;
		case 3:
			viewProduct(supplier);
			break;
		case 4:
			logout();
			break;
		default:
			System.out.println("Please select a correct option to proceed ");
			break;

		}
		getSupplierDashboard(supplier);
		

	}

	public void productRegister(SupplierUser supplier) {
		Product product = new Product();

		System.out.println("Enter Product Name");
		product.setProductName(scanner.nextLine());
		System.out.println("Enter Product Model Number");
		product.setModel(scanner.nextLine());
		System.out.println("Enter brand Name of the product");
		String brandNew = scanner.nextLine();
		Brand existingBrand = dashboardDao.checkBrand(brandNew);
		if (existingBrand != null) {
			product.setBrand(existingBrand);
		} else if(!brandNew.equals("")) {
			Brand brand = new Brand();
			brand.setBrandName(brandNew);
			product.setBrand(brand);
		}
		product.setCategory(getCategory());
		System.out.println("Please provide product description");
		product.setDetailSpecification(scanner.nextLine());
		product.setIsActive((byte) 1);
		product.setSupplieruser(supplier);
		dashboardDao.registerProduct(product);
	}

	public void unRegisteProduct(SupplierUser supplier) {
		List<Product> productList=viewProduct(supplier);
		System.out.println("Enter the number of product to delete");
		int choice = Integer.parseInt(scanner.nextLine());
		dashboardDao.unRegisterProduct(productList.get(choice-1).getProductId());
		

	}

	public List<Product> viewProduct(SupplierUser supplier) {
		List<Product> productList=dashboardDao.getActiveSupplierProducts(supplier);
		int i=1;
		for(Product p:productList) {
			System.out.println(i+". "+p.getProductName()+" "+p.getBrand().getBrandName()+" "+p.getModel()+" "+p.getDetailSpecification());
			i++;
		}

		return productList;
	}

	
	
	
	
	

	public void getTechnicianDashboard(Technician technician) throws ClassNotFoundException, SQLException {
		
		
		ResolveTicketsDemo rtd = new ResolveTicketsDemo();

		System.out.println("\nWelcome to Technician Dashboard \n1. View Tickets \n2. ResolveTickets\n3. Logout \n4. Exit\nEnter Your Choice: ");
		int input = Integer.parseInt(scanner.nextLine());

		switch (input) {

		case 1:
			viewTickets(technician);
			break;
			
		case 2:rtd.resolveTickets(technician);
		break;
		case 3:
			logout();
			break;
			
		case 4:
			exit();
		default:
			System.out.println("Incorrect Option! Select a proper option: ");
			break;

		}
		
		getTechnicianDashboard(technician);

	}

	private void viewTickets(Technician technician) throws SQLException, ClassNotFoundException {

		List<AssignmentJunction> assignments = technician.getAssignmentjunctions();
		
		
		
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
	    System.out.printf("%10s %10s %20s %20s %20s,%30s,%10s", "Ticket Id","Category","Product","Customer Id","Creation Time"," Issue Description",  "Status");
	    System.out.println();
	    System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
	  

		for (AssignmentJunction as : assignments) {
			ServiceRequest serviceRequest = as.getServicerequest();
			System.out.format("%10s %10s %20s %30s %20s %30s %10s",serviceRequest.getServiceRequestId(),
					 serviceRequest.getCategory().getCategoryType(),
					 serviceRequest.getProduct().getProductName(),
					 serviceRequest.getCustomeruser().getCustomerId(),
					 serviceRequest.getCreationDate(),
					 serviceRequest.getIssueDescription(),
					 serviceRequest.getStatus());
			 System.out.println();
			    System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		}
		
		getTechnicianDashboard(technician);

	}
	

	private void logout() throws ClassNotFoundException, SQLException {
		ComplaintProcessor complaintProcessor = new ComplaintProcessor();
		complaintProcessor.run();
	}
	
	private void exit() {
		System.out.println("Exiting App! Thank You!");
		System.exit(0);
	}

	
}
