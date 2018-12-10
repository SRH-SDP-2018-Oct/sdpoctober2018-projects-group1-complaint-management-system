package com.srh.cms.controller;

import java.sql.SQLException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.srh.cms.entities.UserTypes;

public class ComplaintProcessor {
	
	DesignerDemo desg = new DesignerDemo();
	Scanner sc = new Scanner(System.in);
	UserDemo userDemo = new UserDemo();
	DashboardDemo dashboardDemo = new DashboardDemo();
	UserTypes userTypes;

	public void run() throws ClassNotFoundException, SQLException {
		int user = getUserInput();
		int input = getAccessOption();
		redirectToLoginOrRegister(user, input);
		if (userTypes != null) {
			goToDashboard(user);

		}
	    this.run();
		
	}

	private void goToDashboard(int user) throws ClassNotFoundException, SQLException {

		System.out.println("Enter the right option:");
		switch (user) {
		case 1:
			dashboardDemo.getCustomerDashboard(userTypes.getCustomerUser());
			break;
		case 2:
			dashboardDemo.getSupplierDashboard(userTypes.getSupplieruser());
			break;
		case 3:
			dashboardDemo.getTechnicianDashboard(userTypes.getTechnician());
			break;

		}

	}

	private void redirectToLoginOrRegister(int userType, int input) throws ClassNotFoundException, SQLException {
		switch (input) {
		case 1:
			userRegister(userType);
			run();
			break;
		case 2:
			userTypes = userDemo.userLogin(userType);
			break;
		default:
			System.out.println("\nPlease Enter Correct Choice");
			run();
			break;

		}
	}

	private void userRegister(int userType) throws ClassNotFoundException, SQLException {
		switch (userType) {
		case 1:
			userDemo.customerRegister();
			break;
		case 2:userDemo.supplierRegister();
		break;
		default:
			System.out.println("\nPlease Enter Correct Choice");
			run();
			break;

		}

	}

	private int getAccessOption() {
		desg.separator();
		System.out.println("1.Register\t2.Login");
		return Integer.parseInt(sc.nextLine());
	}

	private int getUserInput() {
		desg.separator();
		System.out.println("Select the right option:");
		System.out.println("\nSelect the User Type:\n1. Customer\n2. Supplier\n3. Technician\nEnter Your Choice: ");
		return Integer.parseInt(sc.nextLine());

	}

}
