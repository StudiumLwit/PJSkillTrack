package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.repository.*;
import de.pjskilltrack.pjskilltrack.util.TestContextBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractDbTest {

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private StatusTransitionRepository statusTransitionRepository;
    @Autowired
    private StudentRepository studentRepository;

    protected TestContextBuilder testContextBuilder;

    @LocalServerPort
    private Integer port;

    static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;

        this.testContextBuilder = new TestContextBuilder(progressRepository, skillRepository, statusTransitionRepository, facultyRepository, studentRepository);
    }

    @AfterEach
    void tearDown() {
        statusTransitionRepository.deleteAll();
        progressRepository.deleteAll();
        studentRepository.deleteAll();
        skillRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    protected static RequestSpecification givenDefaultStudent() {
        return given().auth().basic("student", "student").contentType(ContentType.JSON);
    }
}
