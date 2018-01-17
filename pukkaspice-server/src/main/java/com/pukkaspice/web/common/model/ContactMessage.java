package com.pukkaspice.web.common.model;

public class ContactMessage {
	
	public String name;
	
	public String email;
	
	public String message;

	
	public ContactMessage() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(name);
	    sb.append("\n");
	    sb.append(email);
	    sb.append("\n");
	    sb.append(message);
	    
	    return sb.toString();
	}
	
}
