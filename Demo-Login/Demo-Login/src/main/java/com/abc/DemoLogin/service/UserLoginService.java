package com.abc.DemoLogin.service;

import java.util.Optional;

import com.abc.DemoLogin.model.User;

public interface UserLoginService {

	public void save(User user);
	
	public User validateUser(String userName, String password);
	
	public Optional<User> findByUserName(String userName);
}
