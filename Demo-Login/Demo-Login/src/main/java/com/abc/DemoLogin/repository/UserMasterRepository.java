package com.abc.DemoLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.DemoLogin.model.User;


@Repository
public interface UserMasterRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String userName);
}
