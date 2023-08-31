package com.example.projectservice.extermal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.projectservice.model.User;

@FeignClient(url = "http://localhost:8083" , name = "USER-SERVICE" )
public interface UserService {
	
	@GetMapping("/api/users/username/{username}")
	User loadByUserName(@PathVariable String username);

}
