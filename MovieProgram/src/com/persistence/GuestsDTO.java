package com.persistence;

public class GuestsDTO {
	private String GusId;
	private String GusPw;
	private String GusName;
	private String GusAge;
	private String GusGender;
	private String GusPhone;

	public GuestsDTO(String GusId, String GusPw, String GusName, String GusAge, String GusGender, String GusPhone) {
		super();
		this.GusId = GusId;
		this.GusPw = GusPw;
		this.GusName = GusName;
		this.GusAge = GusAge;
		this.GusGender = GusGender;
		this.GusPhone = GusPhone;

	}

	public GuestsDTO(String GusData, int check) {
		super();
		if (check == 0) { 	// check값이 0이면 GusId
			this.GusId = GusData;
		}
		else if (check == 1) { 	// check값이 1이면 GusGender
			this.GusGender = GusData;
		}
		else if (check == 2) { 	// check값이 2면 GusGender
			this.GusGender = GusData;
		}
	}

	public String getGusId() {
		return GusId;
	}

	public void setGusId(String gusId) {
		GusId = gusId;
	}

	public String getGusPw() {
		return GusPw;
	}

	public void setGusPw(String gusPw) {
		GusPw = gusPw;
	}

	public String getGusName() {
		return GusName;
	}

	public void setGusName(String gusName) {
		GusName = gusName;
	}

	public String getGusAge() {
		return GusAge;
	}

	public void setGusAge(String gusAge) {
		GusAge = gusAge;
	}

	public String getGusGender() {
		return GusGender;
	}

	public void setGusGender(String gusGender) {
		GusGender = gusGender;
	}

	public String getGusPhone() {
		return GusPhone;
	}

	public void setGusPhone(String gusPhone) {
		GusPhone = gusPhone;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(GusId + " | ");
		sb.append(GusPw + " | ");
		sb.append(GusName + " | ");
		sb.append(GusAge + " | ");
		sb.append(GusGender + " | ");
		sb.append(GusPhone + " | ");
		return sb.toString();
	}
}
