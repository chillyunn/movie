package com.persistence;



public class TheatersDTO {
	private String ThtId;
	private String ThtAddress;

	public TheatersDTO(String ThtId, String ThtAddress) {
		super();
		this.ThtId = ThtId;
		this.ThtAddress = ThtAddress;
	}

	public TheatersDTO(String ThtId) {
		super();
		this.ThtId = ThtId;
	}

	public String getThtId() {
		return ThtId;
	}

	public void setThtId(String thtId) {
		ThtId = thtId;
	}

	public String getThtAddress() {
		return ThtAddress;
	}

	public void setThtAddress(String thtAddress) {
		ThtAddress = thtAddress;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ThtId + " | ");
		sb.append(ThtAddress + " | ");
		return sb.toString();
	}
}
