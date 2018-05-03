package com.co.ontime_services.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="resources")
@NamedQuery(name="find_all_resources", query="SELECT r FROM ResourcesDTO r")
public class ResourcesDTO implements Serializable {

	private static final long serialVersionUID = -2134677283881033658L;
	
	@Id
	@Column(name="RESOURCES_ID")
	private int resources_id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="QUANTITY")
	private int quantity;
	
	@Column(name="PRICE")
	private String price;
	
//---------------------------------------------------------------------------------
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "branch_office_has_resources", 
	    joinColumns = @JoinColumn(name = "resources_resources_id", referencedColumnName = "RESOURCES_ID"), 
	    inverseJoinColumns = @JoinColumn(name = "branch_office_branch_id", referencedColumnName = "BRANCH_ID"))
	@JsonBackReference(value="resource-branch")
	private Collection<BranchOfficeDTO> branch_office;
	
	public void addBrach(BranchOfficeDTO branch) {
		if (!getBranch_office().contains(branch)) {
	    	getBranch_office().add(branch);
	    }
	    if (!branch.getResources().contains(this)){
	    	branch.getResources().add(this);
	    }
	}
	
	public void removeBrach(BranchOfficeDTO branch) {
		if (getBranch_office().contains(branch)) {
	    	getBranch_office().remove(branch);
	    }
	    if (branch.getResources().contains(this)){
	    	branch.getResources().remove(this);
	    }
	}
//---------------------------------------------------------------------------------
	
	public ResourcesDTO() {
		branch_office = new ArrayList<BranchOfficeDTO>();
	}
	
	public ResourcesDTO(int resources_id, String name, String description, int quantity, String price) {
		super();
		this.resources_id = resources_id;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
}

	public ResourcesDTO(String name, String description, int quantity, String price) {
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}

	public int getResources_id() {
		return resources_id;
	}

	public void setResources_id(int resources_id) {
		this.resources_id = resources_id;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//---------------------------------------------------------------------------------
	
	public Collection<BranchOfficeDTO> getBranch_office() {
		return branch_office;
	}

	public void setBranch_office(Collection<BranchOfficeDTO> branch_office) {
		this.branch_office = branch_office;
	}
	
	

	
	
	
	
	
}
