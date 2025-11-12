package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.taskDtos.TaskFilterDto;
import dev.matheuslf.desafio.inscritos.dto.taskDtos.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
import dev.matheuslf.desafio.inscritos.model.PriorityTask;
import dev.matheuslf.desafio.inscritos.model.StatusTask;
import dev.matheuslf.desafio.inscritos.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Gerenciamento de tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Posts
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastro de uma nova task", description = "Cadastro de uma task que vai ser vinculada a um projeto")
    public TaskResponseDto createTask(@RequestBody @Valid TaskCreateDto taskCreateDto) {
        return taskService.tasksCreate(taskCreateDto);
    }

    //Puts
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizacao de task", description = "Atualizacao do status de task com base no id")
    public TaskResponseDto attTask(@RequestParam StatusTask status,
                                   @RequestParam Long id){
        return taskService.atualizeTask(status, id);
    }

    //Getters
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Buscar taks", description = "Retorna todas as tasks cadastradas")
    public List<TaskResponseDto> getTasks(){
        return taskService.taskList();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Bucar tasks por filtros", description = "Busca tasks por filtros de status, priority e/ou id")
    public List<TaskResponseDto> getTasks(
            @RequestParam(required = false) StatusTask status,
            @RequestParam(required = false) PriorityTask priority,
            @RequestParam(required = false) Long projectId
    ) {
        TaskFilterDto filter = new TaskFilterDto(status, priority, projectId);
        return taskService.getTasksWithFilters(filter);
    }

    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deletar task", description = "Deleta task com base no id")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        return taskService.deleteById(id);
    }
}
