package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.TaskFilterDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
import dev.matheuslf.desafio.inscritos.dto.TaskUpdateDto;
import dev.matheuslf.desafio.inscritos.mappers.TaskMapper;
import dev.matheuslf.desafio.inscritos.model.PriorityTask;
import dev.matheuslf.desafio.inscritos.model.Project;
import dev.matheuslf.desafio.inscritos.model.StatusTask;
import dev.matheuslf.desafio.inscritos.model.Task;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskResponseDto tasksCreate(TaskCreateDto taskCreateDto){
        Project project = projectRepository.findById(Long.parseLong(taskCreateDto.projectId())).get();

        Task task = taskMapper.toEntityTask(taskCreateDto);

        task.setProject(project);
        Task taskSave = taskRepository.save(task);

        return new TaskResponseDto(taskSave);
    }

    public TaskResponseDto atualizeTask(StatusTask status, Long id){
        TaskUpdateDto taskUpdateDto = new TaskUpdateDto(id, status);

        Optional<Task> taskOptional = taskRepository.findById(taskUpdateDto.id());
        if (taskOptional.isEmpty()){
            throw new RuntimeException("Task nao encontrada");
        }
        Task task = taskOptional.get();
        task.setStatus(taskUpdateDto.statusTask());

        Task taskSaved = taskRepository.save(task);

        return new TaskResponseDto(taskSaved);

    }

    public List<TaskResponseDto> getTasksWithFilters(TaskFilterDto filter) {
        return taskRepository.findByFilters(
                        filter.status(),
                        filter.priority(),
                        filter.projectId()
                )
                .stream()
                .map(TaskResponseDto::new)
                .toList();
    }

    public List<TaskResponseDto> taskList(){
        return taskMapper.toResponseTaskDto(taskRepository.findAll());
    }

    public ResponseEntity<String> deleteById(Long id){
        Optional<Task> task  = taskRepository.findById(id);

        if (task.isEmpty()){
            throw new RuntimeException("Task nao encontrada");
        }
        Task taskDelete = task.get();

        taskRepository.delete(taskDelete);

        return ResponseEntity.ok("Task deletada com sucesso!");
    }
    
}
