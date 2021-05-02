package com.vaccine.notifier.vaccinenotifier.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class Center implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("center_id")
	private Long centerId;
	private String name;
	@JsonProperty("state_name")
	private String stateName;
	@JsonProperty("district_name")
	private String districtName;
	@JsonProperty("block_name")
	private String blockName;
	private long pincode;
	@JsonProperty("lat")
	private BigDecimal latitude;
	@JsonProperty("long")
	private BigDecimal longitude;
	private String from;
	private String to;
	@JsonProperty("fee_type")
	private String feeType;
	private List<Session> sessions;
	
	public Long getCenterId() {
		return centerId;
	}
	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public long getPincode() {
		return pincode;
	}
	public void setPincode(long pincode) {
		this.pincode = pincode;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public List<Session> getSessions() {
		return sessions;
	}
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	@Override
	public String toString() {
		return "Center [centerId=" + centerId + ", name=" + name + ", stateName=" + stateName + ", districtName="
				+ districtName + ", blockName=" + blockName + ", pincode=" + pincode + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", from=" + from + ", to=" + to + ", feeType=" + feeType + ", sessions="
				+ sessions + "]";
	}
	
	
	
}
