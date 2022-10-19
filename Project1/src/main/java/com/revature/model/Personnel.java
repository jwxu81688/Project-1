package com.revature.model;

public class Personnel {
	
	private String username;
	private String password;
	private String role = "Employee";
	
	public Personnel() {
		super();
	}
	
	public Personnel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public Personnel(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "Personnel [username=" + username + ", password=" + password + ", role=" + role + "]\n";
	}
}
