package com.persistence;

public class TimeTablesDTO {
	private String TtId;
	private String MovTitle;
	private String ThtId;
	private String ScrId;
	private String TtTime;
	
	public TimeTablesDTO(String TtId, String MovTitle, String ThtId, String ScrId, String TtTime) {
		super();
		this.TtId = TtId;
		this.MovTitle = MovTitle;
		this.ThtId = ThtId;
		this.ScrId = ScrId;
		this.TtTime = TtTime;
	}
	
	public String getTtId() {
		return TtId;
	}
	public void setTtId(String ttId) {
		TtId = ttId;
	}
	public String getMovTitle() {
		return MovTitle;
	}
	public void setMovTitle(String movTitle) {
		MovTitle = movTitle;
	}
	public String getThtId() {
		return ThtId;
	}
	public void setThtId(String thtId) {
		ThtId = thtId;
	}
	public String getScrId() {
		return ScrId;
	}
	public void setScrId(String scrId) {
		ScrId = scrId;
	}
	public String getTtTime() {
		return TtTime;
	}
	public void setTtTime(String ttTime) {
		TtTime = ttTime;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(TtId + " | ");
		sb.append(MovTitle + " | ");
		sb.append(ThtId + " | ");
		sb.append(ScrId + " | ");
		sb.append(TtTime + " | ");
		return sb.toString();
	}
}
