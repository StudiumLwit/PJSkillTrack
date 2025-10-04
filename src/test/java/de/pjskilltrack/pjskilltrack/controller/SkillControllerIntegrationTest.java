package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import de.pjskilltrack.pjskilltrack.util.TestContextBuilder;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SkillControllerIntegrationTest extends AbstractDbTest {

    @Test
    void getSkillsByFacultyName_empty() {
        testContextBuilder.withDefaultStudent().build();

        givenDefaultStudent()
                .when()
                .get("/api/skill?facultyName=Chirurgie")
                .then()
                .statusCode(200)
                .body("", empty());
    }

    @Test
    void getSkillsByFacultyName_oneEntry() {
        final TestContextBuilder.TestContext context = testContextBuilder
                .withDefaultStudent()
                .withFaculty("Chirurgie")
                .withSkill("Medikamente verschreiben", "Immer über Nebenwirkungen aufklären", testContextBuilder.getFaculties().get(0))
                .withProgress("Das habe ich noch nie gemacht", testContextBuilder.getSkills().get(0), testContextBuilder.getStudents().get(0))
                .withStatusTransition(StatusType.SEEN, new Timestamp(System.currentTimeMillis()), testContextBuilder.getProgresses().get(0))
                .build();

        givenDefaultStudent()
                .when()
                .get("/api/skill?facultyName=" + context.getFirstFaculty().getName())
                .then()
                .statusCode(200)
                .body("", hasSize(1))
                .body("[0].name", equalTo(context.getFirstSkill().getName()))
                .body("[0].description", equalTo(context.getFirstSkill().getDescription()))
                .body("[0].note", equalTo(context.getFirstProgress().getNote()))
                .body("[0].statusType", equalTo(context.getFirstStatusTransition().getNewStatus().name()));
    }

    @Test
    void getSkillsByFacultyName_unauthenticated() {
        given()
                .when()
                .get("/api/skill?facultyName=Chirurgie")
                .then()
                .statusCode(401);
    }

    @Test
    void updateSkill_existingProgress() {
        final TestContextBuilder.TestContext context = testContextBuilder
                .withDefaultStudent()
                .withFaculty("Chirurgie")
                .withSkill("Medikamente verschreiben", "Immer über Nebenwirkungen aufklären", testContextBuilder.getFaculties().get(0))
                .withProgress("Das habe ich noch nie gemacht", testContextBuilder.getSkills().get(0), testContextBuilder.getStudents().get(0))
                .withStatusTransition(StatusType.SEEN, new Timestamp(System.currentTimeMillis()), testContextBuilder.getProgresses().get(0))
                .build();

        final UpdateSkillOverviewDto updateSkillOverviewDto = new UpdateSkillOverviewDto("Ich habe das gemacht", StatusType.DONE);

        givenDefaultStudent()
                .body(updateSkillOverviewDto)
                .when()
                .put("/api/skill/" + context.getFirstSkill().getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(context.getFirstSkill().getName()))
                .body("description", equalTo(context.getFirstSkill().getDescription()))
                .body("note", equalTo(updateSkillOverviewDto.note()))
                .body("statusType", equalTo(updateSkillOverviewDto.statusType().name()));
    }

    @Test
    void updateSkill_noExistingProgress() {
        final TestContextBuilder.TestContext context = testContextBuilder
                .withDefaultStudent()
                .withFaculty("Chirurgie")
                .withSkill("Medikamente verschreiben", "Immer über Nebenwirkungen aufklären", testContextBuilder.getFaculties().get(0))
                .build();

        final UpdateSkillOverviewDto updateSkillOverviewDto = new UpdateSkillOverviewDto("Das sehe ich zum ersten Mal", StatusType.SEEN);

        givenDefaultStudent()
                .body(updateSkillOverviewDto)
                .when()
                .put("/api/skill/" + context.getFirstSkill().getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(context.getFirstSkill().getName()))
                .body("description", equalTo(context.getFirstSkill().getDescription()))
                .body("note", equalTo(updateSkillOverviewDto.note()))
                .body("statusType", equalTo(updateSkillOverviewDto.statusType().name()));
    }
}
