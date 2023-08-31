package com.example.projectservice.repository;

import com.example.projectservice.model.Project;
import com.example.projectservice.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProjectRepositoryTest {

    @Mock
    private ProjectRepository projectRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByProjectIdentifier() {
        Project project = new Project();
        when(projectRepository.findByProjectIdentifier(anyString())).thenReturn(project);

        Project result = projectRepository.findByProjectIdentifier("projectId");

        assertSame(project, result);
    }

    @Test
    public void testFindAll() {
        Iterable<Project> projects = Collections.emptyList();
        when(projectRepository.findAll()).thenReturn(projects);

        Iterable<Project> result = projectRepository.findAll();

        assertSame(projects, result);
    }

    @Test
    public void testFindAllByProjectLeader() {
        Iterable<Project> projects = Collections.emptyList();
        when(projectRepository.findAllByProjectLeader(anyString())).thenReturn(projects);

        Iterable<Project> result = projectRepository.findAllByProjectLeader("username");

        assertSame(projects, result);
    }

    @Test
    public void testSaveProject() {
        Project project = new Project();
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project result = projectRepository.save(project);

        assertSame(project, result);
    }

    @Test
    public void testDeleteProject() {
        doNothing().when(projectRepository).delete(any(Project.class));

        assertDoesNotThrow(() -> projectRepository.delete(new Project()));
        verify(projectRepository).delete(any(Project.class));
    }
}
