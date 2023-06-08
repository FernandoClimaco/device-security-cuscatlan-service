package com.device.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.device.security.configuration.JwtTokenUtil;
import com.device.security.dao.UserDao;
import com.device.security.model.JwtAuthenticationRequest;
import com.device.security.model.ResponseDevice;
import com.device.security.model.ResponseDTO;
import com.device.security.model.UserDTO;
import com.device.security.service.JwtUserDetailsService;
import com.device.security.service.CacheRedisService; 


@RestController
@RequestMapping("/login")
@CrossOrigin
public class AuthenticationJwtController {

	
	Logger logger = LoggerFactory.getLogger(AuthenticationJwtController.class);
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private CacheRedisService cacheRedisService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
 
	@Autowired 
	private UserDao userDao;
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception {
		try {
			try {
				UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
				if(userDetails != null) {
					return new ResponseEntity<ResponseDevice>(new ResponseDevice("400" , userDTO.getUsername() +  " user already exist!") , HttpStatus.BAD_REQUEST);
				}else {
					return ResponseEntity.ok(userDetailsService.saveUser(userDTO));
				}
			} catch (UsernameNotFoundException e) {
				return ResponseEntity.ok(userDetailsService.saveUser(userDTO));
			}		
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<ResponseDevice>(new ResponseDevice("500" , e.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticationUserForToken(@RequestBody JwtAuthenticationRequest jwtAuthenticationRequest)  {
		try {
			managerUserAuthenticate(jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword());
			final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
			ResponseDTO redisToken = cacheRedisService.existRegisterOnRedis(jwtAuthenticationRequest.getUsername());
			if(redisToken!= null ) {
				
				return ResponseEntity.ok(redisToken);
			}else {
				final String token = jwtTokenUtil.generateToken(userDetails);
				ResponseDTO redisData = new ResponseDTO();
				redisData.setToken(token);
				redisData.setUser(userDao.findByUsername(jwtAuthenticationRequest.getUsername()));
				cacheRedisService.saveTokenOnRedis(redisData);
				logger.info("[Login success]");
				return ResponseEntity.ok(redisData);
			}
		} 
		catch(BadCredentialsException b) {
			logger.error(b.getMessage());
			return new ResponseEntity<ResponseDevice>(new ResponseDevice("401" , b.getMessage()) , HttpStatus.UNAUTHORIZED);
		}catch(DisabledException d) {
			logger.error(d.getMessage());
			return new ResponseEntity<ResponseDevice>(new ResponseDevice("401" , d.getMessage()) , HttpStatus.UNAUTHORIZED);
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<ResponseDevice>(new ResponseDevice("500" , e.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	

	 
	private void managerUserAuthenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));		
		} catch (BadCredentialsException e) {
			logger.error(e.getMessage());
			throw new BadCredentialsException("INVALID_CREDENTIALS FOR THIS USER", e);
		} catch (DisabledException d) {
			logger.error(d.getMessage());
			throw new DisabledException("DISABLED CREDENTIALS FOR THIS USER", d);
		}
	}
}