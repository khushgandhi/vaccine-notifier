package com.vaccine.notifier.vaccinenotifier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class District {
	
	@JsonProperty("district_id")
	private Long districtId;
	@JsonProperty("district_name")
	private String districtName;
	
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	

}
