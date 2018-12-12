package com.srh.cms.controller;


import java.util.Scanner;


import com.srh.cms.dao.UserDao;
import com.srh.cms.entities.CustomerUser;
import com.srh.cms.entities.SupplierUser;
import com.srh.cms.entities.Technician;
import com.srh.cms.entities.UserTypes;

public class UserDemo {

	RegexMatch regex=new RegexMatch();
	UserDao userDao=new UserDao();
	
	Scanner scanner=new Scanner(System.in);
	int userId;
	
	
	public int customerRegister() {
        CustomerUser customerUser=new CustomerUser();
        System.out.println("\n--> First Name : ");
        customerUser.setFirstName(scanner.nextLine());
        System.out.println("\n--> Last Name : ");
        customerUser.setLastName(scanner.nextLine());
        boolean value;
        System.out.println("\n--> Email : ");
        do {  
        	String email=scanner.nextLine();
        	value=regex.checkMail(email);
        	if(value) {
        		customerUser.setEmail(email);
        	}
        	else {
        		System.out.println("\n\n-x-x-x- Enter a valid email! -x-x-x-");
        	}
        }while(!value);
        System.out.println("\n--> Username : ");
        customerUser.setCustusername(scanner.nextLine());
        System.out.println("\n--> Password : ");
        customerUser.setPassword(scanner.nextLine());
        System.out.println("\n--> Phone Number : ");
        customerUser.setPhoneNo(scanner.nextLine());
        System.out.println("\n--> House Number : ");
        customerUser.setAddressLine1(scanner.nextLine());
        System.out.println("\n--> Street name : ");
        customerUser.setAddressLine2(scanner.nextLine());
        System.out.println("\n--> City : ");
        customerUser.setCity(scanner.nextLine());
        System.out.println("\n--> Postal code : ");
        customerUser.setPostalCode(Integer.parseInt(scanner.nextLine()));
        customerUser.setIsActive((byte) 1);
		customerUser.setIsPremium((byte) 0);
        userDao.registerCustomer(customerUser);
        return 0;
	}
	
	
	public UserTypes userLogin(int userType) {
		System.out.println("\n\nPlease Enter registered credentials : ");
		String username = getUserName();
		String pass = getPassword();
		switch (userType) {
		case 1: return userDao.loginCustomer(username, pass);
		case 2: return userDao.supplierLogin(username, pass); 
		case 3: return userDao.technicianLogin(username, pass);
	}
		return null;
	}
	
	private String getUserName() {
		
		System.out.println("\n--> Enter Your Username : ");
		String name = scanner.nextLine();
		return name;
	}
	
	private String getPassword() {
		
		System.out.println("\n--> Enter Your Password : ");
		String pass = scanner.nextLine();
		return pass;
	}
	
	
	public int supplierRegister() {
		SupplierUser supplierUser = new SupplierUser();
        System.out.println("\n--> Company Name : ");
        supplierUser.setCompanyName(scanner.nextLine());
        System.out.println("\n--> Email : ");
        supplierUser.setEmail(scanner.nextLine());
        System.out.println("\n--> Phone Number : ");
        supplierUser.setPhoneNo(scanner.nextLine());
        supplierUser.setIsActive((byte) 1);
        System.out.println("\n--> UserName : ");
        supplierUser.setSuppusername(scanner.nextLine());
        System.out.println("\n--> Password : ");
        supplierUser.setPassword(scanner.nextLine());
        userDao.registerSupplier(supplierUser);
        return 0;
	}
	

	public int technicianRegister(Technician lead) {
		Technician technician=new Technician();
		System.out.println("\n--> Enter name : ");
		technician.setTechnicalName(scanner.nextLine());
		System.out.println("\n--> Enter email : ");
		technician.setEmail(scanner.nextLine());
		System.out.println("\n--> Enter Phone Number : ");
		technician.setPhoneNo(scanner.nextLine());
		System.out.println("\n--> Enter Username : ");
		technician.setTechusername(scanner.nextLine());
		System.out.println("\n--> Enter Password : ");
		technician.setPassword(scanner.nextLine());
		technician.setJobrole("expert");
		technician.setIsActive((byte)1);
		technician.setCategory(lead.getCategory());
		userDao.registerTechnician(technician);
		return 0;
	}
}
