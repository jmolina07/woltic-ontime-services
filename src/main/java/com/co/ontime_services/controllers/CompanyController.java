package com.co.ontime_services.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.co.ontime_services.dao.CompanyDAO;
import com.co.ontime_services.entities.CompanyDTO;
import com.co.ontime_services.entities.UserDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@CrossOrigin
@RestController
public class CompanyController {
	
	@Autowired
	CompanyDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;

	@GetMapping(path="api/company/{id}")
	public ResponseEntity<Response> getCompanyByID(@PathVariable("id") int id){
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

	@GetMapping(path="api/all_companies")
	public ResponseEntity<Response> getAllCompanies(){
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
	
	@GetMapping(path="api/user/company/{id}")
	public ResponseEntity<Response> getCompanyByUser(@PathVariable("id") int id){
		try {
			response.setData(repository.findCompanyByUser(id));
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}

	@GetMapping(path="api/client/all_companies")
	public ResponseEntity<Response> getAllCompaniesForUser(){
		try {
			response.setData(repository.findAllForUser());
			response.setStatus("success");
			res = HttpStatus.OK;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);  
	}
	
	@PostMapping(path = "api/no_branch/add_company/{ans}", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addCompanyWithoutBranch(@RequestBody String json, @PathVariable("ans") int ans) 
			throws Exception, JsonParseException, JsonMappingException, IOException{
		System.out.println(json);
		List<String> jsonlist = split(json);
		String workingHours = jsonlist.get(2);
		System.out.println(workingHours);
		CompanyDTO companyDTO = new ObjectMapper().readValue(jsonlist.get(1), CompanyDTO.class);
		UserDTO userDTO = new ObjectMapper().readValue(jsonlist.get(0), UserDTO.class);
		try {
			repository.insertCompanyWithOutBranch(userDTO, companyDTO, ans, workingHours);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
		
		
		
		//JSONParser parser = new JSONParser();
		//org.json.simple.JSONArray jsonArray= (org.json.simple.JSONArray) parser.parse(company);
		
		/*System.out.println("el cero: " + jsonlist.get(0));
		System.out.println("el uno: " + jsonlist.get(1));
		repository.insertCompanyWithOutBranch(userDTO, companyDTO);*/
				
		//CompanyDTO companyDTO = new ObjectMapper().readValue(company, CompanyDTO.class);
		/*try {
			repository.insertCompanyAndBranch(companyDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); */
	}
	
	@PostMapping(path = "api/add_company", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addCompany(@RequestBody String company) 
			throws JsonParseException, JsonMappingException, IOException{
		CompanyDTO companyDTO = new ObjectMapper().readValue(company, CompanyDTO.class);
		try {
			repository.insert(companyDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@PostMapping(path = "api/upd_company", consumes = MediaType.TEXT_PLAIN_VALUE,
	 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateCompany(@RequestBody String company) throws JsonParseException, JsonMappingException, IOException{
		CompanyDTO companyDTO = new ObjectMapper().readValue(company, CompanyDTO.class);
		try {
			repository.update(companyDTO);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@DeleteMapping(path="api/del_company/{id}")
	public ResponseEntity<Response> DeleteCompanyById(@PathVariable("id") int id){
		try {
			repository.deleteById(id);
			response.setStatus("success");
			res = HttpStatus.CREATED;
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	public List<String> split(String jsonArray) throws Exception {
        List<String> splittedJsonElements = new ArrayList<String>();
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jsonNode = jsonMapper.readTree(jsonArray);

        if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                JsonNode individualElement = arrayNode.get(i);
                splittedJsonElements.add(individualElement.toString());
            }
        }
        return splittedJsonElements;
	}

}
