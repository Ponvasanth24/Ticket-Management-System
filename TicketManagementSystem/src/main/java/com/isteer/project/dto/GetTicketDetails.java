package com.isteer.project.dto;

import java.util.List;

public class GetTicketDetails {
	
	private String ticketId;
	private List<String> statuses;
	
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public List<String> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	@Override
	public String toString() {
		return "GetTicketDetails [ticketId=" + ticketId + ", statuses=" + statuses + "]";
	}
	
}
