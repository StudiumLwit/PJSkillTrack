package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.StatusType;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.equalTo;

public class StatisticsControllerTest extends AbstractDbTest {

    @Test
    void getStatusCountsPerFaculty() {
        testContextBuilder
                .withDefaultStudent()
                .withFaculty("Chirurgie")
                .withFaculty("Pädiatrie")
                .withSkill("Medikamente verschreiben", "", testContextBuilder.getFaculties().get(0), testContextBuilder.getFaculties().get(1))
                .withSkill("Blut abnehmen", "", testContextBuilder.getFaculties().get(0))
                .withProgress("", testContextBuilder.getSkills().get(0), testContextBuilder.getStudents().get(0))
                .withProgress("", testContextBuilder.getSkills().get(1), testContextBuilder.getStudents().get(0))
                .withStatusTransition(StatusType.DONE, new Timestamp(System.currentTimeMillis()), testContextBuilder.getProgresses().get(0))
                .withStatusTransition(StatusType.SEEN, new Timestamp(System.currentTimeMillis()), testContextBuilder.getProgresses().get(1))
                .build();

        givenDefaultStudent()
                .when()
                .get("/api/statistics/statusPerFaculty")
                .then()
                .statusCode(200)
                .body("Chirurgie.ROUTINE", equalTo(0))
                .body("Chirurgie.DONE", equalTo(1))
                .body("Chirurgie.SEEN", equalTo(1))
                .body("Chirurgie.UNDEFINED", equalTo(0))
                .body("Pädiatrie.ROUTINE", equalTo(0))
                .body("Pädiatrie.DONE", equalTo(1))
                .body("Pädiatrie.SEEN", equalTo(0))
                .body("Pädiatrie.UNDEFINED", equalTo(0));

    }
}
