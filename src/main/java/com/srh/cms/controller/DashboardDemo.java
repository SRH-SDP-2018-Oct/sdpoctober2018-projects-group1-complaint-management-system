package com.srh.cms.controller;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
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

	DashboardDao dashboardDao = new DashboardDao();
	UserDemo userDemo = new UserDemo();
	ReportGenerator reportGen = new ReportGenerator();
	//ResolveTicketsDemo rtd = new ResolveTicketsDemo();
	Scanner scanner = new Scanner(System.in);

	int input = 0;
	String defaultMessage = "\nPlease select a valid option!";

	public void getCustomerDashboard(CustomerUser customer) throws ClassNotFoundException, SQLException {
		DesignerDemo.separator();
		System.out.println(
				"[1]  Create Ticket\n[2]  Update Ticket\n[3]  Close Ticket\n[4]  Show my Ticket \n[5]  Logout \n[6]  Exit \nEnter your choice : ");
		input = Integer.parseInt(scanner.nextLine());
		switch (input) {
		case 1:	createTicket(customer);
				assignTicket();
				getCustomerDashboard(customer);
				break;
		case 2:	updateTicketComment(customer);
				getCustomerDashboard(customer);
				break;
		case 3:	closeTicket(customer);
				assignTicket();
				getCustomerDashboard(customer);
				break;
		case 4:	displayAllCustomerTickets(customer);
				getCustomerDashboard(customer);
				break;
		case 5:	logout();
				break;
		case 6: exit();
		default:System.out.println(defaultMessage);
				getCustomerDashboard(customer);
				break;
		}
	}

	private void createTicket(CustomerUser customer) {
		try {
			ServiceRequest serviceRequest = new ServiceRequest();
			Category category = getCategory();
			List<Product> list = dashboardDao.getProductFromCategory(category);
			int i = 1;
			for (Product c : list) {
				System.out.println(i + " " + c.getProductName() + " " + c.getBrand().getBrandName() + " " + c.getModel()
						+ " " + c.getDetailSpecification());
				i++;
			}
			System.out.println("\n--> Enter product number : ");
			int choice = Integer.parseInt(scanner.nextLine());
			serviceRequest.setProduct(list.get(choice - 1));
			serviceRequest.setCategory(category);
			System.out.println("\n--> Issue with the product : ");
			serviceRequest.setIssueDescription(scanner.nextLine());
			serviceRequest.setCustomeruser(customer);
			Date currentDate = new Date();
			serviceRequest.setCreationDate(currentDate);
			serviceRequest.setCreationTime(new Time(currentDate.getTime()));
			serviceRequest.setIsFastTrack((byte) 0);
			serviceRequest.setStatus(ServiceRequestStatus.OPEN.toString());
			dashboardDao.createTicket(serviceRequest);
			System.out.println("\n\n--- Ticket creation successful! ---");
		} catch (NullPointerException e) {
			System.out.println("\n\n-x-x-x- Sorry!Products do not exist in this category! -x-x-x-\n\n");
		} catch (Exception e) {
			System.out.println("\n\n-x-x-x- Ticket not created! -x-x-x-\n\n");
			createTicket(customer);
		}

	}

	private Category getCategory() {
		System.out.println("\n--> Enter the category of the product : ");
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
			Map<Technician, Integer> map = new HashMap<Technician, Integer>();
			for (Technician t : list) {
				map.put(t, t.getOpenTickets());
			}
			Entry<Technician, Integer> min = null;
			for (Entry<Technician, Integer> entry : map.entrySet()) {
				if (min == null || min.getValue() > entry.getValue()) {
					min = entry;
				}
			}
			Technician t = min.getKey();
			int openTicketCount = min.getValue();
			if (openTicketCount < 10) {
				List<Resolution> resolutions = new ArrayList<Resolution>();
				Resolution resolution = new Resolution();
				resolution.setFinishedTime(new Time(new Date(0).getTime()));
				resolution.setServicerequest(serviceRequest);
				resolution.setStatus(ServiceRequestStatus.OPEN.toString());
				resolutions.add(resolution);
				List<AssignmentJunction> assignments = new ArrayList<AssignmentJunction>();
				AssignmentJunction junction = new AssignmentJunction();
				junction.setServicerequest(serviceRequest);
				t.setOpenTickets(t.getOpenTickets() + 1);
				junction.setTechnician(t);
				junction.setResolution(resolution);
				assignments.add(junction);
				serviceRequest.setAssignmentjunctions(assignments);
				serviceRequest.setResolutions(resolutions);
				serviceRequest.setStatus(ServiceRequestStatus.ASSIGNED.toString());
				dashboardDao.createTicket(serviceRequest);
			}
		}
	}

	private void updateTicketComment(CustomerUser customer) throws ClassNotFoundException, SQLException {
		List<ServiceRequest> list = displayOpenCustomerTickets(customer);
		if (!list.isEmpty()) {
			System.out.println("\n\n[1]  Update Ticket \n[2]  Go Back \nEnter your choice : ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
			case 1:
				System.out.println("\n--> Enter the ticket number to update comment : ");
				choice = Integer.parseInt(scanner.nextLine());
				if (!list.isEmpty()) {
					ServiceRequest sr = list.get(choice - 1);
					int resolutionId = sr.getResolutions().get(sr.getResolutions().size() - 1).getResolutionId();
					System.out.println("\n--> Enter response to solution : ");
					String response = scanner.nextLine();
					dashboardDao.setFeedback(resolutionId, response);
				}
				break;
			case 2:
				getCustomerDashboard(customer);
				break;
			default:
				System.out.println(defaultMessage);
				break;
			}
		} else {
			System.out.println("\n-x-x-x- No open tickets! -x-x-x-");
		}
	}

	private void closeTicket(CustomerUser customer) throws ClassNotFoundException, SQLException {
		List<ServiceRequest> serviceRequests = displayCustomerTicketsToClose(customer);
		if (!serviceRequests.isEmpty()) {
			System.out.println("\n\n[1]  Close Ticket \n[2]  Go Back \nEnter your choice : ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
			case 1:
				System.out.println("Enter the ticket number to close ticket");
				choice = Integer.parseInt(scanner.nextLine());
				dashboardDao.closeTicket(serviceRequests.get(choice - 1).getServiceRequestId());
				break;
			case 2:
				getCustomerDashboard(customer);
				break;
			default:
				System.out.println(defaultMessage);
				break;
			}
		} else {
			System.out.println("\n-x-x-x- No open tickets! -x-x-x-");
		}
	}

	private List<ServiceRequest> displayCustomerTicketsToClose(CustomerUser customer) {
		List<ServiceRequest> list = dashboardDao.getOpenCutomerTickets(customer);
		int i = 1;
		for (ServiceRequest sr : list) {
			System.out.println(i + ".  " + sr.getProduct().getProductName() + "  " + sr.getIssueDescription() + "  "
					+ sr.getStatus());
			i++;
		}
		return list;
	}

	private List<ServiceRequest> displayOpenCustomerTickets(CustomerUser customer) {
		DashboardDao dao=new DashboardDao();
		List<ServiceRequest> list = dao.getOpenCutomerTickets(customer);

		System.out.println("-----------------------------------------------------------------------");
		System.out.println(" Issue Description\tStatus\tTechnician Solution\t Customer Reply ");
		System.out.println("-----------------------------------------------------------------------");
		int i = 1;
		for (ServiceRequest sr : list) {
			System.out.print("\n\n" + i + ". " + sr.getIssueDescription() + "    " + sr.getStatus() + "   ");
			for (Resolution resolution : sr.getResolutions().subList(1, sr.getResolutions().size())) {
				System.out.println(resolution.getSolution() + "\t\t" + resolution.getFeedback());
			}
			i++;
		}
		return list;
	}

	private List<ServiceRequest> displayAllCustomerTickets(CustomerUser customer) {
		List<ServiceRequest> list = dashboardDao.getAllCutomerTickets(customer);
		int i = 1;

		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("SR No  Product name  Issue Description  Status");
		System.out.println("--------------------------------------------------------------------------------------");
		for (ServiceRequest sr : list) {
			System.out.println(i + ".    " + sr.getProduct().getProductName() + "      " + sr.getIssueDescription()
					+ "      " + sr.getStatus());
			i++;
		}
		return list;
	}

	public void getSupplierDashboard(SupplierUser supplier) throws ClassNotFoundException, SQLException {
		System.out.println(
				"\n<--- WELCOME TO SUPPLIER DASHBOARD ---> \n[1]  Register Product \n[2]  De-Register Product \n[3]  View My Products\n[4]  Logout \nEnter Your Choice : ");
		int choice = Integer.parseInt(scanner.nextLine());
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
			System.out.println(defaultMessage);
			break;
		}
		getSupplierDashboard(supplier);
	}

	public void productRegister(SupplierUser supplier) {
		Product product = new Product();
		System.out.println("\n--> Enter Product Name : ");
		product.setProductName(scanner.nextLine());
		System.out.println("\n--> Enter Product Model Number : ");
		product.setModel(scanner.nextLine());
		System.out.println("\n--> Enter brand Name of the product : ");
		String brandNew = scanner.nextLine();
		Brand existingBrand = dashboardDao.checkBrand(brandNew);
		if (existingBrand != null) {
			product.setBrand(existingBrand);
		} else if (!brandNew.equals("")) {
			Brand brand = new Brand();
			brand.setBrandName(brandNew);
			product.setBrand(brand);
		}
		product.setCategory(getCategory());
		System.out.println("\n--> Please provide product description : ");
		product.setDetailSpecification(scanner.nextLine());
		product.setIsActive((byte) 1);
		product.setSupplieruser(supplier);
		dashboardDao.registerProduct(product);
	}

	public void unRegisteProduct(SupplierUser supplier) {
		List<Product> productList = viewProduct(supplier);
		System.out.println("\n--> Enter the number of product to delete : ");
		int choice = Integer.parseInt(scanner.nextLine());
		dashboardDao.unRegisterProduct(productList.get(choice - 1).getProductId());

	}

	public List<Product> viewProduct(SupplierUser supplier) {
		List<Product> productList = dashboardDao.getActiveSupplierProducts(supplier);
		int i = 1;
		for (Product p : productList) {
			System.out.println(i + ". " + p.getProductName() + " " + p.getBrand().getBrandName() + " " + p.getModel()
					+ " " + p.getDetailSpecification());
			i++;
		}
		return productList;
	}

	public void getTechnicianDashboard(Technician technician) throws ClassNotFoundException, SQLException {
		System.out.println(
				"\n\n<--- WELCOME TO TECHNICIAN DASHBOARD ---> \n\n[1]  View Tickets \n[2]  ResolveTickets\n[3]  Logout \n[4]  Exit \n\nEnter Your Choice : ");
		int input = Integer.parseInt(scanner.nextLine());
		switch (input) {
		case 1:
			viewTickets(technician);
			break;
		case 2:
			resolveTickets(technician);
			break;
		case 3:
			logout();
			break;
		case 4:
			exit();
		default:
			System.out.println(defaultMessage);
		}
		getTechnicianDashboard(technician);
	}

	public void getLeadTechnicianDashboard(Technician technician) throws ClassNotFoundException, SQLException   {
		try {
			System.out.println(
					"\n\n<--- WELCOME TO LEAD TECHNICIAN DASHBAORD ---> \n[1]  View Tickets \n[2]  ResolveTickets\n[3]  Register Technician\n[4]  Generate Reports\n[5]  Logout\n[6]  Exit\nEnter Your Choice : ");
			int input = Integer.parseInt(scanner.nextLine());
			switch (input) {
			case 1:
				viewTickets(technician);
				break;
			case 2:
				resolveTickets(technician);
				break;
			case 3:
				userDemo.technicianRegister(technician);
				break;
			case 4:
				reportGen.getReports();
				break;
			case 5:
				logout();
				break;
			case 7:
				exit();
			default:
				System.out.println(defaultMessage);
			}
			getLeadTechnicianDashboard(technician);
			
		} catch (ParseException e) {
			System.out.println("\n-x-x-x- An error has occurred -x-x-x-\n");		
		}
	}


	private void viewTickets(Technician technician)  {
		List<AssignmentJunction> assignments = technician.getAssignmentjunctions();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %10s %20s %20s %20s,%30s,%10s", "Ticket Id", "Category", "Product", "Customer Id",
				"Creation Time", " Issue Description", "Status");
		System.out.println();
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------");
		for (AssignmentJunction as : assignments) {
			ServiceRequest serviceRequest = as.getServicerequest();
			System.out.format("%10s %10s %20s %30s %20s %30s %10s", serviceRequest.getServiceRequestId(),
					serviceRequest.getCategory().getCategoryType(), serviceRequest.getProduct().getProductName(),
					serviceRequest.getCustomeruser().getCustomerId(), serviceRequest.getCreationDate(),
					serviceRequest.getIssueDescription(), serviceRequest.getStatus());
			System.out.println();
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public void resolveTickets(Technician technician)  {

		int choice;
		ServiceRequest serviceRequest;
		try {
			displayTicketInformation(technician);
			System.out.println("\n\n[1]  Resolve Ticket  [2]  Go Back");
			choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:  System.out.println("\n\n--> Select Ticket ID to Resolve : ");
						 choice = Integer.parseInt(scanner.nextLine());
						 serviceRequest = issueSelector(choice, technician);
						 DesignerDemo.separator();
						 dashboardDao.setTicketSolution(serviceRequest);
						 break;
				case 2:  break;
				default: System.out.println(defaultMessage);
			}
			
		} catch (Exception e) {
			System.out.println("\n-x-x-x- An error has occurred -x-x-x-\n");
		}
	}
	
	public void displayTicketInformation(Technician technician) {
		List<AssignmentJunction> assignments = technician.getAssignmentjunctions();
		for (AssignmentJunction as : assignments) {
			ServiceRequest serviceRequest = as.getServicerequest();
			if (!serviceRequest.getStatus().equals(ServiceRequestStatus.CLOSED.toString())) {
				System.out.print(serviceRequest.getServiceRequestId() + "   " + serviceRequest.getStatus() + "   " + serviceRequest.getIssueDescription() + "  ");
				for (Resolution resolution : serviceRequest.getResolutions().subList(1,	serviceRequest.getResolutions().size())) {
					System.out.println(resolution.getSolution() + "   " + resolution.getFeedback());
				}
			}
			System.out.println();
		}
	}
	

	public ServiceRequest issueSelector(int choice, Technician technician){
		List<AssignmentJunction> assignments = technician.getAssignmentjunctions();
		for (AssignmentJunction as : assignments) {
			ServiceRequest serviceRequest = as.getServicerequest();
			if (serviceRequest.getServiceRequestId() == choice) {
				System.out.println("\nResolving - \t" + serviceRequest.getServiceRequestId() + "\t"	+ serviceRequest.getIssueDescription());
				return serviceRequest;
			}
		}
		return null;
	}

	private void logout() throws ClassNotFoundException, SQLException {
		ComplaintProcessor complaintProcessor = new ComplaintProcessor();
		complaintProcessor.run();
	}

	private void exit() {
		DesignerDemo.separator();
		System.out.println("\n\nExiting App! \nThank You!");
		DesignerDemo.separator();
		System.exit(0);
	}

}
