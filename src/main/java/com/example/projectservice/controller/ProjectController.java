package com.example.projectservice.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectservice.model.Project;
import com.example.projectservice.service.ProjectService;

import jakarta.ws.rs.Path;


@RequestMapping("/api/project")
@CrossOrigin
@RestController
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	
	@PostMapping("/data/{username}")
	public ResponseEntity<?> createNewProject( @RequestBody Project project , @PathVariable String username){	
		System.out.println(username);
		
		Project project1 = projectService.saveOrUpdateProject(project,username);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);	
	}
	
	@GetMapping("/{username}/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId, @PathVariable String username){
		Project project = projectService.findProjectByIdentifier(projectId,username);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping("/all/{username}")
	public Iterable<Project> getAllProjects(@PathVariable String username){
		System.out.println(username);
		return projectService.findAllProjects(username);
	}
	
	@DeleteMapping("/{username}/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId, @PathVariable String username){
		projectService.deleteProjectByIdentifier(projectId,username);
		return new ResponseEntity<String> ("Project with ID: '"+projectId+"' was deleted.", HttpStatus.OK);
	}

		
	
}

