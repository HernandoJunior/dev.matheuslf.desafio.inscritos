package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.TaskFilterDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@RequestBody @Valid TaskCreateDto taskCreateDto) {
        return taskService.tasksCreate(taskCreateDto);
    }

    //Getters
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponseDto> getTasks(TaskFilterDto taskFilterDto){
        return taskService.getTasksWithFilters(taskFilterDto);
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<TaskResponseDto> listTasks(){
//        return taskService.taskList();
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity.BodyBuilder deleteTask(@RequestParam Long id){
        return taskService.deleteById(id);
    }
}
