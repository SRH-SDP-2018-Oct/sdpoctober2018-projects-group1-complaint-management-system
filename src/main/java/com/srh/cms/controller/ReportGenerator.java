package com.srh.cms.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;


public class ReportGenerator {

	Connection connection = null;
	Scanner scanner=new Scanner(System.in);
	
	
	public void getReports() throws ClassNotFoundException, SQLException, ParseException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "24051996");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("\n\n[1]  Count Report\n[2]  Ticket Detail Report");
		int choice = Integer.parseInt(scanner.nextLine());
		switch (choice) {
			case 1:	 getCountReport();
				 	 break;
			case 2:	 getTicketDetailReport();
				 	 break;
			default: System.out.println("\nPlease select a valid option!");
				 	 break;
		}
	}

	private void getTicketDetailReport() {
		JasperReportBuilder report = DynamicReports.report();// a new report
		report.columns(
				Columns.column("ServiceRequest Id", "servicerequestId", DataTypes.integerType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Technician Name", "technicalname", DataTypes.stringType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Creation Date", "creationdate", DataTypes.dateType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Finish Date", "resolutiondate", DataTypes.dateType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Status", "status", DataTypes.stringType()).setPattern("\u20ac 0.00")
						.setHorizontalAlignment(HorizontalAlignment.LEFT))
				.highlightDetailEvenRows().title(// title of the report
						Components.text("Tickets Detail Reports").setPattern("\u20ac 0.00").setHorizontalAlignment(
								HorizontalAlignment.CENTER),
						Components.text("Report Date"), Components.currentDate())
				.pageFooter(Components.pageXofY())// show page number on the page footer
				.setDataSource(
						"SELECT r.servicerequestid servicerequestid,t.technicalname technicalname,sr.creationdate creationdate,r.resolutiondate resolutiondate,r.status status FROM cms.resolution r,cms.assignmentjunction aj,cms.technician t,cms.servicerequest sr WHERE t.technicianid = aj.technicianid AND r.resolutionid = aj.resolutionid AND sr.servicerequestid = r.servicerequestid",
						connection);
		try {
			report.show();
			report.toPdf(new FileOutputStream("D:/TicketReport.pdf"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private void getCountReport() {
		JasperReportBuilder report = DynamicReports.report();
		report.columns(
				Columns.column("Category", "category", DataTypes.stringType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Technician Name", "technicalname", DataTypes.stringType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Open Tickets", "opentickets", DataTypes.integerType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT),
				Columns.column("Total Tickets", "totaltickets", DataTypes.integerType())
						.setHorizontalAlignment(HorizontalAlignment.LEFT))
				.highlightDetailEvenRows().title(// title of the report
						Components.text("Technician Count Reports").setPattern("\u20ac 0.00").setHorizontalAlignment(
								HorizontalAlignment.CENTER),
						Components.text("Report Date"), Components.currentDate())
				.pageFooter(Components.pageXofY())// show page number on the page footer
				.setDataSource(
						"SELECT c.categorytype category, t.technicalname technicianname,t.opentickets opentickets, t.totaltickets totaltickets FROM cms.technician t, cms.category c where t.categoryid = c.categoryid order by c.categoryid",
						connection);

		try {
			report.show();
			report.toPdf(new FileOutputStream("D:/CountReport.pdf"));
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}