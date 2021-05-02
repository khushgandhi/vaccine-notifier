package com.vaccine.notifier.vaccinenotifier.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Subscribers")
public class Subscriber {

	@Id
	private String emailId;
	private String mobile;
	private Integer minAge;
	private Long districtId;
	private Date lastNotifiedAt;
	private Boolean isActive;
	private Long stateId;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public Date getLastNotifiedAt() {
		return lastNotifiedAt;
	}
	public void setLastNotifiedAt(Date notifiedAt) {
		this.lastNotifiedAt = notifiedAt;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
}
