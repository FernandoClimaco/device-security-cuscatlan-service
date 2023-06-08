package com.device.security.service;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.device.security.dao.UserDao;
import com.device.security.model.User;
import com.device.security.model.UserDTO;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private UserDao userDao;
	
	
	public User saveUser(UserDTO user) { 
		User newUserSave = new User();
		newUserSave.setCreated(new Date());
		newUserSave.setName(user.getName());
		newUserSave.setUser(user.getUsername());
		newUserSave.setAddress(user.getAddress()); 
		newUserSave.setIdentification(user.getIdentification());
		newUserSave.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUserSave.setCountry(user.getCountry());
		newUserSave.setPhone(user.getPhone());
		newUserSave.setEmail(user.getEmail());
		return userDao.save(newUserSave);
		  
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("This user not exists with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUser(), user.getPassword(),
				new ArrayList<>());
	}
	 
}