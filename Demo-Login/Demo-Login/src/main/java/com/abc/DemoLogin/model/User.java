package com.abc.DemoLogin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;


import lombok.Data;

@Entity
@Data
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email(groups = {ValidationStepOne.class},message = "Email should be valid")
	private String userName;
	private String customerName;
	private String password;
	private Date lastLoginDate;
	private String customerType;
	private String customerSessionId;
	private String sessionStatus;
	
	public interface ValidationStepOne {
        // validation group marker interface
    }
}
