package com.example.projectservice.service;

import com.example.projectservice.model.Project;

public interface ProjectService {

	public Project saveOrUpdateProject(Project project, String username);
	
	public Project findProjectByIdentifier(String projectId, String username);
	
	public Iterable<Project> findAllProjects(String username);
	
	public void deleteProjectByIdentifier(String projectId, String username);
}
