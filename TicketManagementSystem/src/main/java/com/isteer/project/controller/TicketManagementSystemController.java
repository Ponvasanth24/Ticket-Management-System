package com.isteer.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public ResponseEntity<?> ticketsForUser(@RequestParam(required = false) String ticketId,
			@RequestParam(required = false) List<String> statuses) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		boolean isUser = auth.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("USER"));
		boolean isParamsNull = (ticketId == null || ticketId.isEmpty()) && (statuses.isEmpty() || statuses == null);
		List<TicketManagementSystem> tickets;
		if (isParamsNull) {
			if (isUser) {
				tickets = service.getTicketsByUser();
			} else {
				tickets = service.getAllTickets();
			}
		} else {
			if(isUser) {
				tickets = service.getTicketsForUser(ticketId, statuses, userName);
			} else {
				tickets = service.getTicketsForAdmin(ticketId, statuses, userName);
			}
		}
		if (tickets.isEmpty()) {
			SuccessResponseDto response = new SuccessResponseDto(
					ResponseMessageEnum.NO_TICKETS_FOUND_FOR_THE_USER.getResponseCode(),
					ResponseMessageEnum.NO_TICKETS_FOUND_FOR_THE_USER.getResponseMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(tickets);
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
}
