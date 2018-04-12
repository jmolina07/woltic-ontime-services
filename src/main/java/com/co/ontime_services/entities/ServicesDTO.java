package com.co.ontime_services.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="services")
@NamedQuery(name="find_all_services", query="SELECT se FROM ServicesDTO se")
public class ServicesDTO implements Serializable {

	private static final long serialVersionUID = -6226277774471368061L;
	
	@Id
	@Column(name="SERVICE_ID")
	private int service_id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
//---------------------------------------------------------------------------------------
	@OneToMany(mappedBy="service_id", targetEntity=BranchServicesDTO.class)
	@JsonBackReference(value="services-branch")
	private Set<BranchServicesDTO> branch_office;
//---------------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------------	
	@OneToMany(mappedBy="services", targetEntity=UserDTO.class)
	@JsonBackReference(value="services-professional")
	private Collection<UserDTO> professionals;
	
	public void addProfessional(UserDTO professional) {
	    if (!getProfessionals().contains(professional)) {
	    	getProfessionals().add(professional);
	    }
	    if (!professional.getServices().contains(this)) {
	    	professional.getServices().add(this);
	    }
	}
	
	public void removeProfessional(UserDTO professional) {
	    if (getProfessionals().contains(professional)) {
	    	getProfessionals().remove(professional);
	    }
	    if (professional.getServices().contains(this)) {
	    	professional.getServices().remove(this);
	    }
	}
		/*@WhereJoinTable(clause = "USER_TYPE ='3'")*/
//---------------------------------------------------------------------------------------
	
//---------------------------------------------------------------------------------------
	@OneToMany(mappedBy = "service", targetEntity=DailyScheduleDTO.class)
	@JsonBackReference(value="schedule-service")
	//@JsonManagedReference(value="client-schedule")
	private Set<DailyScheduleDTO> schedule;
//---------------------------------------------------------------------------------------

	public ServicesDTO() {
		professionals = new ArrayList<UserDTO>();
	}
	
		
	public ServicesDTO(int service_id, String name, String description) {
		super();
		this.service_id = service_id;
		this.name = name;
		this.description = description;
	}

	public ServicesDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//---------------------------------------------------------------------------------------
	
	public Set<BranchServicesDTO> getBranch_office() {
		return branch_office;
	}

	public void setBranch_office(Set<BranchServicesDTO> branch_office) {
		this.branch_office = branch_office;
	}

	public Collection<UserDTO> getProfessionals() {
		return professionals;
	}

	public void setProfessionals(Collection<UserDTO> professionals) {
		this.professionals = professionals;
	}
	
	@Override
	public String toString() {
		return "ServicesDTO [service_id=" + service_id + ", name=" + name + ", description=" + description + "]";
	}
	
}
