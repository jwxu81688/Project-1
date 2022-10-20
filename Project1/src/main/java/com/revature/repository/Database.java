package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

import com.revature.model.Personnel;
import com.revature.model.Ticket;

public class Database {
	
	public boolean login(String username, String password) {
		
		PreparedStatement stmt = null;
		final String SQL = "SELECT * FROM personnel WHERE personnel_username = ? "
				+ "AND personnel_password = ?";
		boolean registered = false;
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet r = stmt.executeQuery();
			while(r.next()) {
				if(r.getString(1).equals(username) && r.getString(2).equals(password)) {
					registered = true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return registered;
	}
	
	public void register(Personnel personnel) {
		
		PreparedStatement stmt = null;
		final String SQL = "INSERT INTO personnel VALUES (?, ?, ?)";
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, personnel.getUsername());
			stmt.setString(2, personnel.getPassword());
			stmt.setString(3, personnel.getRole());
			stmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int checkRepo(Personnel personnel) {
		
		PreparedStatement stmt = null;
		final String SQL = "SELECT * FROM personnel WHERE personnel_username = ?";
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, personnel.getUsername());
			ResultSet r = stmt.executeQuery();
			while(r.next()) {
				if(r.getString(1).equals(personnel.getUsername())) {
					return 1;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(personnel.getUsername() == null || personnel.getPassword() == null ||
				personnel.getUsername().length() == 0 || personnel.getPassword().length() == 0) {
			return 2;
		}else if(!personnel.getRole().equals("Employee") && !personnel.getRole().equals("Manager")) {
			return -1;
		}
		
		return 0;
	}
	
	public void submit(String username, Ticket ticket) {
		
		PreparedStatement stmt = null;
		final String SQL = "INSERT INTO ticket VALUES (?, default, ?, ?, 'Pending')";
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			stmt.setFloat(2, ticket.getAmount());
			stmt.setString(3, ticket.getDescription());
			stmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int checkRequest(Ticket ticket) {
		if(ticket.getAmount() <= 0) {
			return 1;
		}else if(ticket.getDescription() == null ||
				ticket.getDescription().length() == 0) {
			return -1;
		}
		return 0;
	}
	
	public boolean checkManager(String username) {
		
		PreparedStatement stmt = null;
		final String SQL = "SELECT * FROM personnel WHERE personnel_username = ?";
		boolean manager = false;
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			while(r.next()) {
				if(r.getString(3).equals("Manager")) {
					manager = true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return manager;
	}
	
	public List<Ticket> showPending() {
		
		List<Ticket> tickets = new ArrayList<>();
		ResultSet r = null;
		Statement stmt = null;
		
		try(Connection conn = getConnection()) {
			stmt = conn.createStatement();
			r = stmt.executeQuery("SELECT * FROM ticket WHERE ticket_status = 'Pending'");
			while(r.next()) {
				tickets.add(new Ticket(r.getString(1), r.getInt(2), r.getFloat(3), r.getString(4), r.getString(5)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				r.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;
	}
	
	public int approve(int ticketID) {
		
		PreparedStatement stmt = null;
		final String SQL = "UPDATE ticket SET ticket_status = 'Approved' WHERE ticket_id = ? AND ticket_status = 'Pending'";
		int update = 0;
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, ticketID);
			update = stmt.executeUpdate();
			//System.out.println(update);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return update;
	}
	
	public int deny(int ticketID) {
		
		PreparedStatement stmt = null;
		final String SQL = "UPDATE ticket SET ticket_status = 'Denied' WHERE ticket_id = ? AND ticket_status = 'Pending'";
		int update = 0;
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, ticketID);
			update = stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return update;
	}
	
	public boolean checkEmployee(String username) {
		
		PreparedStatement stmt = null;
		final String SQL = "SELECT * FROM personnel WHERE personnel_username = ?";
		boolean employee = false;
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			while(r.next()) {
				if(r.getString(3).equals("Employee")) {
					employee = true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return employee;
	}
	
	public List<Ticket> showProcessed(String username) {
		
		List<Ticket> tickets = new ArrayList<>();
		final String SQL = "SELECT * FROM ticket WHERE ticket_status != 'Pending' AND ticket_user = ?";
		ResultSet r = null;
		PreparedStatement stmt = null;
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			r = stmt.executeQuery();
			while(r.next()) {
				tickets.add(new Ticket(r.getString(1), r.getInt(2), r.getFloat(3), r.getString(4), r.getString(5)));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				r.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return tickets;
	}
	
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(
				System.getenv("url"), 
				System.getenv("db_username"),
				System.getenv("db_password"));
		
		return conn;
	}
}
