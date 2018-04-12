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
import com.co.ontime_services.controllers.extras.Service;
import com.co.ontime_services.dao.ServicesDAO;
import com.co.ontime_services.entities.BranchServicesDTO;
import com.co.ontime_services.entities.ServicesDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
public class ServicesController {
	
	@Autowired
	ServicesDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;

	@GetMapping(path="api/service/{id}")
	public ResponseEntity<Response> getServicesById(@PathVariable("id") int id){
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

	@GetMapping(path="api/all_services")
	public ResponseEntity<Response> getAllServices(){
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
	
	@PostMapping(path = "api/add_service", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addService(@RequestBody String service) 
			throws JsonParseException, JsonMappingException, IOException{
		Service body = new ObjectMapper().readValue(service, Service.class);
		ServicesDTO servicesDTO = new ServicesDTO();
		BranchServicesDTO bs = new BranchServicesDTO();
		
		servicesDTO.setName(body.getName());
		servicesDTO.setDescription(body.getDescription());
		bs.setDuration_minutes(body.getDuration());
		bs.setPrice(body.getPrice());
		bs.setTax_price(body.getTax());
		bs.setBranch_fk(body.getBranch_fk());
		try {
			repository.insert(servicesDTO, bs);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path = "api/upd_service", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateService(@RequestBody String service) 
			throws JsonParseException, JsonMappingException, IOException{
		Service body = new ObjectMapper().readValue(service, Service.class);
		ServicesDTO servicesDTO = new ServicesDTO();
		BranchServicesDTO bs = new BranchServicesDTO();
		
		servicesDTO.setService_id(body.getService_fk());
		servicesDTO.setName(body.getName());
		servicesDTO.setDescription(body.getDescription());
		
		bs.setBranch_service_id(body.getBranch_service_id());
		bs.setService_fk(body.getService_fk());
		bs.setDuration_minutes(body.getDuration());
		bs.setPrice(body.getPrice());
		bs.setTax_price(body.getTax());
		bs.setBranch_fk(body.getBranch_fk());

		try {
			repository.update(servicesDTO, bs);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@DeleteMapping(path="api/del_service/{bs_id}/{service_id}")
	public ResponseEntity<Response> DeleteService(@PathVariable("bs_id") int bs_id, @PathVariable("service_id") int service_id){
		try {
			repository.delete(bs_id, service_id);
			res = HttpStatus.OK;
			response.setStatus("success");	
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
}
