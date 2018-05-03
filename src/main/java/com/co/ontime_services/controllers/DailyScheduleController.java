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
import com.co.ontime_services.dao.DailyScheduleDAO;
import com.co.ontime_services.entities.DailyScheduleDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin
@RestController
public class DailyScheduleController {
	
	@Autowired
	DailyScheduleDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;

	@GetMapping(path="api/schedule/{id}")
	public ResponseEntity<Response> getScheduleByID(@PathVariable("id") int id){
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
	
	@GetMapping(path="api/all_schedules")
	public ResponseEntity<Response> getAllSchedule(){
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
	
	@GetMapping(path="api/schedule/branch/{id}")
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
	
	@GetMapping(path="api/schedule/professional/{id}")
	public ResponseEntity<Response> getAllByProfessionalID(@PathVariable("id") int id){
		try {
			response.setData(repository.findAllByProfessionalID(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@GetMapping(path="api/schedule/date/{id}/{duration}/{date}")
	public ResponseEntity<Response> getAppointmentInfo(@PathVariable("id") int id, @PathVariable("duration") int duration,
			 @PathVariable("date") String date) {
		try {
			response.setData(repository.AppointmentSlots(id, duration, date));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@GetMapping(path="api/schedule/client/{id}")
	public ResponseEntity<Response> getAllByClientID(@PathVariable("id") int id){
		try {
			response.setData(repository.findAllByClientID(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@PostMapping(path = "api/add_schedule", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addSchedule(@RequestBody String schedule) throws JsonParseException, JsonMappingException, 
			IOException{
		DailyScheduleDTO scheduleDTO = new ObjectMapper().readValue(schedule, DailyScheduleDTO.class);
		try {
			repository.insert(scheduleDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@PostMapping(path = "api/upd_schedule", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateSchedule(@RequestBody String schedule) throws JsonParseException, JsonMappingException, IOException{
		DailyScheduleDTO scheduleDTO = new ObjectMapper().readValue(schedule, DailyScheduleDTO.class);
		try {
			repository.update(scheduleDTO);
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@DeleteMapping(path="api/del_schedule/{id}")
	public ResponseEntity<Response> DeleteScheduleById(@PathVariable("id") int id){
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
