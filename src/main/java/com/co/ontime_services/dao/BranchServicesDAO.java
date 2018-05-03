package com.co.ontime_services.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.co.ontime_services.entities.BranchServicesDTO;

@Repository
@Transactional
public class BranchServicesDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public BranchServicesDTO findByBranchIdServicesId(int branch_fk, int service_fk) {
		String sql = 
				"SELECT bs FROM BranchServicesDTO bs WHERE bs.branch_fk = " + branch_fk +" AND bs.service_fk = " + service_fk;
		TypedQuery<BranchServicesDTO> namedQuery = entityManager.createQuery(sql, BranchServicesDTO.class);
		return namedQuery.getSingleResult();
	}
	
	public List<BranchServicesDTO> findAllByBranchID(int id) {
		String sql = "SELECT bs FROM BranchServicesDTO bs WHERE bs.branch_fk = " + id;
		TypedQuery<BranchServicesDTO> namedQuery = entityManager.createQuery(sql, BranchServicesDTO.class);
		return namedQuery.getResultList();
	}
	
	public List<BranchServicesDTO> findAllByServiceID(int id) {
		String sql = "SELECT bs FROM BranchServicesDTO bs WHERE bs.service_fk = " + id;
		TypedQuery<BranchServicesDTO> namedQuery = entityManager.createQuery(sql, BranchServicesDTO.class);
		return namedQuery.getResultList();
	}
	
	public List<BranchServicesDTO> findAll() {
		TypedQuery<BranchServicesDTO> namedQuery = entityManager.createNamedQuery("find_all_branch_services", BranchServicesDTO.class);
		return namedQuery.getResultList();
	}

	public BranchServicesDTO findById(int id) {
		return entityManager.find(BranchServicesDTO.class, id);
	}

	public BranchServicesDTO update(BranchServicesDTO branch) {
		return entityManager.merge(branch);
	}

	public BranchServicesDTO insert(BranchServicesDTO branch) {
		return entityManager.merge(branch);
	}

	public void deleteById(int id) {
		BranchServicesDTO branch = findById(id);
		entityManager.remove(branch);
	}

}
