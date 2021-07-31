package com.abc.DemoLogin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.DemoLogin.model.BaseResponse;
import com.abc.DemoLogin.model.Login;
import com.abc.DemoLogin.model.User;
import com.abc.DemoLogin.service.UserLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/bank/user")
public class UserController {

	@Autowired
    UserLoginService userLoginService;
	
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<Object> registerUser(@Validated(User.ValidationStepOne.class)@RequestBody(required = true) User userData, BindingResult result) throws JsonMappingException, JsonProcessingException {
	
		BaseResponse response= new BaseResponse();
        	
		try{
			
			if(result.hasErrors()){
				response.setStatus("401");
				response.setMessageType("Failure");
				response.setMessage("Email is not valid");
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		
		String userName = userData.getUserName();		
		String customerName = userData.getCustomerName();
		String password = userData.getPassword();
		String customerType = userData.getCustomerType();
		
		
		Date date = new Date();
		
			
		Optional<User> userOptional = userLoginService.findByUserName(userName);
			
			if(userOptional.isPresent()) {
				response.setStatus("409");
				response.setMessageType("Failure");
				response.setMessage("User already exists");
				
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else{
				
				User user = new User();
				UUID uuid = UUID.randomUUID();
				try {
					
					user.setUserName(userName);
					user.setCustomerName(customerName);
					user.setPassword(password);
					user.setCustomerType(customerType);
					user.setSessionStatus("INACTIVE");
				    userLoginService.save(user);
				}catch(Exception e) {
					logger.error("userLoginService.save(user) failed : " , e); 
				}
			    
			    response.setStatus("200");
			    response.setMessageType("Success");
			    response.setMessage("You are successfully registered");
			    Map<String, Object> map = new HashMap<String, Object>();
			    map.put("UserDetails", userData);
				response.setData(map);
			    return new ResponseEntity<>(response, HttpStatus.OK);
			
			}
			
	}catch(Exception e){
		logger.error("add failed : " , e);
    }
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	@PostMapping(path = "/login")
	public ResponseEntity<Object> userLogin(@RequestBody(required = true) Login loginData, HttpServletRequest request) throws JsonMappingException, JsonProcessingException{
    
		BaseResponse response= new BaseResponse();
		try{    
		String userName = loginData.getUserName();
		
		String password = loginData.getPassword();
		
		
		User user = null;
		if(!userName.isEmpty() && !password.isEmpty()) {
			
			user = userLoginService.validateUser(userName, password);
			
			if(user != null) {
			
				String sessionStatus = user.getSessionStatus();
				if(sessionStatus.equalsIgnoreCase("INACTIVE")) {
				Date todayDate = new Date();
				
				user.setUserName(user.getUserName());
				user.setCustomerName(user.getCustomerName());
				user.setPassword(user.getPassword());
				user.setCustomerType(user.getCustomerType());
				user.setLastLoginDate(todayDate);
				user.setSessionStatus("ACTIVE");
				userLoginService.save(user);
				        
						response.setStatus("200");
						response.setMessageType("Success");
						response.setMessage("You are successfully Logged in");
						
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("UserDetails", user);
						response.setData(m);
						
						
						return new ResponseEntity<>(response, HttpStatus.OK);
						
				}else{
					response.setStatus("401");
					response.setMessageType("Failure");
					response.setMessage("You are already logged in");
					
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				
			}else {
				
				response.setStatus("401");
				response.setMessageType("Failure");
				response.setMessage("Login Failed ! Wrong credentials");
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			}
		}else {
			
			
			response.setStatus("400");
			response.setMessageType("Failure");
			response.setMessage("Request body parameter missing");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		}catch(Exception e){
			logger.error("login failed : " , e);
	    }
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
}
