package com.persistence;

public class TicketsDTO {
	private String TicId;
	private String ScrId;
	private String ThtId;
	private String SeatId;
	private String GusId;
	private String TtId;
	private String MovTitle;

	public TicketsDTO(String TicId, String ScrId, String ThtId, String SeatId,String GusId, String TtId,
			String MovTitle) {
		super();
		this.TicId = TicId;
		this.ScrId = ScrId;
		this.ThtId = ThtId;
		this.SeatId = SeatId;
		this.GusId = GusId;
		this.TtId = TtId;
		this.MovTitle = MovTitle;
	}

	public String getTicId() {
		return TicId;
	}

	public void setTicId(String ticId) {
		TicId = ticId;
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

	public String getSeatId() {
		return SeatId;
	}

	public void setSeatId(String seatId) {
		SeatId = seatId;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(TicId + " | ");
		sb.append(ScrId + " | ");
		sb.append(ThtId + " | ");
		sb.append(SeatId + " | ");
		sb.append(GusId + " | ");
		sb.append(TtId + " | ");
		return sb.toString();
	}

	public String getGusId() {
		return GusId;
	}

	public void setGusId(String gusId) {
		GusId = gusId;
	}
}
