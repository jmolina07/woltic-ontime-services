package com.co.ontime_services.dao;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import com.co.ontime_services.entities.DailyScheduleDTO;
import com.co.ontime_services.entities.UserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Repository
@Transactional
public class DailyScheduleDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<DailyScheduleDTO> findAll() {
		TypedQuery<DailyScheduleDTO> namedQuery = entityManager.createNamedQuery("find_all_schedules", DailyScheduleDTO.class);
		return namedQuery.getResultList();
	}
	
	public List<DailyScheduleDTO> findAllByBranchID(int id) {
		String sql = "SELECT ds FROM DailyScheduleDTO ds WHERE ds.branch_fk = " + id + 
				"ORDER BY ds.date, ds.initial_hour";
		TypedQuery<DailyScheduleDTO> namedQuery = entityManager.createQuery(sql, DailyScheduleDTO.class);
		return namedQuery.getResultList();
	}
	
	public List<DailyScheduleDTO> findAllByProfessionalID(int id) {
		String sql = "SELECT ds FROM DailyScheduleDTO ds WHERE ds.professional_fk = " + id +
				"ORDER BY ds.date, ds.initial_hour";
		TypedQuery<DailyScheduleDTO> namedQuery = entityManager.createQuery(sql, DailyScheduleDTO.class);
		return namedQuery.getResultList();
	}
	
	public List<DailyScheduleDTO> findAllByDate(String date) {
		//String sql = "SELECT ds FROM DailyScheduleDTO ds WHERE ds.date = CURDATE()";
		String sql = "SELECT ds FROM DailyScheduleDTO ds WHERE ds.date = " + date;
		TypedQuery<DailyScheduleDTO> namedQuery = entityManager.createQuery(sql, DailyScheduleDTO.class);
		return namedQuery.getResultList();
	} 
	
	public List<DailyScheduleDTO> findAllByClientID(int id) {
		String sql = "SELECT ds FROM DailyScheduleDTO ds WHERE ds.client_fk = " + id +
				"ORDER BY ds.date, ds.initial_hour";
		TypedQuery<DailyScheduleDTO> namedQuery = entityManager.createQuery(sql, DailyScheduleDTO.class);
		return namedQuery.getResultList();
	}
	
	public DailyScheduleDTO findById(int id) {
		return entityManager.find(DailyScheduleDTO.class, id);
	}

	public DailyScheduleDTO update(DailyScheduleDTO schedule) {
		return entityManager.merge(schedule);
	}

	public DailyScheduleDTO insert(DailyScheduleDTO schedule) {
		return entityManager.merge(schedule);
	}

	public void deleteById(int id) {
		DailyScheduleDTO schedule = findById(id);
		entityManager.remove(schedule);
	}
	
	public List<String> AppointmentSlots(int id, int duration, String date) throws Exception {
		
		String morningStartDayHour="";
		String morningEndDayHour="";
		String AfternoonStartDayHour="";
		String AfternoonEndDayHour="";
		
		UserDTO userDTO = entityManager.find(UserDTO.class, id);
		
		List<String> jsonlist = split(userDTO.getBusiness_hours());
		JSONParser parser = new JSONParser();

		//int today = LocalDate.now().getDayOfWeek();
		int today = LocalDate.parse(date).getDayOfWeek();
		
		for (int i = 0; i < jsonlist.size(); i++) {
			JSONObject json = (JSONObject) parser.parse(jsonlist.get(i));
			int day = Integer.parseInt((String) json.get("day"));
			
			if(day == today) {
				if ((Boolean) json.get("break")) {
					morningStartDayHour = (String) json.get("initial");
					morningEndDayHour = (String) json.get("initial1");
					AfternoonStartDayHour = (String) json.get("end1");
					AfternoonEndDayHour = (String) json.get("end");
				}
			}		
		}
		
		if(morningStartDayHour == "") {
			List<String> resp = new ArrayList<String>();
			resp.add("Hoy no hay citas disponibles");
			return resp;
		}
		
		
		System.out.println(morningStartDayHour + " - " + morningEndDayHour + 
				" y " + AfternoonStartDayHour + " - " + AfternoonEndDayHour );
		//System.out.println(json.get("day") + " and " + today);
		
		List<DailyScheduleDTO> list = findAllByDate(date);
		DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
		List<String> hourslist = new ArrayList<String>();
				
		LocalTime now = LocalTime.now();
		//LocalTime now = formatter.parseLocalTime("09:00");
		LocalTime morning = formatter.parseLocalTime(morningStartDayHour);
		LocalTime Noon = formatter.parseLocalTime(morningEndDayHour);
		LocalTime afternoon = formatter.parseLocalTime(AfternoonStartDayHour);
		LocalTime evening = formatter.parseLocalTime(AfternoonEndDayHour);
				
		LocalTime early = null;
		LocalTime late = null;
		LocalTime late2 = null;
				
		Boolean isMorning = (now.isAfter(morning) && now.isBefore(Noon));
		Boolean isMiddle = (now.isAfter(Noon) && now.isBefore(afternoon));
		Boolean isAfternoon = (now.isAfter(afternoon) && now.isBefore(evening));
				
		LocalTime time1 = null;
				
		if(isMorning) {
			if(now.isAfter(morning)) {
				early = now;
			}else {
				early = morning;
			}
		}
				
		if(isMiddle) {
			early = afternoon;
		}
				
		if(isAfternoon) {
			if(now.isAfter(afternoon) ) {
				early = now;
			}else {
				early = afternoon;
			}
		}
				
		if(list.size()==0) {
			time1=now;
			while (time1.isBefore(evening)) {
				if((time1.isEqual(morning))|| (time1.isAfter(morning) && time1.isBefore(Noon))) {
					hourslist.add(time1.toString());
					time1 = time1.plusMinutes(duration);
					if(time1.isEqual(Noon) || time1.isAfter(Noon)) {
						time1=afternoon;
					}
				}
						
				if((time1.isEqual(afternoon))||(time1.isAfter(afternoon) && time1.isBefore(evening))) {
					hourslist.add(time1.toString());
					time1 = time1.plusMinutes(duration);
					if(time1.isEqual(evening) || time1.isAfter(evening)) {
						break;
					}
				}
			}
		}else {
			for (int i = 0; i < list.size(); i++) {
				late = formatter.parseLocalTime(list.get(i).getInitial_hour());
				Minutes minutes = Minutes.minutesBetween(early, late);
				if(minutes.getMinutes() >= duration) {
					LocalTime time = early;
					for (int j = 0; j < (minutes.getMinutes()/duration); j++) {
						if (time.isAfter(morning) && time.isBefore(Noon)) {
							if (time.isBefore(Noon)) {
									hourslist.add(time.toString());
									time = time.plusMinutes(duration);
							}
						}
						if (early.isAfter(afternoon) && early.isBefore(evening)) {
							if(late2==null) {
								Minutes minutes2 = Minutes.minutesBetween(afternoon, time1);
								if(minutes2.getMinutes() >= duration) {
									time = afternoon;
									for (int k = 0; k < (minutes2.getMinutes()/duration); k++) {
										if (time.isBefore(time1)) {
											hourslist.add(time.toString());
											time = time.plusMinutes(duration);
										}
									}
									late2=time;
								}else {
									late2=time;
								}
							}			
							if (early.isBefore(evening)) {
								hourslist.add(early.toString());
								early = early.plusMinutes(duration);
							}
						}
					}
					early = formatter.parseLocalTime(list.get(i).getFinal_hour());
					time1 = formatter.parseLocalTime(list.get(i).getInitial_hour());
				}else {
					early = formatter.parseLocalTime(list.get(i).getFinal_hour());
				}	
			}
			if(early.isBefore(evening)) {
				Minutes minutes = Minutes.minutesBetween(late, early);
				if(minutes.getMinutes() >= duration) {
					LocalTime time = early;
					for (int j = 0; j < (minutes.getMinutes()/duration); j++) {
						if(early.isBefore(evening)) {
							hourslist.add(time.toString());
							time = time.plusMinutes(duration);
						}
					}
				}
			}
		}
		return hourslist;
	}
	
	public List<String> split(String jsonArray) throws Exception {
        List<String> splittedJsonElements = new ArrayList<String>();
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jsonNode = jsonMapper.readTree(jsonArray);

        if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode individualElement = arrayNode.get(i);
                splittedJsonElements.add(individualElement.toString());
            }
        }
        return splittedJsonElements;
	}

}
