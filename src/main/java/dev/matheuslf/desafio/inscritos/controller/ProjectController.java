package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectCreateDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectResponseDto;
import dev.matheuslf.desafio.inscritos.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //Posts
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto createProject(@RequestBody @Valid ProjectCreateDto projectCreateDto){
        return projectService.projectCreate(projectCreateDto);
    }

    //Getters
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectResponseDto> listProjects(){
        return projectService.projectsList();
    }
}
