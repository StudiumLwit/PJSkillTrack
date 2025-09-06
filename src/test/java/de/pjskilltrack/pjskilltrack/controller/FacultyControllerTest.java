package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.Faculty;
import de.pjskilltrack.pjskilltrack.util.TestContextBuilder;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class FacultyControllerTest extends AbstractDbTest {

    @Test
    void getAllFacultiesAlphabetically() {
        final TestContextBuilder.TestContext context = testContextBuilder
                .withDefaultStudent()
                .withFaculty("Chirurgie")
                .withFaculty("Innere Medizin")
                .withFaculty("Anatomie")
                .build();

        final List<String> sortedFacultyNames = context.getFaculties().stream()
                .sorted(Comparator.comparing(Faculty::getName))
                .map(Faculty::getName)
                .toList();

        givenDefaultStudent()
                .when()
                .get("/api/faculty")
                .then()
                .statusCode(200)
                .body("", hasSize(sortedFacultyNames.size()))
                .body("[0].name", equalTo(sortedFacultyNames.get(0)))
                .body("[1].name", equalTo(sortedFacultyNames.get(1)))
                .body("[2].name", equalTo(sortedFacultyNames.get(2)));
    }
}
