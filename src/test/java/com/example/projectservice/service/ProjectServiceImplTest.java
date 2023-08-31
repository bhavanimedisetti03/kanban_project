package com.example.projectservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.projectservice.exception.ProjectIdException;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.extermal.TaskService;
import com.example.projectservice.extermal.UserService;
import com.example.projectservice.model.Project;
import com.example.projectservice.model.User;
import com.example.projectservice.repository.BacklogRepository;
import com.example.projectservice.repository.ProjectRepository;

public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    
    @Mock
    private BacklogRepository backlogRepository;
    
    @Mock
    private TaskService taskService;
    
    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrUpdateProject_NewProject_Success() {
        // Prepare test data
        Project project = new Project();
        project.setProjectIdentifier("PR123");
        String username = "user123";
        User user = new User();
        user.setUserName(username);
        
        // Mock userService behavior
        when(userService.loadByUserName(username)).thenReturn(user);
        
        // Mock projectRepository behavior
        when(projectRepository.save(any())).thenReturn(project);

        // Test the service method
        Project savedProject = projectService.saveOrUpdateProject(project, username);

        assertNotNull(savedProject);
        assertEquals("PR123", savedProject.getProjectIdentifier());
        assertEquals(username, savedProject.getProjectLeader());
    }

    @Test
    public void testFindProjectByIdentifier_ExistingProject_Success() {
        // Prepare test data
        String projectId = "PR123";
        String username = "user123";
        Project project = new Project();
        project.setProjectIdentifier(projectId);
        project.setProjectLeader(username);

        // Mock projectRepository behavior
        when(projectRepository.findByProjectIdentifier(projectId)).thenReturn(project);

        // Mock taskService behavior
        when(taskService.getProjectBacklog(projectId)).thenReturn(new ArrayList<>());

        // Test the service method
        Project foundProject = projectService.findProjectByIdentifier(projectId, username);

        assertNotNull(foundProject);
        assertEquals(projectId, foundProject.getProjectIdentifier());
        assertEquals(username, foundProject.getProjectLeader());
    }

    @Test
    public void testFindProjectByIdentifier_NonExistentProject_Exception() {
        // Prepare test data
        String projectId = "PR123";
        String username = "user123";

        // Mock projectRepository behavior
        when(projectRepository.findByProjectIdentifier(projectId)).thenReturn(null);

        // Test the service method
        assertThrows(NullPointerException.class, () -> {
            projectService.findProjectByIdentifier(projectId, username);
        });
    }

    @Test
    public void testFindProjectByIdentifier_UnauthorizedUser_Exception() {
        // Prepare test data
        String projectId = "PR123";
        String username = "user123";
        Project project = new Project();
        project.setProjectIdentifier(projectId);
        project.setProjectLeader("otherUser");

        // Mock projectRepository behavior
        when(projectRepository.findByProjectIdentifier(projectId)).thenReturn(project);

        // Test the service method
        String expectedErrorMessage = "Expected ProjectNotFoundException to be thrown for unauthorized user.";
        assertThrows(
            ProjectNotFoundException.class,
            () -> projectService.findProjectByIdentifier(projectId, username),
            expectedErrorMessage
        );
    }


    // Add more test cases for other methods...
}
