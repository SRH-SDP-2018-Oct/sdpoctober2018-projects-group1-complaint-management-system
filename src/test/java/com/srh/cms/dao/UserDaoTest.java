package com.srh.cms.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserDaoTest {

/*	@Test
	public void testUserDao() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterCustomer() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testLoginCustomer() {
		UserDao ud = new UserDao();
		String username = "geethacp";
		String pass = "geetha";
		assertNotNull(ud.loginCustomer(username, pass));
	}

	/*@Test
	public void testRegisterSupplier() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testSupplierLogin() {
		UserDao ud = new UserDao();
		String username = "raj";
		String pass = "raj";
		assertNotNull(ud.supplierLogin(username, pass));	}

	@Test
	public void testTechnicianLogin() {
		UserDao ud = new UserDao();
		String username = "ajayk";
		String pass = "1234";
		assertNotNull(ud.technicianLogin(username, pass));
	}
/*
	@Test
	public void testRegisterTechnician() {
		fail("Not yet implemented");
	}
*/
}
