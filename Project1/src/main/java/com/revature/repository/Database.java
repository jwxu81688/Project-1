package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

import com.revature.model.Personnel;
import com.revature.model.Ticket;

public class Database {
	
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
	
	public void submit(Ticket ticket) {
		
		PreparedStatement stmt = null;
		final String SQL = "INSERT INTO ticket VALUES (?, ?, ?, ?, ?)";
		
		try(Connection conn = getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, ticket.getUserID());
			stmt.setInt(2, ticket.getTicketID());
			stmt.setFloat(3, ticket.getAmount());
			stmt.setString(4, ticket.getDescription());
			stmt.setString(5, ticket.getStatus());
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
	
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(
				System.getenv("url"), 
				System.getenv("db_username"),
				System.getenv("db_password"));
		
		return conn;
	}
}
