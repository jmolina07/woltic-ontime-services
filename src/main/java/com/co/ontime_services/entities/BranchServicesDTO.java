package com.co.ontime_services.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="branch_services")
@NamedQuery(name="find_all_branch_services", query="SELECT bs FROM BranchServicesDTO bs")
public class BranchServicesDTO implements Serializable{

	private static final long serialVersionUID = 8992743560356040328L;
	
	@Id
	@Column(name="BRANCH_SERVICE_ID")
	private int branch_service_id;
	
	/*@EmbeddedId
	private BranchServicesPK bs_id;*/
	
	/*@ManyToOne(cascade = CascadeType.ALL, targetEntity=BranchOfficeDTO.class)
	@JoinColumn(name="BRANCH_FK")
	private BranchOfficeDTO branch_id;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity=ServicesDTO.class)
	@JoinColumn(name="SERVICE_FK")
	private ServicesDTO service_id;*/
	
	@Column(name="BRANCH_FK")
	private int branch_fk;
	
	@Column(name="SERVICE_FK")
	private int service_fk;
	
	@Column(name="PRICE")
	private double price;
	
	@Column(name="TAX_PRICE")
	private double tax_price;
	
	@Column(name="DURATION_MINUTES")
	private int duration_minutes;
	
//---------------------------------------------------------------------------------
	@JoinColumn(name="BRANCH_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=BranchOfficeDTO.class)
	//@JsonManagedReference(value="bs-branch")
	private BranchOfficeDTO branch_id;
	
	@JoinColumn(name="SERVICE_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=ServicesDTO.class)
	//@JsonManagedReference(value="bs-service")
	private ServicesDTO service_id;	
//---------------------------------------------------------------------------------
	
	public BranchServicesDTO() {
		
	}
	
	
	/*public BranchServicesDTO(BranchOfficeDTO branch_id, ServicesDTO service_id, double price, double tax_price,
		int duration_minutes) {
		super();
		this.branch_id = branch_id;
		this.service_id = service_id;
		this.price = price;
		this.tax_price = tax_price;
		this.duration_minutes = duration_minutes;
	}*/
	
	public BranchServicesDTO(int branch_fk, int service_fk, double price, double tax_price, int duration_minutes) {
		this.branch_fk = branch_fk;
		this.service_fk = service_fk;
		this.price = price;
		this.tax_price = tax_price;
		this.duration_minutes = duration_minutes;
	}

	public BranchServicesDTO(int branch_service_id, int branch_fk, int service_fk, double price, double tax_price,
			int duration_minutes) {
		super();
		this.branch_service_id = branch_service_id;
		this.branch_fk = branch_fk;
		this.service_fk = service_fk;
		this.price = price;
		this.tax_price = tax_price;
		this.duration_minutes = duration_minutes;
	}


	/*public BranchServicesDTO(int branch_service_id, BranchOfficeDTO branch_id, ServicesDTO service_id, double price,
			double tax_price, int duration_minutes) {
		super();
		this.branch_service_id = branch_service_id;
		this.branch_id = branch_id;
		this.service_id = service_id;
		this.price = price;
		this.tax_price = tax_price;
		this.duration_minutes = duration_minutes;
	}


	public BranchOfficeDTO getBranch_id() {
		return branch_id;
	}


	public void setBranch_id(BranchOfficeDTO branch_id) {
		this.branch_id = branch_id;
	}


	public ServicesDTO getService_id() {
		return service_id;
	}


	public void setService_id(ServicesDTO service_id) {
		this.service_id = service_id;
	}*/


	public int getBranch_service_id() {
		return branch_service_id;
	}

	public void setBranch_service_id(int branch_services_id) {
		this.branch_service_id = branch_services_id;
	}
	
	public int getBranch_fk() {
		return branch_fk;
	}

	public void setBranch_fk(int branch_fk) {
		this.branch_fk = branch_fk;
	}

	public int getService_fk() {
		return service_fk;
	}

	public void setService_fk(int service_fk) {
		this.service_fk = service_fk;
	}

	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTax_price() {
		return tax_price;
	}

	public void setTax_price(double tax_price) {
		this.tax_price = tax_price;
	}

	public int getDuration_minutes() {
		return duration_minutes;
	}

	public void setDuration_minutes(int duration_minutes) {
		this.duration_minutes = duration_minutes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
//---------------------------------------------------------------------------------

	public BranchOfficeDTO getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(BranchOfficeDTO branch_id) {
		this.branch_id = branch_id;
	}

	public ServicesDTO getService_id() {
		return service_id;
	}

	public void setService_id(ServicesDTO service_id) {
		this.service_id = service_id;
	}


	@Override
	public String toString() {
		return "BranchServicesDTO [branch_service_id=" + branch_service_id + ", branch_fk=" + branch_fk
				+ ", service_fk=" + service_fk + ", price=" + price + ", tax_price=" + tax_price + ", duration_minutes="
				+ duration_minutes + "]";
	}
	
	

}
