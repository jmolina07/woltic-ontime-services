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
import com.co.ontime_services.dao.BranchServicesDAO;
import com.co.ontime_services.entities.BranchServicesDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
public class BranchServicesController {
	
	@Autowired
	BranchServicesDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;

	@GetMapping(path="api/bs/{id}")
	public ResponseEntity<Response> getById(@PathVariable("id") int id){
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

	@GetMapping(path="api/bs/branch_service/{id}/{id2}")
	public ResponseEntity<Response> getByBranchService(@PathVariable("id") int id, @PathVariable("id2") int id2 ){
		try {
			response.setData(repository.findByBranchIdServicesId(id, id2));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/bs/branch/{id}")
	public ResponseEntity<Response> getAllByBranchID(@PathVariable("id") int id){
		try {
			response.setData(repository.findAllByBranchID(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/bs/service/{id}")
	public ResponseEntity<Response> getAllByServiceID(@PathVariable("id") int id){
		try {
			response.setData(repository.findAllByServiceID(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/all_bs")
	public ResponseEntity<Response> getAllBranchServices(){
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
	
	@PostMapping(path = "api/add_bs", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addBranchService(@RequestBody String sucursalServicio) 
			throws JsonParseException, JsonMappingException, IOException{
		BranchServicesDTO sucursalServicioDTO = new ObjectMapper().readValue(sucursalServicio, BranchServicesDTO.class);
		try {
			repository.insert(sucursalServicioDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path = "api/upd_bs", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateBranchService(@RequestBody String sucursalServicio) 
			throws JsonParseException, JsonMappingException, IOException{
		BranchServicesDTO sucursalServicioDTO = new ObjectMapper().readValue(sucursalServicio, BranchServicesDTO.class);
		try {
			repository.update(sucursalServicioDTO);
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@DeleteMapping(path="api/del_bs/{id}")
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
