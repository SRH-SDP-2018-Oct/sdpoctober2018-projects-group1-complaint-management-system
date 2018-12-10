package com.srh.cms.controller;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;


public class MainClass {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
		ComplaintProcessor complaintProcessor=new ComplaintProcessor();
		complaintProcessor.run();
		

	}




}
