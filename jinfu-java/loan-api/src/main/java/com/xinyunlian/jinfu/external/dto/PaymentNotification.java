package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;

public class PaymentNotification implements Serializable {
	private String loanId;
	private String date;
	private Double amount;
	public PaymentNotification(String loanId, String date, Double amount) {
		super();
		this.loanId = loanId;
		this.date = date;
		this.amount = amount;
	}
	public PaymentNotification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentNotification [loanId=");
		builder.append(loanId);
		builder.append(", date=");
		builder.append(date);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
	
}
