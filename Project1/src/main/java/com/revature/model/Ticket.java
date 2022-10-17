package com.revature.model;

public class Ticket {

	private String userID;
	private String ticketID;
	private float amount;
	private String description;
	private String status;
	
	public Ticket() {
		super();
	}
	
	public Ticket(String userID, String ticketID, float amount, String description, String status) {
		super();
		this.userID = userID;
		this.ticketID = ticketID;
		this.amount = amount;
		this.description = description;
		this.status = status;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getTicketID() {
		return ticketID;
	}
	
	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
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
	
	public void changeStatus() {
		/*if() {
			this.status = "Approved";
		}else if() {
			this.status = "Denied";
		}*/
	}
	
	@Override
	public String toString() {
		return "Ticket [amount=" + amount + ", description=" + description + ", status=" + status + "]";
	}
}
