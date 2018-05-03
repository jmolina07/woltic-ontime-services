package com.co.ontime_services.controllers;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.co.ontime_services.controllers.extras.ErrorResponse;
import com.co.ontime_services.controllers.extras.Response;
import com.co.ontime_services.dao.BranchOfficeDAO;
import com.co.ontime_services.entities.BranchOfficeDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
public class BranchOfficeController {
	
	@Autowired
	BranchOfficeDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;
	
	@GetMapping(path="api/branch/{id}")
	public ResponseEntity<Response> getSucursalesById(@PathVariable("id") int id){
		try {
			response.setData(repository.findById(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/user/branch/{id}")
	public ResponseEntity<Response> getBranchByUser(@PathVariable("id") int id){
		try {
			response.setData(repository.findBranchByUser(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/all_branches")
	public ResponseEntity<Response> getAllSucursales(){
		try {
			response.setData(repository.findAll());
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/branch/company/{id}")
	public ResponseEntity<Response> getAllByEmpresaId(@PathVariable("id") int id){
		try {
			response.setData(repository.findAllByCompanyID(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path = "api/add_branch", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addSucursal(@RequestBody String branch) throws JsonParseException, JsonMappingException, IOException{
		BranchOfficeDTO branchOfficeDTO = new ObjectMapper().readValue(branch, BranchOfficeDTO.class);
		try {
			repository.insert(branchOfficeDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path = "api/upd_branch", consumes = MediaType.TEXT_PLAIN_VALUE,
	 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateSucursal(@RequestBody String branch) throws JsonParseException, JsonMappingException, IOException{
		BranchOfficeDTO branchOfficeDTO = new ObjectMapper().readValue(branch, BranchOfficeDTO.class);
		try {
			repository.update(branchOfficeDTO);
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@DeleteMapping(path="api/del_branch/{id}")
	public ResponseEntity<Response> DeleteById(@PathVariable("id") int id){
		try {
			repository.deleteById(id);
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}

}
