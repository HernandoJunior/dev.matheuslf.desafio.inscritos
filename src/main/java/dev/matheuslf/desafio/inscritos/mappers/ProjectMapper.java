package dev.matheuslf.desafio.inscritos.mappers;

import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectCreateDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectResponseDto;
import dev.matheuslf.desafio.inscritos.dto.projectDtos.ProjectUpdateDto;
import dev.matheuslf.desafio.inscritos.model.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    //convertendo para cadastrar o projeto
    Project toEntity(ProjectCreateDto dto);

    //convertendo e retornando lista de responseDto
    List<ProjectResponseDto> toResponseDto(List<Project> projectList);

    //atualizando projetos
    void updateFromDto(ProjectUpdateDto projectUpdateDto, @MappingTarget Project project);
}
