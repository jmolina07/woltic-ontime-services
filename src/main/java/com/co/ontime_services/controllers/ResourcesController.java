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
import com.co.ontime_services.dao.ResourcesDAO;
import com.co.ontime_services.entities.ResourcesDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin
@RestController
public class ResourcesController {
	
	@Autowired
	ResourcesDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;
	
	@GetMapping(path="api/resource/{id}")
	public ResponseEntity<Response> getResourceById(@PathVariable("id") int resource_id){
		try {
			response.setData(repository.findById(resource_id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@GetMapping(path="api/all_resources")
	public ResponseEntity<Response> getAllResources(){
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
	
	@PostMapping(path = "api/{branch_id}/add_resource", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addResource(@PathVariable("branch_id") int branch_id, @RequestBody String resource) 
			throws JsonParseException, JsonMappingException, IOException{
		ResourcesDTO resourceDTO = new ObjectMapper().readValue(resource, ResourcesDTO.class);
		try {
			repository.insert(branch_id, resourceDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path = "api/upd_resource", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateResource(@RequestBody String resource) 
			throws JsonParseException, JsonMappingException, IOException{
		ResourcesDTO resourceDTO = new ObjectMapper().readValue(resource, ResourcesDTO.class);
		try {
			repository.update(resourceDTO);
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}

	@DeleteMapping(path="api/del_resource/{id}")
	public ResponseEntity<Response> deleteResource(@PathVariable("id") int resource_id){
		try {
			repository.delete(resource_id);
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
