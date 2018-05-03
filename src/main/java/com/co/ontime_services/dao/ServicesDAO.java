package com.co.ontime_services.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;

import com.co.ontime_services.entities.BranchServicesDTO;
import com.co.ontime_services.entities.ServicesDTO;;

@Repository
@Transactional
public class ServicesDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<ServicesDTO> findAll() {
		TypedQuery<ServicesDTO> namedQuery = entityManager.createNamedQuery("find_all_services", ServicesDTO.class);
		return namedQuery.getResultList();
	}

	public ServicesDTO findById(int id) {
		return entityManager.find(ServicesDTO.class, id);
	}

	public void update(ServicesDTO service, BranchServicesDTO bs) {
		entityManager.merge(service);
		entityManager.merge(bs);
	}

	public void insert(ServicesDTO service, BranchServicesDTO bs) {
		entityManager.merge(service);
		Query query = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
		long value = ((BigInteger) query.getSingleResult()).longValue();
		Integer id = (int) (long) value;
		bs.setService_fk(id);
		entityManager.merge(bs);
	}

	public void deleteById(int service_id) {
		ServicesDTO service = findById(service_id);
		entityManager.remove(service);
	}
	
	public void delete(int bs_id, int service_id) {
		BranchServicesDTO branchServicesDTO = entityManager.find(BranchServicesDTO.class, bs_id);
		entityManager.remove(branchServicesDTO);
		ServicesDTO service = findById(service_id);
		entityManager.remove(service);
	}


}
