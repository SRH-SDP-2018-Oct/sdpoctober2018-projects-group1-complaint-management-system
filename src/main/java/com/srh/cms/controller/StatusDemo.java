package com.srh.cms.controller;

import com.srh.cms.entities.ServiceRequest;

public class StatusDemo {
	
	ServiceRequest sr = new ServiceRequest();
	
	public void setTicketStatus(int serviceRequestId, String status) {
				
		String ticketStatus = null;
		ticketStatus = status;
		sr.setStatus(ticketStatus);
		
	}
	
	public String getTicketStatus() {
		
	return sr.getStatus();	
		
	}
}
