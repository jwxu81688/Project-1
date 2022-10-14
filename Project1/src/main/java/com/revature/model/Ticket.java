package com.revature.model;

public class Ticket {

	private float amount;
	private String description;
	private String status = "Pending";
	
	public Ticket() {
		super();
	}
	
	public Ticket(float amount, String description, String status) {
		super();
		this.amount = amount;
		this.description = description;
		this.status = status;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Ticket [amount=" + amount + ", description=" + description + ", status=" + status + "]";
	}
}
