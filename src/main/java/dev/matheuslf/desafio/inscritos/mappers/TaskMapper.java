package dev.matheuslf.desafio.inscritos.mappers;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
import dev.matheuslf.desafio.inscritos.model.Task;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntityTask(TaskCreateDto taskCreateDto);

    //convertendo e retornando lista de responseDto
    List<TaskResponseDto> toResponseTaskDto(List<Task> taskList);

}
