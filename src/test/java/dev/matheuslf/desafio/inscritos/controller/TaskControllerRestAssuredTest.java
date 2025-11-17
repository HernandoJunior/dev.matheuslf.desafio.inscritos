package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.taskDtos.TaskCreateDto;
import dev.matheuslf.desafio.inscritos.dto.userDto.UserCreateDto;
import dev.matheuslf.desafio.inscritos.dto.userDto.UserLoginDto;
import dev.matheuslf.desafio.inscritos.model.*;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import dev.matheuslf.desafio.inscritos.repository.UserRepository;
import dev.matheuslf.desafio.inscritos.service.UserService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TaskControllerRestAssuredTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    private Project project;
    private Task task;
    private String token;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/tasks";

        taskRepository.deleteAll();
        projectRepository.deleteAll();
        userRepository.deleteAll();

        UserCreateDto userDto = new UserCreateDto(
                "test@email.com",
                "test1234",
                "test1234"
        );
        userService.createUser(userDto);

        token = userService.login(new UserLoginDto(userDto.login(), userDto.password())).token();
        RestAssured.requestSpecification = given()
                .header("Authorization", "Bearer " + token);

        project = new Project(
                null,
                "Projeto para Tasks",
                "Projeto de teste",
                LocalDate.now(),
                LocalDate.now()
        );
        project = projectRepository.save(project);

        task = new Task(
                null,
                "Task Teste",
                "Descrição da task teste",
                StatusTask.TODO,
                PriorityTask.HIGH,
                LocalDate.now(),
                project
        );
        task = taskRepository.save(task);
    }

    @Test
    void createTaskWithSucess() {
        TaskCreateDto taskDto = new TaskCreateDto(
                "Nova Task",
                "Descrição da nova task",
                StatusTask.TODO.toString(),
                PriorityTask.MEDIUM.toString(),
                LocalDate.now(),
                project.getId().toString()
        );

        given()
                .contentType(ContentType.JSON)
                .body(taskDto)
                .when()
                .post()
                .then()
                .body("title", equalTo("Nova Task"))
                .body("description", equalTo("Descrição da nova task"))
                .body("status", equalTo("TODO"))
                .body("priority", equalTo("MEDIUM"));
    }

    @Test
    void return500AtProjectsNotExist() {
        TaskCreateDto taskDto = new TaskCreateDto(
                "Task Inválida",
                "Descrição",
                StatusTask.TODO.toString(),
                PriorityTask.LOW.toString(),
                LocalDate.now(),
                "99999"
        );

        given()
                .contentType(ContentType.JSON)
                .body(taskDto)
                .when()
                .post()
                .then()
                .statusCode(400);
    }

    @Test
    void statusTaskAtt() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("status", StatusTask.DOING)
                .queryParam("id", task.getId())
                .when()
                .put()
                .then()
                .statusCode(200)
                .body("id", equalTo(task.getId().intValue()))
                .body("status", equalTo("DOING"))
                .body("title", equalTo("Task Teste"));
    }

    @Test
    void return500ToTaskInviable() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("status", StatusTask.DONE)
                .queryParam("id", 99999L)
                .when()
                .put()
                .then()
                .statusCode(400);
    }

    @Test
    void listAllTasks() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/list")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].title", equalTo("Task Teste"))
                .body("[0].status", equalTo("TODO"));
    }

    @Test
    void filterTaskWithStatus() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("status", StatusTask.TODO)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].status", equalTo("TODO"));
    }

    @Test
    void filterTaskWithPriority() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("priority", PriorityTask.HIGH)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].priority", equalTo("HIGH"));
    }

    @Test
    void filterTasksWithProjectsId() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("projectId", project.getId())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(1)))
                .body("[0].project.id", equalTo(project.getId().intValue()));
    }

    @Test
    void filterWithParams() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("status", StatusTask.TODO)
                .queryParam("priority", PriorityTask.HIGH)
                .queryParam("projectId", project.getId())
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].title", equalTo("Task Teste"));
    }

    @Test
    void returnEmptyArray() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("status", StatusTask.DONE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void deleteTaskWithSucess() {
        given()
                .pathParam("id", task.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .body(containsString("deletada com sucesso"));
    }

    @Test
    void return500ToDeleteTaskEmpty() {
        given()
                .pathParam("id", 99999L)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(400);
    }
}