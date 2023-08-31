package com.example.projectservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.projectservice.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User getById(Long id);
	
}
