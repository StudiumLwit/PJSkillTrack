package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.Student;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class StudentControllerIntegrationTest extends AbstractDbTest {


    @Test
    void me_authenticated() {
        final Student student = testContextBuilder
                .withDefaultStudent()
                .build()
                .getFirstStudent();

        givenDefaultStudent()
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(200)
                .body("email", equalTo(student.getEmail()))
                .body("name", equalTo(student.getName()));
    }

    @Test
    void me_unauthenticated() {
        // Notice that we do not set up any student in the context here

        givenDefaultStudent()
                .when()
                .get("/api/auth/me")
                .then()
                .statusCode(401);
    }
}
