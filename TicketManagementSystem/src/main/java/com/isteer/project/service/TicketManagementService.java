package com.isteer.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.isteer.project.entity.TicketManagementSystem;
import com.isteer.project.entity.User;
import com.isteer.project.repository.TicketManagementSystemRepo;
import com.isteer.project.repository.UserSecurityRepo;

@Service
public class TicketManagementService {

	@Autowired
	TicketManagementSystemRepo ticketDao;
	@Autowired
	UserSecurityRepo userRepo;
	
	public int raiseTicket(TicketManagementSystem ticket) {
		ticket.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		ticket.setCreatedOn(LocalDateTime.now());
		int status = ticketDao.raiseTicket(ticket);
		return status;
	}
	
	public List<TicketManagementSystem> getTicketsByUser() {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		List<TicketManagementSystem> tickets = ticketDao.getTicketsByUser(createdBy);
		return tickets;
	}
	
	public TicketManagementSystem getTicketByTicketId(Integer ticketId) {
		TicketManagementSystem ticket = ticketDao.getTicketById(ticketId);
		return ticket;
	}
	
	public int assignTicketTo(Integer ticketId, String assignTo) {
		User user = userRepo.findByUserName(assignTo);
		if(user == null)
			return 0;
		boolean roleStatus = false;
		if(user.getRoles().size() == 1) {
		roleStatus = user.getRoles().stream().anyMatch(role -> role.getRole().equalsIgnoreCase("User"));
		}
		if(!roleStatus) {
		int status = ticketDao.assignTicketTo(ticketId, assignTo);
		return status;
	}
		return 0;
	}
	
	public int updateTicketStatus(Integer ticketId, String ticketStatus) {
		int status = ticketDao.updateTicketStatus(ticketId, ticketStatus);
		return status;
	}
}
