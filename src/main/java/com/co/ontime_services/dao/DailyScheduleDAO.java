package com.co.ontime_services.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.co.ontime_services.entities.DailyScheduleDTO;

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

}
