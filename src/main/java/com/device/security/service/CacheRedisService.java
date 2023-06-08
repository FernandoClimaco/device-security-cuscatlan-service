package com.device.security.service;

import java.time.Duration; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.device.security.configuration.JwtTokenUtil;
import com.device.security.model.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 
@Service
public class CacheRedisService {

	Logger logger = LoggerFactory.getLogger(CacheRedisService.class);
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${spring.redis.ttl}")
	private Long ttl;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	public ResponseDTO existRegisterOnRedis(String username) {
		try {
			Gson gson = new Gson();
			ValueOperations<String, String> valueOp= redisTemplate.opsForValue();	
			String token = valueOp.get(username) ;
			logger.info("stored data on Redis: " + token.toString());
			return token == null ? null : gson.fromJson(token ,ResponseDTO.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
	}
	
	
	public Boolean saveTokenOnRedis(ResponseDTO token) {
		try {
			Gson gson =new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String username = jwtTokenUtil.getUsernameFromToken(token.getToken());
			ValueOperations<String, String> valueOp= redisTemplate.opsForValue();	
			valueOp.set(username, gson.toJson(token), Duration.ofMinutes(ttl));
			logger.info("stored data on redis success");
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Boolean.FALSE;
		}
		
	}
	
	
}
