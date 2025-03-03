package com.isteer.project.enums;

public enum ResponseMessageEnum {
	
	USER_REGISTRATION_SUCCESS(1001, "User is registered along with their credentials!!!"),
	
	ROLE_ELEVATION_SUCCESS(1002, "Elevation Success"),
	
	ROLE_DEMOTION_SUCCESS(1003, "Demotion Success"),
	
	ADD_NEW_ROLE(1004, "New role added successfully"),
	
	REMOVE_EXISTING_ROLE(1005,"Existing role removed successfully"),
	
	ADD_URL_FOR_DYNAMIC_RBAC(1006, "Url added successfully"),
	
	REMOVE_URL_FROM_DYNAMIC_RBAC(1007, "Url removed successfully"),
	
	TICKET_RAISED_SUCCESS(1008, "Ticket raised successfully with ticket ID "),
	
	ASSIGNED_TICKET_TO_ADMIN(1009, "Ticket is assigned to a admin successfully"),
	
	STATUS_SET_SUCCESSFULLY(1010,"Ticket status is updated successfully"),
	
	NO_ASSIGNED_TICKETS_FOUND(1011, "There are no tickets assigned for you currently."),
	
	NO_WORKING_TICKETS_FOUND(1012, "There are no tickets you are working currently."),
	
	NO_TICKETS_FOUND_FOR_THE_USER(1013, "No tickets found. Please raise tickets to see the tickets."),
	
	HTTP_METHOD_ADDED_SUCCESSFULLY(1014, "Http method is added successfully"),
	
	HTTP_METHOD_REMOVED_SUCCESSFULLY(1015, "Http method is removed successfully"),
	
	USER_IS_NOT_REGISTERED(9001, "User is not registered"),
	
	ROLE_NOT_ELEVATED(9002, "Role elevation failed"),
	
	ROLE_NOT_DEMOTED(9003, "Role demotion failed"),
	
	ADDING_NEW_ROLE_FAILED(9004, "Adding new roles is failed"),
	
	REMOVING_EXISTING_ROLE_FAILED(9005, "The role you are trying to remove is not present"),
	
	ADDING_URL_FOR_DYNAMIC_RBAC_FAILED(9006, "Adding new url for dynamic RBAC failed"),
	
	REMOVING_URL_FROM_DYNAMIC_RBAC_FAILED(9007, "Removing url form dynamic RBAC failed"),
	
	TICKET_RAISING_FAILED(9008,"Ticket raisng failed"),
	
	ASSIGNING_TICKET_TO_ADMIN_FAILED(9009, "Assigning ticket to a admin failed"),
	
	TICKET_STATUS_UPDATE_FAILED(9010, "Ticket status update failed"),
	
	USERNAME_NOT_FOUND_EXCEPTION(9011, "Please check the username."),
	
	ACCESS_DENIED_EXCEPTION(9012, "You dont have enough authorization to access this endpoint"),
	
	GLOBAL_EXCEPTION_HANDLER(9013, "Ooops!! You have encountered an error"),
	
	NO_TICKET_FOUND_FOR_THE_TICKET_ID(9014, "No ticket found for the given ticketId. Please check the ticketId."),
	
	TICKET_ID_NOT_FOUND_EXCEPTION(9015, "Ticket id not found. Check Your Ticket id."),
	
	ROLE_NOT_FOUND_EXCEPTION(9016, "Role not found."),
	
	DUPLICATE_KEY_EXCEPTION(9017, "The value you are trying to enter is already present"),
	
	ASSIGNING_TICKET_TO_USER_ERROR(9018, "Assign the ticket to an admin"),
	
	URL_NOT_FOUND_EXCEPTION(9019, "The url you are trying to remove is not found."),
	
	HTTP_METHOD_NOT_FOUND_EXCEPTION(9020, "The http method you are trying to remove is not found");

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
