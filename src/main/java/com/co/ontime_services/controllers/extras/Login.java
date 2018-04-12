package com.co.ontime_services.controllers.extras;

import java.io.Serializable;

public class Login implements Serializable {
	
	private static final long serialVersionUID = 8238493151829750356L;

	private String email;
	
	private String password;
	
	public Login() {
		
	}
	
	public Login(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
