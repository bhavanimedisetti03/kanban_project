package com.example.projectservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectservice.exception.ProjectIdException;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.extermal.TaskService;
import com.example.projectservice.extermal.UserService;
import com.example.projectservice.model.Backlog;
import com.example.projectservice.model.Project;
import com.example.projectservice.model.ProjectTask;
import com.example.projectservice.model.User;
import com.example.projectservice.repository.BacklogRepository;
import com.example.projectservice.repository.ProjectRepository;



@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
//	@Autowired
//	private UserRepository userRepository;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	public Project saveOrUpdateProject(Project project, String username) {
		System.out.println(username);
		if(project.getId() != null) {
			Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject != null &&(!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			}else if(existingProject == null) {
				throw new ProjectNotFoundException("Project with ID:'"+project.getProjectIdentifier()+"' cannot be updated because it does not exist!");
			}
		} 
		
		try {
			System.out.println("project3");
			User user = userService.loadByUserName(username);
			System.out.println(user);
			project.setUser_id(user.id);
			project.setProjectLeader(username);
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			//saving new project
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog.getId());
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			//updating project
//			if(project.getId()!=null) {
//				project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
//			}
			
			return projectRepository.save(project);
			
		}catch(Exception e) {
			throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase()+"' already exists.");
		}
	}
	
	//find by ID
	public Project findProjectByIdentifier(String projectId, String username) {
		
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		List<ProjectTask> projectTaskList =  taskService.getProjectBacklog(projectId);
		
		project.setProjectTaskList(projectTaskList);
				
		
		if(project == null) {
			throw new ProjectIdException("Project ID '"+ projectId+"' does not exist.");
		}
		
		if(!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account!");
		}
		
		
		return project;
	}
	
	//find All
	public Iterable<Project> findAllProjects(String username){
		return projectRepository.findAllByProjectLeader(username);
	}
	
	//delete by ID
	public void deleteProjectByIdentifier(String projectId, String username) {	
		projectRepository.delete(findProjectByIdentifier(projectId, username));
		
	}
	

}
