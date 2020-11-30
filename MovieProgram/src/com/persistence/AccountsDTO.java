package com.persistence;

public class AccountsDTO {
	private String AccId;
	private String AccPw;
	private String AccBank;
	private String AccSerial;
	private String AccBalance;

	public AccountsDTO(String AccId, String AccPw, String AccBank, String AccSerial, String AccBalance) {
		super();
		this.AccId = AccId;
		this.AccPw = AccPw;
		this.AccBank = AccBank;
		this.AccSerial = AccSerial;
		this.AccBalance = AccBalance;
	}

	public String getAccId() {
		return AccId;
	}

	public void setAccId(String accId) {
		AccId = accId;
	}

	public String getAccPw() {
		return AccPw;
	}

	public void setAccPw(String accPw) {
		AccPw = accPw;
	}

	public String getAccBank() {
		return AccBank;
	}

	public void setAccBank(String accBank) {
		AccBank = accBank;
	}

	public String getAccSerial() {
		return AccSerial;
	}

	public void setAccSerial(String accSerial) {
		AccSerial = accSerial;
	}

	public String getAccBalance() {
		return AccBalance;
	}

	public void setAccBalance(String accBalance) {
		AccBalance = accBalance;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(AccId + " | ");
		sb.append(AccPw + " | ");
		sb.append(AccBank + " | ");
		sb.append(AccSerial + " | ");
		sb.append(AccBalance + " | ");
		return sb.toString();
	}

}
