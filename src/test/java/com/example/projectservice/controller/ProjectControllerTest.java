package com.example.projectservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.projectservice.model.Project;
import com.example.projectservice.service.ProjectService;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewProject() {
        Project project = new Project();
        when(projectService.saveOrUpdateProject(any(Project.class), anyString())).thenReturn(project);

        ResponseEntity<?> responseEntity = projectController.createNewProject(project, "username");

        verify(projectService).saveOrUpdateProject(project, "username");
        assertSame(project, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testGetProjectById() {
        Project project = new Project();
        when(projectService.findProjectByIdentifier(anyString(), anyString())).thenReturn(project);

        ResponseEntity<?> responseEntity = projectController.getProjectById("projectId", "username");

        verify(projectService).findProjectByIdentifier("projectId", "username");
        assertSame(project, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllProjects() {
        Iterable<Project> projects = mock(Iterable.class);
        when(projectService.findAllProjects(anyString())).thenReturn(projects);

        Iterable<Project> returnedProjects = projectController.getAllProjects("username");

        verify(projectService).findAllProjects("username");
        assertSame(projects, returnedProjects);
    }

    @Test
    public void testDeleteProject() {
        ResponseEntity<?> responseEntity = projectController.deleteProject("projectId", "username");

        verify(projectService).deleteProjectByIdentifier("projectId", "username");
        assertEquals("Project with ID: 'projectId' was deleted.", responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
