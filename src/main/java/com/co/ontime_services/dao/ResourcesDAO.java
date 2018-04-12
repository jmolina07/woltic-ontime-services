package com.co.ontime_services.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.co.ontime_services.entities.BranchOfficeDTO;
import com.co.ontime_services.entities.ResourcesDTO;

@Repository
@Transactional
public class ResourcesDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<ResourcesDTO> findAll() {
		TypedQuery<ResourcesDTO> namedQuery = entityManager.createNamedQuery("find_all_resources", ResourcesDTO.class);
		return namedQuery.getResultList();
	}

	public ResourcesDTO findById(int id) {
		return entityManager.find(ResourcesDTO.class, id);
	}

	public ResourcesDTO update(ResourcesDTO resource) {
		ResourcesDTO resourceDTO = entityManager.find(ResourcesDTO.class, resource.getResources_id());
		resourceDTO.setName(resource.getName());
		resourceDTO.setDescription(resource.getDescription());
		resourceDTO.setQuantity(resource.getQuantity());
		resourceDTO.setPrice(resource.getPrice());
		return entityManager.merge(resourceDTO);
	}
	
	public ResourcesDTO findResourceID(ResourcesDTO re) {
		String query = "SELECT r FROM ResourcesDTO r WHERE r.name = :nameParameter";
		TypedQuery<ResourcesDTO> namedQuery = entityManager.createQuery(query, ResourcesDTO.class);
		namedQuery.setParameter("nameParameter", re.getName());
		return namedQuery.getSingleResult();
	}
	
	public ResourcesDTO addResourceToBranch(int branch_id , ResourcesDTO resource) {
		BranchOfficeDTO branch = entityManager.find(BranchOfficeDTO.class, branch_id);
		ResourcesDTO resource1 = entityManager.find(ResourcesDTO.class, resource.getResources_id());
		resource1.addBrach(branch);
		return entityManager.merge(resource1);
	}
	
	public ResourcesDTO removeResourceToBranch(int branch_id , int resource_id) {
		BranchOfficeDTO branch = entityManager.find(BranchOfficeDTO.class, branch_id);
		ResourcesDTO resource = entityManager.find(ResourcesDTO.class, resource_id);
		resource.removeBrach(branch);
		return entityManager.merge(resource);
	}

	public ResourcesDTO insert(int branch_id, ResourcesDTO resource) {
		entityManager.merge(resource);
		return addResourceToBranch(branch_id, findResourceID(resource));
	}
	
	public void delete(int resource_id) {
		ResourcesDTO resource = findById(resource_id);
		entityManager.remove(resource);
	}
}
