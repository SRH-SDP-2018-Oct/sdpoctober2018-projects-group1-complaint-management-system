package com.srh.cms.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

import com.srh.cms.dao.DashboardDao;
import com.srh.cms.entities.AssignmentJunction;
import com.srh.cms.entities.Resolution;
import com.srh.cms.entities.ServiceRequest;
import com.srh.cms.entities.ServiceRequestStatus;
import com.srh.cms.entities.Technician;

public class ResolveTicketsDemo {

	Resolution res = new Resolution();
	DashboardDemo dd = new DashboardDemo();
	StatusDemo sd = new StatusDemo();
	DashboardDao dashboardDao=new DashboardDao();
	Scanner sc = new Scanner(System.in);

	public void resolveTickets(Technician technician) throws ClassNotFoundException, SQLException {

		int choice;
		ServiceRequest serviceRequest;

		try {
			displayTicketInformation(technician);
			System.out.println("\n\nSELECT TICKET ID TO RESOLVE: ");
			choice = Integer.parseInt(sc.nextLine());
			serviceRequest=issueSelector(choice, technician);
			DesignerDemo.separator();
			dashboardDao.setTicketSolution(serviceRequest);
			dd.getTechnicianDashboard(technician);
		} catch (Exception e) {
			System.out.println("\nAn error has occurred: " + e.getMessage());
		}
	}

	public void displayTicketInformation(Technician technician) throws ClassNotFoundException, SQLException {
		List<AssignmentJunction> assignments = technician.getAssignmentjunctions();

		for (AssignmentJunction as : assignments) {
			ServiceRequest serviceRequest = as.getServicerequest();
			if(!serviceRequest.getStatus().equals(ServiceRequestStatus.CLOSED.toString())) {
			System.out.println(serviceRequest.getServiceRequestId() +"   "+serviceRequest.getStatus()+"   " + serviceRequest.getIssueDescription()+"  ");
			for(Resolution resolution:serviceRequest.getResolutions().subList( 1, serviceRequest.getResolutions().size())){
				System.out.println(resolution.getSolution()+"   "+resolution.getFeedback());
			}
			
			}
			System.out.println();
		}

	}

	public ServiceRequest issueSelector(int choice, Technician technician) throws ClassNotFoundException, SQLException {
		List<AssignmentJunction> assignments = technician.getAssignmentjunctions();

		for (AssignmentJunction as : assignments) {
			ServiceRequest serviceRequest = as.getServicerequest();
			if (serviceRequest.getServiceRequestId() == choice) {

				System.out.println("\nRESOLVING - \t" + serviceRequest.getServiceRequestId() + "\t" + serviceRequest.getIssueDescription());
				return serviceRequest;
			
			}
		}
		
		return null;
	}

	/*public void setSolution(ServiceRequest serviceRequest) throws ClassNotFoundException, SQLException {

		String solution = null;
		System.out.println("ENTER YOUR SOLUTION: ");
		solution = sc.nextLine();
		
		serviceRequest.setStatus(ServiceRequestStatus.PROCESSING.toString());
		List<Resolution> resolutions = serviceRequest.getResolutions();
		for (Resolution resolution : resolutions) {
			resolution.setServicerequest(serviceRequest);
			resolution.setSolution(solution);
		}
		serviceRequest.setResolutions(resolutions);
		dashboardDao.setTicketSolution(serviceRequest);
		
	}*/
}
