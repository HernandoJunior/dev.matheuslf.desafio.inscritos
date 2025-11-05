package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.TaskFilterDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
import dev.matheuslf.desafio.inscritos.dto.TaskUpdateDto;
import dev.matheuslf.desafio.inscritos.model.PriorityTask;
import dev.matheuslf.desafio.inscritos.model.StatusTask;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import dev.matheuslf.desafio.inscritos.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Posts
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@RequestBody @Valid TaskCreateDto taskCreateDto) {
        return taskService.tasksCreate(taskCreateDto);
    }

    //Puts
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto attTask(@RequestParam StatusTask status,
                                   @RequestParam Long id){
        return taskService.atualizeTask(status, id);
    }

    //Getters
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponseDto> getTasks(){
        return taskService.taskList();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
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
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        return taskService.deleteById(id);
    }
}
