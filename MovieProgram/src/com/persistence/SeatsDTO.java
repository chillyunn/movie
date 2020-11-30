package com.persistence;

public class SeatsDTO {
	private String SeatId;
	private String ThtId;
	private String ScrId;

	public SeatsDTO(String seatId, String thtId, String scrId) {
		super();
		SeatId = seatId;
		ThtId = thtId;
		ScrId = scrId;
	}

	public String getSeatId() {
		return SeatId;
	}

	public void setSeatId(String seatId) {
		SeatId = seatId;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(SeatId + " | ");
		sb.append(ThtId + " | ");
		sb.append(ScrId + " | ");
		return sb.toString();
	}
}
