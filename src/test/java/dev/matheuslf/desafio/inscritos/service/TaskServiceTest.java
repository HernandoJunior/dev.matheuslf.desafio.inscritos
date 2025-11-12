package dev.matheuslf.desafio.inscritos.service;

import dev.matheuslf.desafio.inscritos.dto.taskDtos.TaskFilterDto;
import dev.matheuslf.desafio.inscritos.dto.TaskResponseDto;
import dev.matheuslf.desafio.inscritos.dto.taskDtos.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.taskDtos.TaskUpdateDto;
import dev.matheuslf.desafio.inscritos.mappers.TaskMapper;
import dev.matheuslf.desafio.inscritos.model.PriorityTask;
import dev.matheuslf.desafio.inscritos.model.Project;
import dev.matheuslf.desafio.inscritos.model.StatusTask;
import dev.matheuslf.desafio.inscritos.model.Task;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Project project;
    private Task task;
    private TaskCreateDto taskCreateDto;
    private TaskUpdateDto taskUpdateDto;
    private TaskFilterDto taskFilterDto;
    private TaskResponseDto taskResponseDto;

    @BeforeEach
    void setUp() {
        project = new Project(
                2L,
                "Project test in task",
                "test project",
                LocalDate.now(), LocalDate.now()
        );

        task = new Task(
                1L,
                "Task Test",
                "Desc task project test",
                StatusTask.DONE,
                PriorityTask.HIGH,
                LocalDate.now(),
                project
        );

        taskCreateDto = new TaskCreateDto(
                "Project Test",
                "task project test create dto",
                StatusTask.DONE.toString(),
                PriorityTask.HIGH.toString(),
                LocalDate.now(),
                "1"
        );

        taskUpdateDto = new TaskUpdateDto(
                task.getId(),
                task.getStatus()
        );

        taskFilterDto = new TaskFilterDto(
                StatusTask.DONE,
                PriorityTask.HIGH,
                1L
        );

        taskResponseDto = new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getProject()
        );
    }

    @Test
    void tasksCreate() {
        when(projectRepository.findById(Long.parseLong(taskCreateDto.projectId()))).thenReturn(Optional.ofNullable(project));
        when(taskMapper.toEntityTask(any(TaskCreateDto.class))).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDto result = taskService.tasksCreate(taskCreateDto);

        assertNotNull(result, "Resultado não deveria ser nulo");
        assertEquals("Task Test", result.title());
        verify(projectRepository, times(1)).findById(anyLong());
//        verify(taskMapper, times(1)).toEntityTask(any(TaskCreateDto.class));
        verify(taskRepository, times(1)).save(any(Task.class));

        System.out.println("Teste realizado com sucesso, Task: " + result.title());
    }

    @Test
    void atualizeTask() {
        when(taskRepository.findById(taskUpdateDto.id())).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        task.setStatus(taskUpdateDto.statusTask());
        TaskResponseDto result = taskService.atualizeTask(
                taskUpdateDto.statusTask(), taskUpdateDto
                        .id());

        assertNotNull(result, "Resultado nao deve ser nulo.");
        assertEquals("Task Test", result.title());
        verify(taskRepository, times(1)).findById(taskUpdateDto.id());
        verify(taskRepository, times(1)).save(any(Task.class));

        System.out.println("Teste de atualizacao realizado com sucesso, Task: " + result.title());

    }

    @Test
    void getTasksWithFilters() {
        when(taskRepository.findByFilters(taskFilterDto.status(),
                taskFilterDto.priority(),
                taskFilterDto.projectId())).thenReturn(List.of(task));

        List<TaskResponseDto> taskResponseDtos = taskService.getTasksWithFilters(taskFilterDto);

        assertNotNull(taskResponseDtos, "Resultado nao deve ser nulo");
        verify(taskRepository, times(1)).findByFilters(taskFilterDto.status(),
                taskFilterDto.priority(),
                taskFilterDto.projectId());
    }

    @Test
    void taskList() {
        when(taskMapper.toResponseTaskDto(taskRepository.findAll())).thenReturn(List.of(taskResponseDto));

        List<TaskResponseDto> taskResponseDtoList = taskService.taskList();

        assertNotNull(taskResponseDtoList, "Lista nao pode ser nula");
        verify(taskMapper, times(1)).toResponseTaskDto(taskRepository.findAll());
        verify(taskRepository, times(3)).findAll();
    }

    @Test
    void deleteById() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.ofNullable(task));
        doNothing().when(taskRepository).delete(task);

        ResponseEntity<String> string = taskService.deleteById(task.getId());

        assertNotNull(string, "Resultado nao deve ser nulo");
        verify(taskRepository, times(1)).findById(task.getId());
        verify(taskRepository, times(1)).delete(task);

        System.out.println("Teste de deletar realizado com sucesso, retorno: " + string.toString());
    }
}
