package com.vaccine.notifier.vaccinenotifier.dto;

public class SubscriberResponse {
	String message;

	public SubscriberResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
