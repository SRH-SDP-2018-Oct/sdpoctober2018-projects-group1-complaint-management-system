package com.srh.cms.controller;


import java.sql.SQLException;


public class MainClass {	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ComplaintProcessor complaintProcessor = new ComplaintProcessor();
		complaintProcessor.run();
	}
}
