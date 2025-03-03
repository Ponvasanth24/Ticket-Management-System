package com.isteer.project.entity;

import java.time.LocalDateTime;

public class TicketManagementSystem {

	private String ticketId;
	private String createdBy;
	private String ticketHeading;
	private String ticketDescription;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private String ticketStatus = "Opened";
	private String assignedTo = "To be assigned";
	
	
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getTicketHeading() {
		return ticketHeading;
	}
	public void setTicketHeading(String ticketHeading) {
		this.ticketHeading = ticketHeading;
	}
	public String getTicketDescription() {
		return ticketDescription;
	}
	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	
	@Override
	public String toString() {
		return "TicketManagementSystem [ticketId=" + ticketId + ", createdBy=" + createdBy + ", ticketHeading="
				+ ticketHeading + ", ticketDescription=" + ticketDescription + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", ticketStatus=" + ticketStatus + ", assignedTo=" + assignedTo + "]";
	}
	
}
