package com.persistence;

public class ScreensDTO {
	private String ScrId;
	private String ThtId;
	private String ScrType;
	private String ScrPremium;
	
	
	public ScreensDTO(String ScrId,String ThtId,String ScrType,String ScrPremium) {
		super();
		this.ScrId = ScrId;
		this.ThtId = ThtId;
		this.ScrType = ScrType;
		this.ScrPremium = ScrPremium;
	}
	
	public ScreensDTO(String ScrId,String ThtId) {
		super();
		this.ScrId = ScrId;
		this.ThtId = ThtId;
	}
	
	public String getScrId() {
		return ScrId;
	}
	public void setScrId(String scrId) {
		ScrId = scrId;
	}
	public String getThtId() {
		return ThtId;
	}
	public void setThtId(String thtId) {
		ThtId = thtId;
	}
	public String getScrType() {
		return ScrType;
	}
	public void setScrType(String scrType) {
		ScrType = scrType;
	}
	public String getScrPremium() {
		return ScrPremium;
	}
	public void setScrPremium(String scrPremium) {
		ScrPremium = scrPremium;
	}
	

@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(ScrId+" | ");
	sb.append(ThtId+" | ");
	sb.append(ScrType+" | ");
	sb.append(ScrPremium+" | ");
	return sb.toString();
}
}
