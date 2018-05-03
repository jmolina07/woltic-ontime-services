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
import com.co.ontime_services.entities.UserDTO;

@Repository
@Transactional
public class UserDAO {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public UserDTO findById(int id) {
		try {
			return entityManager.find(UserDTO.class, id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<UserDTO> findAll() {
		try {
			TypedQuery<UserDTO> namedQuery = entityManager.createNamedQuery("find_all_users", UserDTO.class);
			return namedQuery.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public UserDTO findByEP(String email, String password){
		try {
			TypedQuery<UserDTO> namedQuery = entityManager.createNamedQuery("find_user_by_email_password", UserDTO.class);
			namedQuery.setParameter("emailParameter", email);
			namedQuery.setParameter("passParameter", password);
			return namedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public UserDTO insertProfessional(int branch_id, UserDTO user) {
		try {
			entityManager.merge(user);
			int professional_id = getLastID();
			return addProfessionalToBranch(branch_id, professional_id);	
		} catch (Exception e) {
			return null;
		}	
	}
	
	public UserDTO insertUser(UserDTO user) {
		try {
			return entityManager.merge(user);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public UserDTO addProfessionalToBranch(int branch_id , int professional_id) {
		try {
			BranchOfficeDTO branch = entityManager.find(BranchOfficeDTO.class, branch_id);
			UserDTO professional = entityManager.find(UserDTO.class, professional_id);
			professional.addBrach(branch);
			return entityManager.merge(professional);
		} catch (Exception e) {
			return null;
		}
	}
	
	public UserDTO removeProfessionalOfBranch(int branch_id , int professional_id) {
		try {
			BranchOfficeDTO branch = entityManager.find(BranchOfficeDTO.class, branch_id);
			UserDTO professional = entityManager.find(UserDTO.class, professional_id);
			professional.removeBrach(branch);
			return entityManager.merge(professional);
		} catch (Exception e) {
			return null;
		}	
	}
	
	public int getLastID() {
		try {
			Query query = entityManager.createNativeQuery("SELECT LAST_INSERT_ID()");
			long value = ((BigInteger) query.getSingleResult()).longValue();
			Integer id = (int) (long) value;
			return id;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public UserDTO update(UserDTO user) {
		try {
			UserDTO userDTO = entityManager.find(UserDTO.class , user.getUser_id());
			userDTO.setName(user.getName());
			userDTO.setLast_name(user.getLast_name());
			userDTO.setEmail(user.getEmail());
			userDTO.setPassword(user.getPassword());
			userDTO.setCellphone(user.getCellphone());
			userDTO.setBusiness_hours(user.getBusiness_hours());
			return entityManager.merge(userDTO);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void deleteUser(int id) {
		UserDTO user = findById(id);
		entityManager.remove(user);
	}
	
	public void deleteProfessional(int branch_id , int professional_id) {
		try {
			removeProfessionalOfBranch(branch_id, professional_id);
			UserDTO professional = findById(professional_id);
			entityManager.remove(professional);
		} catch (Exception e) {
			System.out.println(e);
		}	
	}
}
