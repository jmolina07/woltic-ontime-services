package com.co.ontime_services.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.co.ontime_services.entities.BranchOfficeDTO;;

@Repository
@Transactional
public class BranchOfficeDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<BranchOfficeDTO> findAll() {
		TypedQuery<BranchOfficeDTO> namedQuery = entityManager.createNamedQuery("find_all_branch_offices", BranchOfficeDTO.class);
		return namedQuery.getResultList();
	}
	
	public List<BranchOfficeDTO> findAllByCompanyID(int id) {
		String sql = "SELECT bs FROM BranchOfficeDTO bs WHERE bs.company_fk = " + id;
		TypedQuery<BranchOfficeDTO> namedQuery = entityManager.createQuery(sql, BranchOfficeDTO.class);
		return namedQuery.getResultList();
	}
	
	public BranchOfficeDTO findBranchByUser(int user_type) {
		String query = "SELECT p FROM BranchOfficeDTO p WHERE p.user_fk = :userParameter";
		try {
			TypedQuery<BranchOfficeDTO> namedQuery = entityManager.createQuery(query, BranchOfficeDTO.class);
			namedQuery.setParameter("userParameter", user_type);
			return namedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public BranchOfficeDTO findProfessionalID(BranchOfficeDTO branch) {
		String query = "SELECT p FROM ProfessionalDTO p WHERE p.email = :emailParameter AND p.password = :passParameter";
		TypedQuery<BranchOfficeDTO> namedQuery = entityManager.createQuery(query, BranchOfficeDTO.class);
		namedQuery.setParameter("emailParameter", ""/*branch.getContact_email()*/);
		namedQuery.setParameter("passParameter", ""/*branch.getContact_password()*/);
		return namedQuery.getSingleResult();
	}

	public BranchOfficeDTO findById(int id) {
		return entityManager.find(BranchOfficeDTO.class, id);
	}

	public BranchOfficeDTO update(BranchOfficeDTO branch) {
		return entityManager.merge(branch);
	}

	public BranchOfficeDTO insert(BranchOfficeDTO branch) {
		return entityManager.merge(branch);
	}

	public void deleteById(int id) {
		BranchOfficeDTO branch = findById(id);
		entityManager.remove(branch);
	}

}
