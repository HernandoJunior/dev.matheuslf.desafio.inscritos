package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectCreateDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectResponseDto;
import dev.matheuslf.desafio.inscritos.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projetos", description = "Gerenciamento de projetos")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //Posts
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo projeto", description = "Criacao de novo projeto")
    public ProjectResponseDto createProject(@RequestBody @Valid ProjectCreateDto projectCreateDto){
        return projectService.projectCreate(projectCreateDto);
    }

    //Getters
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar projetos", description = "Retorna lista de projetos cadastrados")
    public List<ProjectResponseDto> listProjects(){
        return projectService.projectsList();
    }
}
