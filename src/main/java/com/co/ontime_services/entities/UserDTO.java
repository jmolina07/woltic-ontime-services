package com.co.ontime_services.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="user")
@NamedQueries({
	@NamedQuery(name="find_all_users", 
			query="SELECT u FROM UserDTO u"),
	@NamedQuery(name="find_user_by_email_password", 
			query="SELECT u FROM UserDTO u WHERE u.email = :emailParameter AND u.password = :passParameter")
})
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = -6458849171682812584L;

	@Id 
	@Column(name="USER_ID")
	private int user_id;
	
	@Column(name="USER_TYPE")
	private int user_type;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="LAST_NAME")
	private String last_name;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="CELLPHONE")
	private String cellphone;
	
//-----------------------------------------------------------------------------------
	@OneToMany(mappedBy = "user", targetEntity=CompanyDTO.class)
	@JsonBackReference(value="user-company")
	private Set<CompanyDTO> companies;
//-----------------------------------------------------------------------------------
	
//-----------------------------------------------------------------------------------
	@OneToMany(mappedBy = "client", targetEntity=DailyScheduleDTO.class)
	@JsonBackReference(value="schedule-client")
	//@JsonManagedReference(value="client-schedule")
    private Set<DailyScheduleDTO> schedule_c;
	
	@OneToMany(mappedBy = "professional", targetEntity=DailyScheduleDTO.class)
	@JsonBackReference(value="schedule-professional")
	//@JsonManagedReference(value="professional-schedule")
    private Set<DailyScheduleDTO> schedule_p;
//-----------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "user_has_branch_office", 
		joinColumns = @JoinColumn(name = "user_user_id", referencedColumnName = "USER_ID"), 
	    inverseJoinColumns = @JoinColumn(name = "branch_office_branch_id", referencedColumnName = "BRANCH_ID"))
	@JsonBackReference(value="professional-branch")
		private Collection<BranchOfficeDTO> branch_office;
		
		public void addBrach(BranchOfficeDTO branch) {
			if (!getBranch_office().contains(branch)) {
		    	getBranch_office().add(branch);
		    }
		    if (!branch.getProfessionals().contains(this)){
		    	branch.getProfessionals().add(this);
		    }
		}
		
		public void removeBrach(BranchOfficeDTO branch) {
			if (getBranch_office().contains(branch)) {
		    	getBranch_office().remove(branch);
		    }
		    if (branch.getProfessionals().contains(this)){
		    	branch.getProfessionals().remove(this);
		    }
		}
//---------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_has_services", 
		joinColumns = @JoinColumn(name = "user_user_id", referencedColumnName = "USER_ID"), 
		inverseJoinColumns = @JoinColumn(name = "services_service_id", referencedColumnName = "SERVICE_ID"))
	private Collection<ServicesDTO> services;
		
	public void addService(ServicesDTO services) {
		if (!getServices().contains(services)) {
			getServices().add(services);
		}
		if (!services.getProfessionals().contains(this)){
			services.getProfessionals().add(this);
		}
	}
		
	public void removeService(ServicesDTO services) {
		if (getServices().contains(services)) {
			getServices().remove(services);
		}
		if (services.getProfessionals().contains(this)){
		    services.getProfessionals().remove(this);
		}
	}
//---------------------------------------------------------------------------------

	public UserDTO() {
		/*super();*/
		/*branch_office = new ArrayList<BranchOfficeDTO>();
		services = new ArrayList<ServicesDTO>();*/
	}

	public UserDTO(int user_type, String name, String last_name, String email, String password, String cellphone) {
		super();
		this.user_type = user_type;
		this.name = name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.cellphone = cellphone;
	}

	public UserDTO(int user_id, int user_type, String name, String last_name, String email, String password, String cellphone) {
		super();
		this.user_id = user_id;
		this.user_type = user_type;
		this.name = name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
		this.cellphone = cellphone;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Collection<BranchOfficeDTO> getBranch_office() {
		return branch_office;
	}
	
	public void setBranch_office(Collection<BranchOfficeDTO> branch_office) {
		this.branch_office = branch_office;
	}
	
	public Collection<ServicesDTO> getServices() {
		return services;
	}

	public void setServices(Collection<ServicesDTO> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "UserDTO [user_id=" + user_id + ", user_type=" + user_type + ", name=" + name + ", last_name="
				+ last_name + ", email=" + email + ", password=" + password + ", cellphone=" + cellphone + "]";
	}
	
	
	
}
