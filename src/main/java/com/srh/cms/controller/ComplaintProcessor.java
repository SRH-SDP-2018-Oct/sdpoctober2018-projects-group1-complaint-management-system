package com.srh.cms.controller;

import java.sql.SQLException;
import java.util.Scanner;

import com.srh.cms.entities.Technician;
import com.srh.cms.entities.UserTypes;


public class ComplaintProcessor {

	DesignerDemo desg = new DesignerDemo();
	Scanner sc = new Scanner(System.in);
	UserDemo userDemo = new UserDemo();
	DashboardDemo dashboardDemo = new DashboardDemo();
	Technician tech = new Technician();
	UserTypes userTypes;
	String defaultMessage = "\nPlease select a valid option!";

	
	public void run() throws ClassNotFoundException, SQLException {
		int user = getUserInput();
		int input = getAccessOption(user);
		redirectToLoginOrRegister(user, input);
		if (userTypes != null) {
			goToDashboard(user);
		}
		this.run();
	}
	

	private int getUserInput() {
		DesignerDemo.separator();
		System.out.println("\nSelect the right option:");
		System.out.println("\nSelect the User Type:\n[1]  Customer\n[2]  Supplier\n[3]  Technician\nEnter Your Choice: ");
		return Integer.parseInt(sc.nextLine());
	}
	
	
	private int getAccessOption(int user) throws ClassNotFoundException, SQLException {
		try {
			DesignerDemo.separator();
			switch (user) {
			case 1:  System.out.println("\n[1]  Login \n[2]  Register ");
					 return Integer.parseInt(sc.nextLine());

			case 2:  System.out.println("\n[1]  Login \n[2]  Register ");
					 return Integer.parseInt(sc.nextLine());

			case 3:  System.out.println("\n[1]  Login: ");
					 int choice = Integer.parseInt(sc.nextLine());
					 if (choice == 1) {
						 return choice;
					 } else {
						 return 0;
					 }
			default: System.out.println(defaultMessage);
					 getAccessOption(user);
					 return 0;
			}
		} catch (Exception e) {
			System.out.println("\n++++ AN ERROR HAS OCCURRED! ++++ \n" + e.getMessage());
			run();
			return 0;
		}
	}

	
	private void goToDashboard(int user) throws ClassNotFoundException, SQLException {
		System.out.println("Enter the right option:");
		switch (user) {
			case 1:	 dashboardDemo.getCustomerDashboard(userTypes.getCustomerUser());
					 break;
			case 2:	 dashboardDemo.getSupplierDashboard(userTypes.getSupplieruser());
					 break;
			case 3:  if(userTypes.getTechnician().getJobrole().equalsIgnoreCase("lead")) {
		 				dashboardDemo.getLeadTechnicianDashboard(userTypes.getTechnician());
			 		 } else {
				 		dashboardDemo.getTechnicianDashboard(userTypes.getTechnician());
					 }
					 break;
			default: System.out.println(defaultMessage);
					 goToDashboard(user);
					 break;
		}
	}
	

	private void redirectToLoginOrRegister(int userType, int input) throws ClassNotFoundException, SQLException {
		switch (input) {
		case 1:	 userTypes = userDemo.userLogin(userType);
 				 break;
		case 2:	 userRegister(userType);
				 run();
				 break;
		default: System.out.println("\nPlease Enter Correct Choice");
			 	 run();
			 	 break;
		}
	}

	
	private void userRegister(int userType) throws ClassNotFoundException, SQLException {
		switch (userType) {
		case 1:	 userDemo.customerRegister();
				 break;
		case 2:	 userDemo.supplierRegister();
				 break;
		default: System.out.println(defaultMessage);
				 run();
				 break;
		}
	}
}