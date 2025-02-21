package com.isteer.project.enums;

public enum ResponseMessageEnum {
	
	USER_REGISTRATION_SUCCESS(1001, "User is registered along with their credentials!!!"),
	
	ROLE_ELEVATION_SUCCESS(1002, "Elevation Success"),
	
	ROLE_DEMOTION_SUCCESS(1003, "Demotion Success"),
	
	ADD_NEW_ROLE(1004, "New role added successfully"),
	
	REMOVE_EXISTING_ROLE(1005,"Existing role removed successfully");

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
