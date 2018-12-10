package com.srh.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import com.srh.cms.controller.DashboardDemo;
import com.srh.cms.entities.CustomerUser;
import com.srh.cms.entities.SupplierUser;
import com.srh.cms.entities.Technician;
import com.srh.cms.entities.UserTypes;

public class UserDao {

	EntityManagerFactory emFactory;
	EntityManager entityManager;
	UserTypes userTypes=new UserTypes();
	//Logger logger = Logger.getLogger(UserDao.class);

	public UserDao() {

		emFactory = Persistence.createEntityManagerFactory("ComplaintMngmtSystem");
		entityManager = emFactory.createEntityManager();
		
	}

	public void registerCustomer(CustomerUser customerUser) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(customerUser);
			entityManager.getTransaction().commit();
			System.out.println("Registeration successful!");

		} catch (Exception e) {
			System.out.println("Registeration unsuccessful!");
		}
	}

	public UserTypes loginCustomer(String userName, String password) {

		try {
			Query query = entityManager.createQuery(
					"Select c FROM CustomerUser c WHERE c.custusername =:custusername AND c.password =:password");
			query.setParameter("custusername", userName);
			query.setParameter("password", password);
			CustomerUser customer = (CustomerUser) query.getSingleResult();
			if (customer != null) {
				System.out.println("Login successful!! Welcome " + customer.getFirstName().toUpperCase() + "!");
				userTypes.setCustomerUser(customer);
				return userTypes;

			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Incorect email or password!");
			return null;
		}
	}
	
	
	public void registerSupplier(SupplierUser supplierUser) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(supplierUser);
			entityManager.getTransaction().commit();
			System.out.println("Registeration successful!");

		} catch (Exception e) {
			System.out.println("Registeration unsuccessful!");
		}
		
		
	}
	

	public UserTypes supplierLogin(String username, String password) {
		try {
			Query query = entityManager.createQuery(
					"SELECT s FROM SupplierUser s WHERE s.suppusername =:username AND s.password =:password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			SupplierUser supplier = (SupplierUser) query.getSingleResult();
			if (supplier != null) {
				System.out.println("Welcome " + supplier.getSuppusername().toUpperCase() + "!");
				userTypes.setSupplieruser(supplier);
				return userTypes;

			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Incorect email or password!");
			return null;
		}
	}

	public UserTypes technicianLogin(String username, String password) {

		try {
			Query query = entityManager.createQuery(
					"SELECT t FROM Technician t WHERE t.techusername =:username AND t.password =:password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			Technician technician = (Technician) query.getSingleResult();
			if (technician != null) {
				System.out.println("Welcome " + technician.getTechnicalName().toUpperCase() + "!");
				userTypes.setTechnician(technician);
				return userTypes;

			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Incorect email or password!");
			return null;
		}
	}
	
	

}
