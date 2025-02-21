package com.isteer.project.enums;

public enum ResponseMessageEnum {
	
	USER_REGISTRATION_SUCCESS(1001, "User is registered along with their credentials!!!"),
	
	ROLE_ELEVATION_SUCCESS(1002, "Elevation Success"),
	
	ROLE_DEMOTION_SUCCESS(1003, "Demotion Success"),
	
	ADD_NEW_ROLE(1004, "New role added successfully"),
	
	REMOVE_EXISTING_ROLE(1005,"Existing role removed successfully"),
	
	ADD_URL_FOR_DYNAMIC_RBAC(1006, "Url added successfully"),
	
	REMOVE_URL_FROM_DYNAMIC_RBAC(1007, "Url removed successfully"),
	
	TICKET_RAISED_SUCCESS(1008, "Ticket raised successfully"),
	
	ASSIGNED_TICKET_TO_ADMIN(1009, "Ticket is assigned to a admin successfully"),
	
	STATUS_SET_SUCCESSFULLY(1010,"Ticket status is updated successfully"),
	
	USER_IS_NOT_REGISTERED(9001, "User is not registered"),
	
	ROLE_NOT_ELEVATED(9002, "Role elevation failed"),
	
	ROLE_NOT_DEMOTED(9003, "Role demotion failed"),
	
	ADDING_NEW_ROLE_FAILED(9004, "Adding new roles is failed"),
	
	REMOVING_EXISTING_ROLE_FAILED(9005, "Removing existing role failed"),
	
	ADDING_URL_FOR_DYNAMIC_RBAC_FAILED(9006, "Adding new url for dynamic RBAC failed"),
	
	REMOVING_URL_FROM_DYNAMIC_RBAC_FAILED(9007, "Removing url form dynamic RBAC failed"),
	
	TICKET_RAISING_FAILED(9008,"Ticket raisng failed"),
	
	ASSIGNING_TICKET_TO_ADMIN_FAILED(9009, "Assigning ticket to a admin failed"),
	
	TICKET_STATUS_UPDATE_FAILED(9010, "Ticket status update failed");

	int responseCode;
	String responseMessage;
	
	private ResponseMessageEnum(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
	
	
}
