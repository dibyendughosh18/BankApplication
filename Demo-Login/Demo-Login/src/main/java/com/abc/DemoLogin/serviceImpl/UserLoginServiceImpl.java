package com.abc.DemoLogin.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.DemoLogin.model.User;
import com.abc.DemoLogin.repository.UserMasterRepository;
import com.abc.DemoLogin.service.UserLoginService;

@Service
public class UserLoginServiceImpl implements UserLoginService{

	@Autowired
	private UserMasterRepository userMasterRepository;
	
	public void save(User user) {
		userMasterRepository.save(user);
	}
	
	public User validateUser(String userName, String password) {
		Optional<User> userOptional = userMasterRepository.findByUserName(userName);
		
		User user = null;
		String dbPassword = null;
		User dbuser = null;
		
		if(userOptional.isPresent()) {
			dbuser = userOptional.get();
			dbPassword = dbuser.getPassword();
			}
		
		if(password.equals(dbPassword)) {
			
			user = dbuser;
			
			return user;
		}else {
			return user;
		}
	}
	
	public Optional<User> findByUserName(String userName) {
		return userMasterRepository.findByUserName(userName);
		
	}
}
