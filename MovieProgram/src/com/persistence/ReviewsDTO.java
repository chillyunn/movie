package com.persistence;

public class ReviewsDTO {
	private String RevId;
	private String GusId;
	private String MovTitle;
	private String RevDate;
	private String RevContent;
	private String RevGrade;

	public ReviewsDTO(String RevId, String GusId, String MovTitle, String RevDate, String RevContent, String RevGrade) {
		super();
		this.RevId = RevId;
		this.GusId = GusId;
		this.MovTitle = MovTitle;
		this.RevDate = RevDate;
		this.RevContent = RevContent;
		this.RevGrade = RevGrade;
	}

	public String getRevId() {
		return RevId;
	}

	public void setRevId(String revId) {
		RevId = revId;
	}

	public String getGusId() {
		return GusId;
	}

	public void setGusId(String gusId) {
		GusId = gusId;
	}

	public String getMovTitle() {
		return MovTitle;
	}

	public void setMovTitle(String movTitle) {
		MovTitle = movTitle;
	}

	public String getRevDate() {
		return RevDate;
	}

	public void setRevDate(String revDate) {
		RevDate = revDate;
	}

	public String getRevContent() {
		return RevContent;
	}

	public void setRevContent(String revContent) {
		RevContent = revContent;
	}

	public String getRevGrade() {
		return RevGrade;
	}

	public void setRevGrade(String revGrade) {
		RevGrade = revGrade;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(RevId + " | ");
		sb.append(GusId + " | ");
		sb.append(MovTitle + " | ");
		sb.append(RevDate + " | ");
		sb.append(RevContent + " | ");
		sb.append(RevGrade + " | ");
		return sb.toString();
	}
}