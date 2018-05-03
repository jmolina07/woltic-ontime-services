package com.co.ontime_services.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="daily_schedule")
@NamedQuery(name="find_all_schedules", query="SELECT ds FROM DailyScheduleDTO ds")
public class DailyScheduleDTO implements Serializable{

	private static final long serialVersionUID = 5207873768523158371L;
	
	@Id 
	@Column(name="SCHEDULE_ID")
	private int schedule_id;
	
	@Column(name="CLIENT_FK")
	private int client_fk;
	
	@Column(name="BRANCH_FK")
	private int branch_fk;
	
	@Column(name="SERVICE_FK")
	private int service_fk;
	
	@Column(name="PROFESSIONAL_FK")
	private int professional_fk;
	
	@Column(name="DATE")
	private java.sql.Date date;
	
	@Column(name = "INITIAL_HOUR")
	private String initial_hour;
	
	@Column(name = "FINAL_HOUR")
	private String final_hour;
	
	/*java.sql.Time*/ 
	
	@Column(name="TURN")
	private int turn;
	
	@Column(name="STATE")
	private int state;
	
//------------------------------------------------------------------------------------------------------------------
	@JoinColumn(name="CLIENT_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=UserDTO.class)
	//@JsonBackReference(value="schedule-client")
    private UserDTO client;
	
	@JoinColumn(name="BRANCH_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=BranchOfficeDTO.class)
	//@JsonBackReference(value="schedule-branch")
    private BranchOfficeDTO branch;
	
	@JoinColumn(name="SERVICE_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=ServicesDTO.class)
	//@JsonBackReference(value="schedule-service")
    private ServicesDTO service;
	
	@JoinColumn(name="PROFESSIONAL_FK", insertable = false, updatable = false)
	@ManyToOne(targetEntity=UserDTO.class)
	//@JsonBackReference(value="schedule-professional")
    private UserDTO professional;
	
//------------------------------------------------------------------------------------------------------------------

	public DailyScheduleDTO() {
		
	}
	
	public DailyScheduleDTO(int schedule_id, int client_fk, int branch_fk, int service_fk, int professional_fk, 
			Date date, String initial_hour, String final_hour, int turn, int state) {
		super();
		this.schedule_id = schedule_id;
		this.client_fk = client_fk;
		this.branch_fk = branch_fk;
		this.service_fk = service_fk;
		this.professional_fk = professional_fk;
		this.date = date;
		this.initial_hour = initial_hour;
		this.final_hour = final_hour;
		this.turn = turn;
		this.state = state;
	}

	public DailyScheduleDTO(int client_fk, int branch_fk, int service_fk, int professional_fk,
			Date date, String initial_hour, String final_hour, int turn, int state) {
		super();
		this.client_fk = client_fk;
		this.branch_fk = branch_fk;
		this.service_fk = service_fk;
		this.professional_fk = professional_fk;
		this.date = date;
		this.initial_hour = initial_hour;
		this.final_hour = final_hour;
		this.turn = turn;
		this.state = state;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}

	public int getClient_fk() {
		return client_fk;
	}

	public void setClient_fk(int client_fk) {
		this.client_fk = client_fk;
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

	public int getProfessional_fk() {
		return professional_fk;
	}

	public void setProfessional_fk(int professional_fk) {
		this.professional_fk = professional_fk;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public String getInitial_hour() {
		return initial_hour;
	}

	public void setInitial_hour(String initial_hour) {
		this.initial_hour = initial_hour;
	}

	public String getFinal_hour() {
		return final_hour;
	}

	public void setFinal_hour(String final_hour) {
		this.final_hour = final_hour;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
//--------------------------------------------------------------------------------------------------------

	public BranchOfficeDTO getBranch() {
		return branch;
	}

	public void setBranch(BranchOfficeDTO branch) {
		this.branch = branch;
	}

	public UserDTO getClient() {
		return client;
	}

	public void setClient(UserDTO client) {
		this.client = client;
	}

	public UserDTO getProfessional() {
		return professional;
	}

	public void setProfessional(UserDTO professional) {
		this.professional = professional;
	}

	public ServicesDTO getService() {
		return service;
	}

	public void setService(ServicesDTO service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return "DailyScheduleDTO [schedule_id=" + schedule_id + ", client_fk=" + client_fk + ", branch_fk=" + branch_fk
				+ ", service_fk=" + service_fk + ", professional_fk=" + professional_fk
				+ ", date=" + date + ", initial_hour=" + initial_hour + ", final_hour=" + final_hour + ", turn=" + turn
				+ ", state=" + state + "]";
	}	
}
