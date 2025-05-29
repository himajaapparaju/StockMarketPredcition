package com.infy.model;

import java.util.List;

public class MyData {
	
	private List<Double> atm;
	private List<Double> oi;
	private List<Double> rs;
	private List<Double> iv;
	private List<Double> premiumDecay;
	private List<Double> pcr;
	private List<Double> vix;
	private List<Double> sp;
	private String message;
	
	public List<Double> getSp() {
		return sp;
	}
	public void setSp(List<Double> sp) {
		this.sp = sp;
	}
	public List<Double> getPremiumDecay() {
		return premiumDecay;
	}
	public void setPremiumDecay(List<Double> premiumDecay) {
		this.premiumDecay = premiumDecay;
	}
	public List<Double> getIv() {
		return iv;
	}
	public void setIv(List<Double> iv) {
		this.iv = iv;
	}
	
	public List<Double> getPcr() {
		return pcr;
	}
	public void setPcr(List<Double> pcr) {
		this.pcr = pcr;
	}
	public List<Double> getVix() {
		return vix;
	}
	public void setVix(List<Double> vix) {
		this.vix = vix;
	}
	public List<Double> getRs() {
		return rs;
	}
	public void setRs(List<Double> rs) {
		this.rs = rs;
	}
	public List<Double> getOi() {
		return oi;
	}
	public void setOi(List<Double> oi) {
		this.oi = oi;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Double> getAtm() {
		return atm;
	}
	public void setAtm(List<Double> atm) {
		this.atm = atm;
	}
}
