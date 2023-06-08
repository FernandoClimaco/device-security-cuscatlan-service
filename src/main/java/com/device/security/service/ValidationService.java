package com.device.security.service;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.device.security.configuration.JwtTokenUtil;
import com.device.security.model.ResponseDevice;
import com.device.security.model.ResponseDTO;

@Service
public class ValidationService {

	Logger logger = LoggerFactory.getLogger(ValidationService.class);
	
	@Value("${request.bearer}")
	private String bearer;
	
	@Autowired
	private CacheRedisService cacheRedisService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	public ResponseEntity<?> doValidationOnRedis(String token){
		try {
			if(token != null && !token.isEmpty()) {
				String tokenTemporality = token.trim().replace(bearer, "").replace(" ", "");
				ResponseDTO tokenRedis =  cacheRedisService.existRegisterOnRedis(jwtTokenUtil.getUsernameFromToken(tokenTemporality));
				logger.info("token on redis : "+tokenRedis.getToken().toString());
				if(tokenRedis != null && tokenRedis.getToken().equalsIgnoreCase(tokenTemporality)) {
					return ResponseEntity.ok(tokenRedis.getUser());
				}else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			}else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}			
		}catch (JDBCConnectionException conection) {
			conection.printStackTrace();
			return new ResponseEntity<ResponseDevice>(new ResponseDevice("500" , conection.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<ResponseDevice>(new ResponseDevice("500" , e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
}
