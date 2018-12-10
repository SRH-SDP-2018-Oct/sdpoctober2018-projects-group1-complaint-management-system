package com.srh.cms.controller;


import java.util.Scanner;


import com.srh.cms.dao.UserDao;
import com.srh.cms.entities.CustomerUser;
import com.srh.cms.entities.SupplierUser;
import com.srh.cms.entities.UserTypes;

public class UserDemo {

	RegexMatch regex=new RegexMatch();
	UserDao userDao=new UserDao();
	
	Scanner scanner=new Scanner(System.in);
	int userId;
	
	
	public void customerRegister() {
        CustomerUser customerUser=new CustomerUser();
        System.out.println("First Name");
        customerUser.setFirstName(scanner.nextLine());
        System.out.println("Last Name");
        customerUser.setLastName(scanner.nextLine());
        boolean value;
        System.out.println("Email");
        do {  
        String email=scanner.nextLine();
        value=regex.checkMail(email);
        if(value) {
        	customerUser.setEmail(email);
        }
        else {
        	System.out.println("Enter a valid email");
 
        }
        
      }while(!value);
        System.out.println("Username");
        customerUser.setCustusername(scanner.nextLine());
        System.out.println("Password");
        customerUser.setPassword(scanner.nextLine());
        System.out.println("Phone Number");
        customerUser.setPhoneNo(scanner.nextLine());
        System.out.println("House Number");
        customerUser.setAddressLine1(scanner.nextLine());
        System.out.println("Street name");
        customerUser.setAddressLine2(scanner.nextLine());
        System.out.println("City");
        customerUser.setCity(scanner.nextLine());
        System.out.println("Postal code");
        customerUser.setPostalCode(Integer.parseInt(scanner.nextLine()));
        customerUser.setIsActive((byte) 1);
		customerUser.setIsPremium((byte) 0);
        userDao.registerCustomer(customerUser);
        
	}
	
	
	public UserTypes userLogin(int userType) {
		System.out.println("Please Enter registered credentials");
		String username = getUserName();
		String pass = getPassword();
		switch (userType) {
		case 1: return userDao.loginCustomer(username, pass);
		case 2: return userDao.supplierLogin(username, pass); 
		case 3: return userDao.technicianLogin(username, pass);
	}
		return null;
	}
	
	
	public void supplierRegister() {
		// TODO Auto-generated method stub
		
		SupplierUser supplierUser=new SupplierUser();
        System.out.println("Company Name");
        supplierUser.setCompanyName(scanner.nextLine());
        System.out.println("Email");
        supplierUser.setEmail(scanner.nextLine());
        System.out.println("Phone Number");
        supplierUser.setPhoneNo(scanner.nextLine());
        supplierUser.setIsActive((byte) 1);
        System.out.println("UserName");
        supplierUser.setSuppusername(scanner.nextLine());
        System.out.println("Password");
        supplierUser.setPassword(scanner.nextLine());
        userDao.registerSupplier(supplierUser);
	}
	
	
private String getUserName() {
		
		System.out.println("Enter Your Username: ");
		String name = scanner.nextLine();
		return name;
	}
	
	private String getPassword() {
		
		System.out.println("Enter Your Password: ");
		String pass = scanner.nextLine();
		return pass;
	}


}
