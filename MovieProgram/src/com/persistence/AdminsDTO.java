package com.persistence;

public class AdminsDTO 
{
private String AdmId;
private String AdmPw;
private String ThtId;

public AdminsDTO(String AdmId, String AdmPw,String ThtId) {
	super();
	this.AdmId = AdmId;
	this.AdmPw =AdmPw;
	this.ThtId = ThtId;
}

public String getAdmId() {
	return AdmId;
}
public void setAdmId(String admId) {
	AdmId = admId;
}
public String getAdmPw() {
	return AdmPw;
}
public void setAdmPw(String admPw) {
	AdmPw = admPw;
}
public String getThtId() {
	return ThtId;
}
public void setThtId(String thtId) {
	ThtId = thtId;
}
@Override
public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(AdmId+" | ");
	sb.append(AdmPw+" | ");
	sb.append(ThtId+" | ");
	return sb.toString();
}
}
