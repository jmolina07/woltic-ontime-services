package com.co.ontime_services.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HomeController {
	
	@RequestMapping(value = "/")
    public String index() {
        return "Greetings from The Back-End!";
    }
	
	@RequestMapping(value = "/api")
    public String index2() {
        return "Greetings from The Back-End!";
    }

}
