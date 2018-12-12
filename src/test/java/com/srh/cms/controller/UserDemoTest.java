package com.srh.cms.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import com.srh.cms.entities.Technician;

public class UserDemoTest {
	
	UserDemo ud = new UserDemo();
	Technician lead = new Technician();

	@Test
	public void testCustomerRegister() {
		assertEquals(0,ud.customerRegister());	
	}

	@Test
	public void testUserLogin() {
		assertNotNull(ud.userLogin(1));
	}

	@Test
	public void testSupplierRegister() {
		assertEquals(0,ud.supplierRegister());
	}

	@Test
	public void testTechnicianRegister() {
		assertEquals(0,ud.technicianRegister(lead));
	}
}
