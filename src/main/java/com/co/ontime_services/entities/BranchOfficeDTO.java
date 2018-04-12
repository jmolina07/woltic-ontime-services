package com.co.ontime_services.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="branch_office")
@NamedQuery(name="find_all_branch_offices", query="SELECT bo FROM BranchOfficeDTO bo")
public class BranchOfficeDTO implements Serializable {

	private static final long serialVersionUID = -2781823844736787568L;
	
	@Id
	@Column(name="BRANCH_ID", unique = true, nullable = false)
	private int branch_id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="CELLPHONE")
	private String cellphone;
	
	@Column(name="BUSINESS_HOURS")
	private String business_hours;
	
	/*@Column(name="CONTACT_NAME")
	private String contact_name;

	@Column(name="CONTACT_EMAIL")
	private String contact_email;
	
	@Column(name="CONTACT_PASSWORD")
	private String contact_password;
	
	@Column(name="CONTACT_CELLPHONE")
	private String contact_cellphone;*/
	
	@Column(name="COMPANY_FK")
	private int company_fk;
	
	@Column(name="USER_FK")
	private int user_fk;
	
	@Column(name="LATITUDE")
	private String latitude;
	
	@Column(name="LONGITUDE")
	private String longitude;
	
//---------------------------------------------------------------------------------
	@JoinColumn(name="COMPANY_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=CompanyDTO.class)
	//@JsonManagedReference(value="branch-company")
    private CompanyDTO company;
//---------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------
	@OneToMany(mappedBy="branch_id", targetEntity=BranchServicesDTO.class)
	@JsonBackReference(value="branch-bs")
    private Set<BranchServicesDTO> branch_services;
//---------------------------------------------------------------------------------

//---------------------------------------------------------------------------------
	@ManyToMany(mappedBy="branch_office", targetEntity=UserDTO.class)
	//@JsonManagedReference(value="branch-professional")
    private Collection<UserDTO> professionals;
	
	public void addProfessional(UserDTO professional) {
	    if (!getProfessionals().contains(professional)) {
	    	getProfessionals().add(professional);
	    }
	    if (!professional.getBranch_office().contains(this)) {
	    	professional.getBranch_office().add(this);
	    }
	}
	
	public void removeProfessional(UserDTO professional) {
	    if (getProfessionals().contains(professional)) {
	    	getProfessionals().remove(professional);
	    }
	    if (professional.getBranch_office().contains(this)) {
	    	professional.getBranch_office().remove(this);
	    }
	}
//---------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------
	@ManyToMany(mappedBy = "branch_office", targetEntity=ResourcesDTO.class)
	//@JsonManagedReference(value="branch-resource")
	private Collection<ResourcesDTO> resources;
	
	public void addResource(ResourcesDTO resource) {
	    if (!getResources().contains(resource)) {
	    	getResources().add(resource);
	    }
	    if (!resource.getBranch_office().contains(this)) {
	    	resource.getBranch_office().add(this);
	    }
	}
	
	public void removeResource(ResourcesDTO resource) {
	    if (getResources().contains(resource)) {
	    	getResources().remove(resource);
	    }
	    if (resource.getBranch_office().contains(this)) {
	    	resource.getBranch_office().remove(this);
	    }
	}
//---------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------
	@OneToMany(mappedBy = "branch", targetEntity=DailyScheduleDTO.class)
	@JsonBackReference(value="schedule-branch")
	//@JsonManagedReference(value="branch-schedule")
    private Set<DailyScheduleDTO> schedule;
//---------------------------------------------------------------------------------

	public BranchOfficeDTO() {
		/*professionals = new ArrayList<ProfessionalDTO>();*/
		resources = new ArrayList<ResourcesDTO>();
	}
	
	public BranchOfficeDTO(String name, String description, String email, String phone, String cellphone,
		String business_hours, int company_fk, int user_fk, String latitude, String longitude) {
		super();
		this.name = name;
		this.description = description;
		this.email = email;
		this.phone = phone;
		this.cellphone = cellphone;
		this.business_hours = business_hours;
		this.company_fk = company_fk;
		this.user_fk = user_fk;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public BranchOfficeDTO(int branch_id, String name, String description, String email, String phone, String cellphone,
			String business_hours, int company_fk, int user_fk, String latitude, String longitude) {
		super();
		this.branch_id = branch_id;
		this.name = name;
		this.description = description;
		this.email = email;
		this.phone = phone;
		this.cellphone = cellphone;
		this.business_hours = business_hours;
		this.company_fk = company_fk;
		this.user_fk = user_fk;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_office_id) {
		this.branch_id = branch_office_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBusiness_hours() {
		return business_hours;
	}

	public void setBusiness_hours(String business_hours) {
		this.business_hours = business_hours;
	}

	public int getCompany_fk() {
		return company_fk;
	}
	
	public int getUser_fk() {
		return user_fk;
	}

	public void setUser_fk(int user_fk) {
		this.user_fk = user_fk;
	}

	public void setCompany_fk(int company_fk) {
		this.company_fk = company_fk;
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
	
	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public Set<BranchServicesDTO> getBranch_services() {
		return branch_services;
	}

	public void setBranch_services(Set<BranchServicesDTO> branch_services) {
		this.branch_services = branch_services;
	}
	
	public Collection<UserDTO> getProfessionals() {
		return professionals;
	}

	public void setProfessionals(Collection<UserDTO> professionals) {
		this.professionals = professionals;
	}

	public Collection<ResourcesDTO> getResources() {
		return resources;
	}

	public void setResources(Collection<ResourcesDTO> resources) {
		this.resources = resources;
	}
	
	public Set<DailyScheduleDTO> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<DailyScheduleDTO> schedule) {
		this.schedule = schedule;
	}

	
	
	
}
