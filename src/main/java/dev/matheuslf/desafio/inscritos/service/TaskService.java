package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.dto.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.TaskFilterDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
import dev.matheuslf.desafio.inscritos.mappers.TaskMapper;
import dev.matheuslf.desafio.inscritos.model.Project;
import dev.matheuslf.desafio.inscritos.model.Task;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }

    public TaskResponseDto tasksCreate(TaskCreateDto taskCreateDto){
        Project project = projectRepository.findById(Long.parseLong(taskCreateDto.projectId())).get();

        Task task = taskMapper.toEntityTask(taskCreateDto);

        task.setProject(project);
        Task taskSave = taskRepository.save(task);

        return new TaskResponseDto(taskSave);
    }

    public List<TaskResponseDto> taskList(){
        return taskMapper.toResponseTaskDto(taskRepository.findAll());
    }

    public ResponseEntity.BodyBuilder deleteById(Long id){
        if (id.toString().isEmpty()){
            throw new RuntimeException("Projeto nao encontrado");
        } else {
            taskRepository.deleteById(id);
        }
        return ResponseEntity.ok();
    }

    public List<TaskResponseDto> getTasksWithFilters(TaskFilterDto filter) {
        return taskRepository.findAll()
                .stream()
                .filter(t -> t.getStatus().equals(filter.status())
                        || t.getDueDate().equals(filter.dueDate())
                        || t.getProject().equals(filter.projectName())
                        || t.getDescription().equals(filter.description())
                        || t.getTitle().equals(filter.title())
                        || t.getPriority().equals(filter.priority()))
                .map(TaskResponseDto::new)
                .toList();
    }

}
