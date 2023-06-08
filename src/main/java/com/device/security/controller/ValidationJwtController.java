package com.device.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.device.security.service.ValidationService; 


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class ValidationJwtController {

	@Autowired
	private ValidationService validation; 
	
	@PostMapping("/validation")
	public ResponseEntity<?> doValidationOnRedis(@RequestHeader("Authorization") String  bearerToken){
		return validation.doValidationOnRedis(bearerToken);
	}
	
  
}
