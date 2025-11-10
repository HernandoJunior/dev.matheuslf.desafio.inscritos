package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectCreateDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectResponseDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectUpdateDto;
import dev.matheuslf.desafio.inscritos.mappers.ProjectMapper;
import dev.matheuslf.desafio.inscritos.model.Project;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    public ProjectResponseDto projectCreate (ProjectCreateDto projectCreateDto){
        Project project = projectMapper.toEntity(projectCreateDto);
        project.setStartDate(LocalDate.parse(projectCreateDto.startDate()));
        Project projectSaved = projectRepository.save(project);

        return new ProjectResponseDto(projectSaved);
    }

    public ProjectResponseDto projectAtualize(ProjectUpdateDto projectUpdateDto){
        Project project = new Project();
        projectMapper.updateFromDto(projectUpdateDto, project);
        return new ProjectResponseDto(project);

    }

    public List<ProjectResponseDto> projectsList (){
        return projectMapper.toResponseDto(projectRepository.findAll());
    }
}
