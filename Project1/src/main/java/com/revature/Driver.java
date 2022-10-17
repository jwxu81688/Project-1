package com.revature;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.model.Personnel;
//import com.revature.model.Ticket;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class Driver {

	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(8000);
		/*
		app.before(ctx -> {
			System.out.println("This happens before the http requests make it to their final destination.");
		});
		
		app.get("/personnel/{username}", (Context ctx) -> {
			Set<Personnel> users = new HashSet<>();
			Personnel user1 = new Personnel("jwxu", "July2502", "Employee");
			Personnel user2 = new Personnel("pdcf", "June7497", "Employee");
			Personnel user3 = new Personnel("Ross", "password", "Manager");
			
			users.add(user1);
			users.add(user2);
			users.add(user3);
			
			Personnel selectedUser = null;
			
			for(Personnel p : users) {
				if(p.getUsername().equals(String.valueOf(ctx.pathParam("username")))) selectedUser = p;
			}
			
			ctx.json(selectedUser);
		});
		
		app.post("/new-user", ctx -> {
			Personnel receivedUser = ctx.bodyAsClass(Personnel.class);
			System.out.println(receivedUser);
			ctx.status(HttpStatus.CREATED_201);
		});
		*/
		
		
	}
}
