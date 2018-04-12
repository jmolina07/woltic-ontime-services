package com.co.ontime_services.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="company")
@JsonRootName("company")
@NamedQuery(name="find_all_companies", query="SELECT co FROM CompanyDTO co")
public class CompanyDTO implements Serializable{

	private static final long serialVersionUID = -5331862166698534635L;
	
	@Id
	@Column(name="COMPANY_ID")
	private int company_id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="CELLPHONE")
	private String cellphone;
	
	@Column(name="HAS_BRANCH")
	private boolean has_branch;
	
	@Column(name="USER_FK")
	private int user_fk;
	
	@Column(name="BUSINESS_HOURS")
	private String business_hours;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Column(name="LONGITUDE")
	private String longitude;
	
//-----------------------------------------------------------------------------------
	@OneToMany(mappedBy = "company", targetEntity=BranchOfficeDTO.class)
	@JsonBackReference(value="company-branch")
    private Set<BranchOfficeDTO> branch_offices;
//-----------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------
	@JoinColumn(name="USER_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=UserDTO.class)
    private UserDTO user;
//-----------------------------------------------------------------------------------
	
	public CompanyDTO() {
		
	}
	
	public CompanyDTO(int company_id, String name, String email, String description, String phone, String cellphone,
			boolean has_branch, int user_fk, String business_hours, String latitude, String longitude) {
		super();
		this.company_id = company_id;
		this.name = name;
		this.email = email;
		this.description = description;
		this.phone = phone;
		this.cellphone = cellphone;
		this.has_branch = has_branch;
		this.user_fk = user_fk;
		this.business_hours = business_hours;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public CompanyDTO(String name, String email, String description, String phone, String cellphone, boolean has_branch,
			int user_fk, String business_hours, String latitude, String longitude) {
		super();
		this.name = name;
		this.email = email;
		this.description = description;
		this.phone = phone;
		this.cellphone = cellphone;
		this.has_branch = has_branch;
		this.user_fk = user_fk;
		this.business_hours = business_hours;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public boolean isHas_branch() {
		return has_branch;
	}

	public void setHas_branch(boolean has_branch) {
		this.has_branch = has_branch;
	}

	public int getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(int user_fk) {
		this.user_fk = user_fk;
	}

	public String getBusiness_hours() {
		return business_hours;
	}

	public void setBusiness_hours(String business_hours) {
		this.business_hours = business_hours;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//----------------------------------------------------------------------------

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Set<BranchOfficeDTO> getBranch_offices() {
		return branch_offices;
	}

	public void setBranch_offices(Set<BranchOfficeDTO> branch_offices) {
		this.branch_offices = branch_offices;
	}
	
	
}
