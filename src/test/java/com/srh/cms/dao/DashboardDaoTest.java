package com.srh.cms.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.srh.cms.entities.Brand;

public class DashboardDaoTest {

	DashboardDao dashboardDao = new DashboardDao();
	
	/*@Test
	public void testDashboardDao() {
		fail("Not yet implemented");
	}*/

	/*@Test
	public void testGetProduct() {
			
	}

	@Test
	public void testCreateTicket() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGetBrandId() {
		assertNotNull(dashboardDao.getBrandId("samsung"));
	}
/*
	@Test
	public void testGetAllOpenTickets() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllCutomerTickets() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOpenCutomerTickets() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloseTicket() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCategories() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckBrand() {
		Brand testBrandName = dashboardDao.checkBrand("lenovo");
		assertEquals("Lenovo",testBrandName.getBrandName());
	}

	@Test
	public void testGetProductFromCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnRegisterProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActiveSupplierProducts() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTicketSolution() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFeedback() {
		fail("Not yet implemented");
	}

*/
}
