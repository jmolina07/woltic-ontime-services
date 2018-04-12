package com.co.ontime_services.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.co.ontime_services.controllers.extras.Login;
import com.co.ontime_services.controllers.extras.Response;
import com.co.ontime_services.dao.UserDAO;
import com.co.ontime_services.entities.UserDTO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import net.minidev.json.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	UserDAO repository;
	Response response = new Response();
	ErrorResponse error = new ErrorResponse();
	HttpStatus res;
	
	@GetMapping(path="api/all_users")
	public ResponseEntity<Response> getAllUsers(){
		try {
			List<UserDTO> body = repository.findAll();
			res = HttpStatus.OK;
			response.setStatus("success");
			response.setData(body);
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res); 
	}
	
	@GetMapping(path="api/user/{id}")
	public ResponseEntity<Response> getUserByID(@PathVariable("id") int id){
		try {
			UserDTO body = repository.findById(id);
			res = HttpStatus.OK;
			response.setStatus("success");
			response.setData(body);
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path="api/login", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> loginUser(@RequestBody String user) throws JsonParseException, JsonMappingException, 
			IOException{
		Login json = new ObjectMapper().readValue(user, Login.class); 
		try {
			UserDTO body = repository.findByEP(json.getEmail(), json.getPassword());
			if(body == null) {
				res = HttpStatus.NOT_FOUND;
				response.setStatus("fail");
				response.setData(null);
				return new ResponseEntity<>(response, res);
			}else{
				System.out.println("no es null");
				res = HttpStatus.OK;
				response.setStatus("success");
				response.setData(body);
			}
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path="api/{b_id}/add_user", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addProfessional(@PathVariable("b_id") int b_id, @RequestBody String user) 
			throws JsonParseException,JsonMappingException, IOException{
		UserDTO userDTO = new ObjectMapper().readValue(user, UserDTO.class); 
		try {
			repository.insertProfessional(b_id, userDTO);
			res = HttpStatus.CREATED;
			response.setStatus("success");	
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path="api/add_user", consumes = MediaType.TEXT_PLAIN_VALUE,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> addUser(@RequestBody String user) throws JsonParseException, JsonMappingException, IOException{
		UserDTO userDTO = new ObjectMapper().readValue(user, UserDTO.class);
		System.out.println(userDTO.toString());
		try {
			repository.insertUser(userDTO);
			res = HttpStatus.CREATED;
			response.setStatus("success");	
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path="api/upd_user", consumes = MediaType.TEXT_PLAIN_VALUE ,
			 produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Response> updateProfessional(@RequestBody String user) throws JsonParseException, 
			JsonMappingException, IOException{
		UserDTO userDTO = new ObjectMapper().readValue(user, UserDTO.class);
		try {
			repository.update(userDTO);
			res = HttpStatus.OK;
			response.setStatus("success");	
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@DeleteMapping(path="api/del_professional/{id1}/{id2}")
	public ResponseEntity<Response> deleteProfessional(@PathVariable("id1") int branch_id, 
			@PathVariable("id2") int professional_id){
		try {
			repository.deleteProfessional(branch_id, professional_id);
			res = HttpStatus.OK;
			response.setStatus("success");	
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@DeleteMapping(path="api/del_user/{id1}")
	public ResponseEntity<Response> deleteUser(@PathVariable("id1") int id) throws JsonParseException,
			JsonMappingException, IOException{
		try {
			repository.deleteUser(id);
			res = HttpStatus.OK;
			response.setStatus("success");	
		} catch (Exception e) {
			error.setStatus("fail");
			error.setMessage(e.toString());
			res = HttpStatus.CONFLICT;
		}
		return new ResponseEntity<>(response, res);
	}
	
	@PostMapping(path="api/test")
	public void getTest(@RequestBody String user) throws Exception{
		//JSONObject obj=(JSONObject)JSONValue.parse(user); 
		JSONParser parser = new JSONParser();
		org.json.simple.JSONArray jsonArray= (org.json.simple.JSONArray) parser.parse(user);
		System.out.println("String: " + user);
		System.out.println(jsonArray.toJSONString());
		List<String> hola = split(user);
		System.out.println("el cero: " + hola.get(0));
		System.out.println("el uno: " + hola.get(1));
				
		/*JSONParser parser = new JSONParser();
		Object obj  = parser.parse(user);
		JSONArray array = new JSONArray();
		array.add(obj);*/
		/*JSONParser parser = new JSONParser();
		JSONObject jsnobject = (JSONObject) parser.parse(user);*/
		/*JSONParser parser = new JSONParser();
		JSONArray parser2 = new JSONArray();
		JSONObject json = (JSONObject) parser.parse(user);
		JSONArray json2 = (JSONArray) parser2;
		
		System.out.println(json2);*/
		/*JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(stringToParse);
		
		UserDTO userDTO = new UserDTO();
		String hola;
		json.get(hola) = String;
		json.get(userDTO) = "test one";
		System.out.println(json);*/
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
