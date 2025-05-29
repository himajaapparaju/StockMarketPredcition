package com.infy.model;

public class OITodayData {
	private Double strikePrice;
	private Double callOIChangeValue;
	private Double putOIChangeValue;
	String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Double getStrikePrice() {
		return strikePrice;
	}
	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}
	public Double getCallOIChangeValue() {
		return callOIChangeValue;
	}
	public void setCallOIChangeValue(Double callOIChangeValue) {
		this.callOIChangeValue = callOIChangeValue;
	}
	public Double getPutOIChangeValue() {
		return putOIChangeValue;
	}
	public void setPutOIChangeValue(Double putOIChangeValue) {
		this.putOIChangeValue = putOIChangeValue;
	}
	
}
