package com.co.ontime_services.controllers.extras;

import java.io.Serializable;

public class Service implements Serializable {

	private static final long serialVersionUID = -2387398226737943022L;
	
	private String name;
	
	private String description;
	
	private int duration;
	
	private double price;
	
	private double tax;
	
	private int branch_fk;
	
	private int service_fk;
	
	private int branch_service_id;

	public Service() {
		super();
	}
	
	public Service(String name, String description, int duration, double price, double tax, int branch_fk, int service_fk,
			int branch_service_id) {
		super();
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.price = price;
		this.tax = tax;
		this.branch_fk = branch_fk;
		this.branch_fk = service_fk;
		this.branch_service_id = branch_service_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
	
	public int getBranch_fk() {
		return branch_fk;
	}

	public void setBranch_id(int branch_fk) {
		this.branch_fk = branch_fk;
	}

	public int getService_fk() {
		return service_fk;
	}

	public void setService_fk(int service_fk) {
		this.service_fk = service_fk;
	}
	
	public int getBranch_service_id() {
		return branch_service_id;
	}

	public void setBranch_service_id(int branch_service_id) {
		this.branch_service_id = branch_service_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
