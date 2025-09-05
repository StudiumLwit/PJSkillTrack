package de.pjskilltrack.pjskilltrack.util;

import de.pjskilltrack.pjskilltrack.entity.*;
import de.pjskilltrack.pjskilltrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Component
public class TestContextManager {
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private StatusTransitionRepository statusTransitionRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;

    public void createAuthenticatedStudent() {
        final Student student = new Student();
        student.setEmail("student");
        student.setName("Student");
        student.setPassword("$2a$12$buvjYttpZPdMcjeZQ5FXDeZIolHUG5AoEsbbP29OtcIpLd1zlAzFW"); // password: student
        studentRepository.save(student);
    }

    public Student getAuthenticatedStudent() {
        // In AbstractDbTest, there is always one student created
        return studentRepository.findAll().stream().findFirst().orElseThrow();
    }

    private Faculty createFaculty(final String facultyName) {
        final Faculty faculty = new Faculty();
        faculty.setName(facultyName);
        return facultyRepository.save(faculty);
    }

    private Skill createSkill(final String skillName, final String skillDescription, final Faculty... faculties) {
        final Skill skill = new Skill();
        skill.setName(skillName);
        skill.setDescription(skillDescription);
        skill.setFaculties(Set.of(faculties));
        return skillRepository.save(skill);
    }

    private Progress createProgress(final String note, final Skill skill) {
        final Progress progress = new Progress();
        progress.setSkill(skill);
        progress.setStudent(getAuthenticatedStudent());
        progress.setNote(note);
        return progressRepository.save(progress);
    }

    private StatusTransition createStatusTransition(final StatusType newStatus, final Timestamp changeTime, final Progress progress) {
        final StatusTransition statusTransition = new StatusTransition();
        statusTransition.setProgress(progress);
        statusTransition.setNewStatus(newStatus);
        statusTransition.setChangeTime(changeTime);
        return statusTransitionRepository.save(statusTransition);
    }


    public TestContext skillFacultyProgressAndStatusTransitionEach() {
        final Faculty faculty = createFaculty("Chirurgie");
        final Skill skill = createSkill("Medikamente verschreiben", "Immer über Nebenwirkungen aufklären", faculty);
        final Progress progress = createProgress("Das habe ich noch nie gemacht", skill);
        final StatusTransition statusTransition = createStatusTransition(StatusType.SEEN, new Timestamp(System.currentTimeMillis()), progress);

        return new TestContext(List.of(faculty), List.of(skill), List.of(progress), List.of(statusTransition));
    }

    public TestContext oneSkillAndFacultyEach() {
        final Faculty faculty = createFaculty("Chirurgie");
        final Skill skill = createSkill("Medikamente verschreiben", "Immer über Nebenwirkungen aufklären", faculty);
        return new TestContext(List.of(faculty), List.of(skill), List.of(), List.of());
    }

    public TestContext threeUnsortedFaculties() {
        final Faculty faculty1 = createFaculty("Chirurgie");
        final Faculty faculty2 = createFaculty("Pädiatrie");
        final Faculty faculty3 = createFaculty("Innere Medizin");
        return new TestContext(List.of(faculty1, faculty2, faculty3), List.of(), List.of(), List.of());
    }

    public void tearDown() {
        statusTransitionRepository.deleteAll();
        progressRepository.deleteAll();
        skillRepository.deleteAll();
        facultyRepository.deleteAll();
        studentRepository.deleteAll();
    }

    public static class TestContext {
        public List<Faculty> faculties;
        public List<Skill> skills;
        public List<Progress> progresses;
        public List<StatusTransition> statusTransitions;

        public TestContext(final List<Faculty> faculties, final List<Skill> skills, final List<Progress> progresses, final List<StatusTransition> statusTransitions) {
            this.faculties = faculties;
            this.skills = skills;
            this.progresses = progresses;
            this.statusTransitions = statusTransitions;

        }

    }
}
