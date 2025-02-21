package com.isteer.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isteer.project.dto.SuccessResponseDto;
import com.isteer.project.entity.TicketManagementSystem;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.service.TicketManagementService;



@RestController
public class TicketManagementSystemController {

	@Autowired
	TicketManagementService service;
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("raiseTicket")
	public ResponseEntity<?> raiseTicket(@RequestBody TicketManagementSystem ticket) {
		int status = service.raiseTicket(ticket);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.TICKET_RAISED_SUCCESS.getResponseCode(), ResponseMessageEnum.TICKET_RAISED_SUCCESS.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket not submitted");
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@GetMapping("getTickets")
	public List<?> ticketsForUser() {
		List<TicketManagementSystem> tickets = service.getTicketsByUser();
		return tickets;
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@GetMapping("getTicketDetails")
	public TicketManagementSystem ticketDetails(@RequestParam Integer ticketId) {
		TicketManagementSystem ticket = service.getTicketByTicketId(ticketId);
		return ticket;
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("assign")
	public ResponseEntity<?> assignTicket(@RequestParam int ticketId, String assignTo) {
		int status = service.assignTicketTo(ticketId, assignTo);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.ASSIGNED_TICKET_TO_ADMIN.getResponseCode(), ResponseMessageEnum.ASSIGNED_TICKET_TO_ADMIN.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Assigned");
	}
	
	@PreAuthorize("@permissionService.hasPermission()")
	@PatchMapping("setStatus")
	public ResponseEntity<?> setTicketStatus(Integer ticketId, String ticketStatus) {
		int status = service.updateTicketStatus(ticketId, ticketStatus);
		if(status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(ResponseMessageEnum.STATUS_SET_SUCCESSFULLY.getResponseCode(), ResponseMessageEnum.STATUS_SET_SUCCESSFULLY.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status not updated");
	}
	
}
