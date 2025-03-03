package com.isteer.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.isteer.project.entity.TicketManagementSystem;
import com.isteer.project.entity.User;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.exception.AssignTicketToAdminException;
import com.isteer.project.exception.UserNameNotFoundException;
import com.isteer.project.repository.TicketManagementSystemRepo;
import com.isteer.project.repository.UserSecurityRepo;
import com.isteer.project.utility.UUIdGenerator;

@Service
public class TicketManagementService {

	@Autowired
	TicketManagementSystemRepo ticketDao;
	@Autowired
	UserSecurityRepo userRepo;
	@Autowired
	UUIdGenerator shortIdGenerator;
	
	public int raiseTicket(TicketManagementSystem ticket) {
		ticket.setTicketId("TKT"+shortIdGenerator.generateShortID());
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
	
	public List<TicketManagementSystem> getAllTickets() {
		List<TicketManagementSystem> tickets = ticketDao.getAllTickets();
		return tickets;
	}
	
	public List<TicketManagementSystem> getTicketsForUser(String ticketId, List<String> statuses, String userName) {
		List<TicketManagementSystem> tickets = null;
		if(ticketId != null && !(ticketId.isEmpty())) {
			tickets = ticketDao.getTicketById(ticketId);
		}
		if(statuses != null && !statuses.isEmpty()) {
			for (String status : statuses) {
				tickets = ticketDao.getTicketByStatusForUser(status, userName);
			}
		}
		return tickets;
	}
	
	public List<TicketManagementSystem> getTicketsForAdmin(String ticketId, List<String> statuses, String adminName) {
		List<TicketManagementSystem> tickets = null;
		if(ticketId != null && !(ticketId.isEmpty())) {
			tickets = ticketDao.getTicketById(ticketId);
		}
		if(statuses != null && !statuses.isEmpty()) {
			for (String status : statuses) {
				tickets = ticketDao.getTicketByStatusForAdmin(status, adminName);
			}
		}
		return tickets;
	}
	
	public int assignTicketTo(String ticketId, String assignTo) {
		User user = userRepo.findByUserName(assignTo);
		if(user == null)
			throw new UserNameNotFoundException(ResponseMessageEnum.USERNAME_NOT_FOUND_EXCEPTION);
		boolean roleStatus = false;
		if(user.getRoles().size() == 1) {
		roleStatus = user.getRoles().stream().anyMatch(role -> role.getRole().equalsIgnoreCase("User"));
		}
		if(!roleStatus) {
		int status = ticketDao.assignTicketTo(ticketId, assignTo);
		return status;
	}
		throw new AssignTicketToAdminException(ResponseMessageEnum.ASSIGNING_TICKET_TO_USER_ERROR);
	}
	
	public int updateTicketStatus(String ticketId, String ticketStatus) {
		int status = ticketDao.updateTicketStatus(ticketId, ticketStatus);
		return status;
	}

}
