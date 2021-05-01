package com.vaccine.notifier.vaccinenotifier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class State {

	@JsonProperty("state_id")
	private Long stateId;
	@JsonProperty("state_name")
	private String stateName;
	
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	
}
