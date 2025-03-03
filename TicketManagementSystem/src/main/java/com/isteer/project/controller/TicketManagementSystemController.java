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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isteer.project.dto.ErrorResponseDto;
import com.isteer.project.dto.SuccessResponseDto;
import com.isteer.project.entity.TicketManagementSystem;
import com.isteer.project.enums.ResponseMessageEnum;
import com.isteer.project.service.TicketManagementService;

@RestController
@RequestMapping("tms")
public class TicketManagementSystemController {

	@Autowired
	TicketManagementService service;

	@PreAuthorize("@permissionService.hasPermission()")
	@PostMapping("user/raiseTicket")
	public ResponseEntity<?> raiseTicket(@RequestBody TicketManagementSystem ticket) {
		int status = service.raiseTicket(ticket);
		if (status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.TICKET_RAISED_SUCCESS.getResponseCode(),
					ResponseMessageEnum.TICKET_RAISED_SUCCESS.getResponseMessage() + ticket.getTicketId());
			return ResponseEntity.ok(response);
		}
		ErrorResponseDto response = new ErrorResponseDto(ResponseMessageEnum.TICKET_RAISING_FAILED.getResponseCode(),
				ResponseMessageEnum.TICKET_RAISING_FAILED.getResponseMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@PreAuthorize("@permissionService.hasPermission()")
	@GetMapping("user/getTickets")
	public ResponseEntity<?> ticketsForUser() {
		List<TicketManagementSystem> tickets = service.getTicketsByUser();
		if (tickets.isEmpty()) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.NO_TICKETS_FOUND_FOR_THE_USER.getResponseCode(),
					ResponseMessageEnum.NO_TICKETS_FOUND_FOR_THE_USER.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(tickets);
	}

	@PreAuthorize("@permissionService.hasPermission()")
	@GetMapping("getTicketDetails")
	public ResponseEntity<?> ticketDetails(@RequestParam String ticketId) {
		TicketManagementSystem ticket = service.getTicketByTicketId(ticketId);
		if (ticket == null) {
			ErrorResponseDto response = new ErrorResponseDto(
					ResponseMessageEnum.NO_TICKET_FOUND_FOR_THE_TICKET_ID.getResponseCode(),
					ResponseMessageEnum.NO_TICKET_FOUND_FOR_THE_TICKET_ID.getResponseMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		return ResponseEntity.ok(ticket);
	}

	@PreAuthorize("@permissionService.hasPermission()")
	@PatchMapping("admin/assign")
	public ResponseEntity<?> assignTicket(@RequestParam String ticketId, String assignTo) {
		int status = service.assignTicketTo(ticketId, assignTo);
		if (status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.ASSIGNED_TICKET_TO_ADMIN.getResponseCode(),
					ResponseMessageEnum.ASSIGNED_TICKET_TO_ADMIN.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		ErrorResponseDto response = new ErrorResponseDto(
				ResponseMessageEnum.TICKET_ID_NOT_FOUND_EXCEPTION.getResponseCode(),
				ResponseMessageEnum.TICKET_ID_NOT_FOUND_EXCEPTION.getResponseMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@PreAuthorize("@permissionService.hasPermission()")
	@PatchMapping("admin/setStatus")
	public ResponseEntity<?> setTicketStatus(@RequestParam String ticketId, String ticketStatus) {
		int status = service.updateTicketStatus(ticketId, ticketStatus);
		if (status > 0) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.STATUS_SET_SUCCESSFULLY.getResponseCode(),
					ResponseMessageEnum.STATUS_SET_SUCCESSFULLY.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		ErrorResponseDto response = new ErrorResponseDto(
				ResponseMessageEnum.TICKET_ID_NOT_FOUND_EXCEPTION.getResponseCode(),
				ResponseMessageEnum.TICKET_ID_NOT_FOUND_EXCEPTION.getResponseMessage());
		return ResponseEntity.badRequest().body(response);
	}

	@PreAuthorize("@permissionService.hasPermission()")
	@GetMapping("admin/assignedTickets")
	public ResponseEntity<?> getAssignedTickets() {
		List<TicketManagementSystem> assignedTickets = service.getAssignedTickets();
		if (assignedTickets.isEmpty()) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.NO_ASSIGNED_TICKETS_FOUND.getResponseCode(),
					ResponseMessageEnum.NO_ASSIGNED_TICKETS_FOUND.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(assignedTickets);
	}

	@PreAuthorize("@permissionService.hasPermission()")
	@GetMapping("admin/workingTickets")
	public ResponseEntity<?> getWorkingTickets() {
		List<TicketManagementSystem> workingTickets = service.getWorkingTickets();
		if (workingTickets.isEmpty()) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.NO_WORKING_TICKETS_FOUND.getResponseCode(),
					ResponseMessageEnum.NO_WORKING_TICKETS_FOUND.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(workingTickets);
	}

}
