package com.vaccine.notifier.vaccinenotifier.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Session implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("session_id")
	private String sessionId;
	private String date;
	@JsonProperty("available_capacity")
	private Integer availableCapacity;
	@JsonProperty("min_age_limit")
	private Integer minAgeLimit;
	private String vaccine;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getAvailableCapacity() {
		return availableCapacity;
	}
	public void setAvailableCapacity(Integer availableCapacity) {
		this.availableCapacity = availableCapacity;
	}
	public Integer getMinAgeLimit() {
		return minAgeLimit;
	}
	public void setMinAgeLimit(Integer minAgeLimit) {
		this.minAgeLimit = minAgeLimit;
	}
	public String getVaccine() {
		return vaccine;
	}
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}
	
	
}
