package com.co.ontime_services.dao;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.co.ontime_services.entities.BranchOfficeDTO;
import com.co.ontime_services.entities.CompanyDTO;
import com.co.ontime_services.entities.UserDTO;

@Repository
@Transactional
public class CompanyDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<CompanyDTO> findAll() {
		TypedQuery<CompanyDTO> namedQuery = entityManager.createNamedQuery("find_all_companies", CompanyDTO.class);
		return namedQuery.getResultList();
	}
	
	public CompanyDTO findCompanyByUser(int user_id) {
		String query = "SELECT c FROM CompanyDTO c WHERE c.user_fk = :userParameter";
		try {
			TypedQuery<CompanyDTO> namedQuery = entityManager.createQuery(query, CompanyDTO.class);
			namedQuery.setParameter("userParameter", user_id);
			return namedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<CompanyDTO> findAllForUser() {
		String sql = "SELECT co.name, co.email, co.description, co.phone, co.business_hours, co.latitude, co.longitude"
				+ " FROM CompanyDTO co";
		TypedQuery<CompanyDTO> namedQuery = entityManager.createQuery(sql, CompanyDTO.class);
		return namedQuery.getResultList();
	}

	public CompanyDTO findById(int id) {
		return entityManager.find(CompanyDTO.class, id);
	}

	public CompanyDTO update(CompanyDTO company) {
		return entityManager.merge(company);
	}
	
	public void insertCompanyAndBranch(CompanyDTO company) {
		entityManager.merge(company);
		Query query = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
		long value = ((BigInteger) query.getSingleResult()).longValue();
		Integer id = (int) (long) value;
		BranchOfficeDTO branch = new BranchOfficeDTO();
		branch.setName(company.getName());
		branch.setDescription(company.getDescription());
		branch.setEmail(company.getEmail());
		branch.setPhone(company.getPhone());
		branch.setCellphone(company.getCellphone());
		branch.setUser_fk(company.getUser_fk());
		branch.setCompany_fk(id);
		entityManager.merge(branch);
	}
	
	public void insertCompanyWithOutBranch(UserDTO user, CompanyDTO company, int ans, String wh) {
		entityManager.merge(user);
		int u_id = lastID();
		company.setHas_branch(false);
		company.setUser_fk(u_id);
		company.setBusiness_hours(wh);
		entityManager.merge(company);
		int c_id = lastID();
		BranchOfficeDTO branch = new BranchOfficeDTO();
		branch.setName(company.getName());
		branch.setDescription(company.getDescription());
		branch.setEmail(company.getEmail());
		branch.setPhone(company.getPhone());
		branch.setCellphone(company.getCellphone());
		branch.setUser_fk(company.getUser_fk());
		branch.setCompany_fk(c_id);
		branch.setBusiness_hours(wh);
		entityManager.merge(branch);
		if(ans == 1) {
			int b_id = lastID();
			System.out.println("ans: " + ans);
			System.out.println("branch_id: " + b_id);
			System.out.println("user_id: " + u_id);
			BranchOfficeDTO branch1 = entityManager.find(BranchOfficeDTO.class, b_id);
			UserDTO professional = entityManager.find(UserDTO.class, u_id);
			professional.setBusiness_hours(wh);
			professional.addBrach(branch1);
			entityManager.merge(professional);
		}
	}

	public CompanyDTO insert(CompanyDTO company) {
		return entityManager.merge(company);
	}

	public void deleteById(int id) {
		CompanyDTO company = findById(id);
		entityManager.remove(company);
	}
	
	public int lastID(){
		Query query = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
		long value = ((BigInteger) query.getSingleResult()).longValue();
		Integer id = (int) (long) value;
		return id;
	}

}
