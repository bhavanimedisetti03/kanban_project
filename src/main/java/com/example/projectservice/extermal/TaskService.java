package com.example.projectservice.extermal;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.projectservice.model.ProjectTask;

@FeignClient(url = "http://localhost:8084" , value = "TASK-SERVICE")
public interface TaskService {

	@GetMapping("/api/backlog/{backlog_id}")
	List<ProjectTask> getProjectBacklog(@PathVariable("backlog_id") String identifier);
	
}
