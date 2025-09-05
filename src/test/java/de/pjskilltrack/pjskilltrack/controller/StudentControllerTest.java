package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.Student;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StudentControllerTest extends AbstractDbTest {


    @Test
    void me_authenticated() {
        final Student student = testDataFactory.getAuthenticatedStudent();

        givenStudent()
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(200)
                .body("email", equalTo(student.getEmail()))
                .body("name", equalTo(student.getName()));
    }

    @Test
    void me_unauthenticated() {
        given()
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(401);
    }
}
