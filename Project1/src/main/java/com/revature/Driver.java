package com.revature;

import java.util.List;

//import java.util.HashSet;
//import java.util.Set;

import org.eclipse.jetty.http.HttpStatus;

import com.revature.model.Personnel;
import com.revature.model.Ticket;
import com.revature.repository.Database;

import io.javalin.Javalin;


public class Driver {

	public static void main(String[] args) {
		
		Javalin app = Javalin.create().start(8000);
		Database database = new Database();
		
		app.post("/login", ctx -> {
			String username = ctx.formParam("username");
			String password = ctx.formParam("password");
			
			if(database.login(username, password)) {
				ctx.cookieStore().set("username", username);
				ctx.result("Successful Login");
				ctx.status(HttpStatus.OK_200);
			}else {
				ctx.result("Incorrect Username or Password, or Unregistered");
			}
		});
		
		app.get("/logout", ctx -> {
			String username = ctx.cookieStore().get("username");
			ctx.cookieStore().clear();
			if(username == null) {
				ctx.result("Not Logged In");
			}else {
				ctx.result(username + " Successfully Logged Out");
			}
		});
		
		app.post("/register", ctx -> {
			Personnel receivedUser = ctx.bodyAsClass(Personnel.class);
			int check = database.checkRepo(receivedUser);
			if(check == 0) {
				database.register(receivedUser);
				ctx.result("New User Registered");
				ctx.status(HttpStatus.CREATED_201);
			}else if(check == 1) {
				ctx.result("Username Already Registered");
				ctx.status(HttpStatus.BAD_REQUEST_400);
			}else if(check == 2) {
				ctx.result("Empty Username and/or Password");
				ctx.status(HttpStatus.BAD_REQUEST_400);
			}else {
				ctx.result("Role Is Neither Exactly 'Employee' Nor 'Manager'");
				ctx.status(HttpStatus.BAD_REQUEST_400);
			}
		});
		
		app.post("/submit", ctx -> {
			String username = ctx.cookieStore().get("username");
			if(username != null) {
				boolean employee = database.checkEmployee(username);
				if(employee) {
					Ticket receivedTicket = ctx.bodyAsClass(Ticket.class);
					int check = database.checkRequest(receivedTicket);
					if(check == 0) {
						database.submit(username, receivedTicket);
						ctx.result("New Ticket Submitted");
						ctx.status(HttpStatus.CREATED_201);
					}else if(check > 0) {
						ctx.result("Invalid Amount");
						ctx.status(HttpStatus.BAD_REQUEST_400);
					}else {
						ctx.result("Invalid Description");
						ctx.status(HttpStatus.BAD_REQUEST_400);
					}
				}else {
					ctx.result("Invalid Role");
					ctx.status(HttpStatus.UNAUTHORIZED_401);
				}
			}else {
				ctx.result("Not Logged In");
				ctx.status(HttpStatus.UNAUTHORIZED_401);
			}
		});
		
		app.get("/pending", ctx -> {
			String username = ctx.cookieStore().get("username");
			if(username != null) {
				boolean manager = database.checkManager(username);
				if(manager) {
					List<Ticket> pendings = database.showPending();
					ctx.result(pendings.toString());
					ctx.status(HttpStatus.OK_200);
				}else {
					ctx.result("Invalid Role");
					ctx.status(HttpStatus.UNAUTHORIZED_401);
				}
			}else {
				ctx.result("Not Logged In");
				ctx.status(HttpStatus.UNAUTHORIZED_401);
			}
		});
		
		app.get("/approve/{id}", ctx -> {
			String username = ctx.cookieStore().get("username");
			if(username != null) {
				boolean manager = database.checkManager(username);
				if(manager) {
					int ticketID = Integer.parseInt(ctx.pathParam("id"));
					int updated = database.approve(ticketID);
					if(updated > 0) {
						ctx.result("Successfully Approved Ticket");
						ctx.status(HttpStatus.OK_200);
					}else {
						ctx.result("Ticket Already Processed");
						ctx.status(HttpStatus.OK_200);
					}
				}else {
					ctx.result("Invalid Role");
					ctx.status(HttpStatus.UNAUTHORIZED_401);
				}
			}else {
				ctx.result("Not Logged In");
				ctx.status(HttpStatus.UNAUTHORIZED_401);
			}
		});
		
		app.get("/deny/{id}", ctx -> {
			String username = ctx.cookieStore().get("username");
			if(username != null) {
				boolean manager = database.checkManager(username);
				if(manager) {
					int ticketID = Integer.parseInt(ctx.pathParam("id"));
					int updated = database.deny(ticketID);
					if(updated > 0) {
						ctx.result("Successfully Denied Ticket");
						ctx.status(HttpStatus.OK_200);
					}else {
						ctx.result("Ticket Already Processed");
						ctx.status(HttpStatus.OK_200);
					}
				}else {
					ctx.result("Invalid Role");
					ctx.status(HttpStatus.UNAUTHORIZED_401);
				}
			}else {
				ctx.result("Not Logged In");
				ctx.status(HttpStatus.UNAUTHORIZED_401);
			}
		});
		
		app.get("/view", ctx -> {
			String username = ctx.cookieStore().get("username");
			if(username != null) {
				boolean employee = database.checkEmployee(username);
				if(employee) {
					List<Ticket> processed = database.showProcessed(username);
					ctx.result(processed.toString());
					ctx.status(HttpStatus.OK_200);
				}else {
					ctx.result("Invalid Role");
					ctx.status(HttpStatus.UNAUTHORIZED_401);
				}
			}else {
				ctx.result("Not Logged In");
				ctx.status(HttpStatus.UNAUTHORIZED_401);
			}
		});
		
	}
}
