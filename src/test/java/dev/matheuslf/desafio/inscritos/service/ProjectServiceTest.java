package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectCreateDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectResponseDto;
import dev.matheuslf.desafio.inscritos.mappers.ProjectMapper;
import dev.matheuslf.desafio.inscritos.model.Project;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    public Project project;
    private ProjectCreateDto projectCreateDto;

    @BeforeEach
    void setUp() {
        project = new Project(
                1L,
                "Project Test",
                "Desc",
                LocalDate.now(),
                LocalDate.now()
        );

        projectCreateDto = new ProjectCreateDto(
                "Project Test",
                "Desc",
                "2025-11-07",
                "2025-11-07"
        );
    }

    @Test
    void projectCreate() {
        // ARRANGE
        when(projectMapper.toEntity(any(ProjectCreateDto.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // ACT
        ProjectResponseDto result = projectService.projectCreate(projectCreateDto);

        // ASSERT
        assertNotNull(result, "Resultado não deveria ser nulo");
        assertEquals("Project Test", result.name());
        verify(projectMapper, times(1)).toEntity(any(ProjectCreateDto.class));
        verify(projectRepository, times(1)).save(any(Project.class));

        System.out.println("Teste realizado com sucesso! Projeto: " + result.name());
    }

    @Test
    void projectAtualize() {
    }

    @Test
    void projectsList() {
    }
}