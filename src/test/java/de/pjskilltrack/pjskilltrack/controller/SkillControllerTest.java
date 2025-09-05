package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import de.pjskilltrack.pjskilltrack.util.TestContextManager;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SkillControllerTest extends AbstractDbTest {

    @Test
    void getSkillsByFacultyId_empty() {
        givenStudent()
                .when()
                .get("/api/skill?facultyId=1")
                .then()
                .statusCode(200)
                .body("", empty());
    }

    @Test
    void getSkillsByFacultyId_oneEntry() {
        final TestContextManager.TestContext context = testContextManager.skillFacultyProgressAndStatusTransitionEach();

        givenStudent()
                .when()
                .get("/api/skill?facultyId=" + context.faculties.get(0).getId())
                .then()
                .statusCode(200)
                .body("", hasSize(1))
                .body("[0].name", equalTo(context.skills.get(0).getName()))
                .body("[0].description", equalTo(context.skills.get(0).getDescription()))
                .body("[0].note", equalTo(context.progresses.get(0).getNote()))
                .body("[0].statusType", equalTo(context.statusTransitions.get(0).getNewStatus().name()));
    }

    @Test
    void getSkillsByFacultyId_unauthenticated() {
        given()
                .when()
                .get("/api/skill?facultyId=1")
                .then()
                .statusCode(401);
    }

    @Test
    void updateSkill_existingProgress() {
        final TestContextManager.TestContext context = testContextManager.skillFacultyProgressAndStatusTransitionEach();
        final Skill contextSkill = context.skills.get(0);

        final UpdateSkillOverviewDto updateSkillOverviewDto = new UpdateSkillOverviewDto("Ich habe das gemacht", StatusType.DONE);

        givenStudent()
                .body(updateSkillOverviewDto)
                .when()
                .put("/api/skill/" + contextSkill.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(contextSkill.getName()))
                .body("description", equalTo(contextSkill.getDescription()))
                .body("note", equalTo(updateSkillOverviewDto.note()))
                .body("statusType", equalTo(updateSkillOverviewDto.statusType().name()));
    }

    @Test
    void updateSkill_noExistingProgress() {
        final TestContextManager.TestContext context = testContextManager.oneSkillAndFacultyEach();

        final UpdateSkillOverviewDto updateSkillOverviewDto = new UpdateSkillOverviewDto("Das sehe ich zum ersten Mal", StatusType.SEEN);

        givenStudent()
                .body(updateSkillOverviewDto)
                .when()
                .put("/api/skill/" + context.skills.get(0).getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(context.skills.get(0).getName()))
                .body("description", equalTo(context.skills.get(0).getDescription()))
                .body("note", equalTo(updateSkillOverviewDto.note()))
                .body("statusType", equalTo(updateSkillOverviewDto.statusType().name()));
    }
}
