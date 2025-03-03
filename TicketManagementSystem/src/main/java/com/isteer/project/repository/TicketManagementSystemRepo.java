package com.isteer.project.repository;

import java.util.List;

import com.isteer.project.entity.TicketManagementSystem;


public interface TicketManagementSystemRepo {

	public int raiseTicket(TicketManagementSystem ticket);
	public List<TicketManagementSystem> getTicketsByUser(String createdBy);
	public int assignTicketTo(String ticketId, String assignTo);
	public int updateTicketStatus(String ticketId, String ticketStatus);
	public List<TicketManagementSystem> getTicketById(String ticketId);
	public List<TicketManagementSystem> getAllTickets();
	public List<TicketManagementSystem> getTicketByStatusForUser(String status, String userName);
	public List<TicketManagementSystem> getTicketByStatusForAdmin(String status, String userName);

}
