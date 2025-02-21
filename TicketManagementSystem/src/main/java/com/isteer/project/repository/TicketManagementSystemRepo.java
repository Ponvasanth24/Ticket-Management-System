package com.isteer.project.repository;

import java.util.List;

import com.isteer.project.entity.TicketManagementSystem;


public interface TicketManagementSystemRepo {

	public int raiseTicket(TicketManagementSystem ticket);
	public List<TicketManagementSystem> getTicketsByUser(String createdBy);
	public int assignTicketTo(Integer ticketId, String assignTo);
	public int updateTicketStatus(Integer ticketId, String ticketStatus);
	public TicketManagementSystem getTicketById(Integer ticketId);
	
}
