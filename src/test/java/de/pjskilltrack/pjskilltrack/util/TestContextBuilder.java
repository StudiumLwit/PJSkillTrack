package de.pjskilltrack.pjskilltrack.util;

import de.pjskilltrack.pjskilltrack.entity.*;
import de.pjskilltrack.pjskilltrack.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestContextBuilder {

    private final ProgressRepository progressRepository;
    private final SkillRepository skillRepository;
    private final StatusTransitionRepository statusTransitionRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    private final List<Student> students = new ArrayList<>();
    private final List<Faculty> faculties = new ArrayList<>();
    private final List<Skill> skills = new ArrayList<>();
    private final List<Progress> progresses = new ArrayList<>();
    private final List<StatusTransition> statusTransitions = new ArrayList<>();

    public TestContextBuilder(final ProgressRepository progressRepository, final SkillRepository skillRepository, final StatusTransitionRepository statusTransitionRepository, final FacultyRepository facultyRepository, final StudentRepository studentRepository) {
        this.progressRepository = progressRepository;
        this.skillRepository = skillRepository;
        this.statusTransitionRepository = statusTransitionRepository;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public TestContextBuilder withDefaultStudent() {
        final Student student = new Student();
        student.setEmail("student");
        student.setName("Student");
        student.setPassword("$2a$12$buvjYttpZPdMcjeZQ5FXDeZIolHUG5AoEsbbP29OtcIpLd1zlAzFW"); // password: student
        students.add(studentRepository.save(student));
        return this;
    }

    public TestContextBuilder withFaculty(final String facultyName) {
        final Faculty faculty = new Faculty();
        faculty.setName(facultyName);
        faculties.add(facultyRepository.save(faculty));
        return this;
    }

    public TestContextBuilder withSkill(final String skillName, final String skillDescription, final Faculty... faculties) {
        final Skill skill = new Skill();
        skill.setName(skillName);
        skill.setDescription(skillDescription);
        skill.setFaculties(Set.of(faculties));
        skills.add(skillRepository.save(skill));
        return this;
    }

    public TestContextBuilder withProgress(final String note, final Skill skill, final Student student) {
        final Progress progress = new Progress();
        progress.setSkill(skill);
        progress.setStudent(student);
        progress.setNote(note);
        progresses.add(progressRepository.save(progress));
        return this;
    }

    public TestContextBuilder withStatusTransition(final StatusType newStatus, final Timestamp changeTime, final Progress progress) {
        final StatusTransition statusTransition = new StatusTransition();
        statusTransition.setProgress(progress);
        statusTransition.setNewStatus(newStatus);
        statusTransition.setChangeTime(changeTime);
        statusTransitions.add(statusTransitionRepository.save(statusTransition));
        return this;
    }

    public TestContext build() {
        return new TestContext(students, faculties, skills, progresses, statusTransitions);
    }

    public void tearDown() {
        statusTransitionRepository.deleteAll();
        progressRepository.deleteAll();
        skillRepository.deleteAll();
        facultyRepository.deleteAll();
        studentRepository.deleteAll();

        faculties.clear();
        skills.clear();
        progresses.clear();
        statusTransitions.clear();
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Progress> getProgresses() {
        return progresses;
    }

    public List<StatusTransition> getStatusTransitions() {
        return statusTransitions;
    }

    public static class TestContext {

        private final List<Student> students;
        private final List<Faculty> faculties;
        private final List<Skill> skills;
        private final List<Progress> progresses;
        private final List<StatusTransition> statusTransitions;

        public TestContext(final List<Student> students, final List<Faculty> faculties, final List<Skill> skills, final List<Progress> progresses, final List<StatusTransition> statusTransitions) {
            this.students = students;
            this.faculties = faculties;
            this.skills = skills;
            this.progresses = progresses;
            this.statusTransitions = statusTransitions;
        }

        public List<Student> getStudents() {
            return students;
        }

        public List<Faculty> getFaculties() {
            return faculties;
        }

        public List<Skill> getSkills() {
            return skills;
        }

        public List<Progress> getProgresses() {
            return progresses;
        }

        public List<StatusTransition> getStatusTransitions() {
            return statusTransitions;
        }

        public Student getFirstStudent() {
            return students.get(0);
        }

        public Faculty getFirstFaculty() {
            return faculties.get(0);
        }

        public Skill getFirstSkill() {
            return skills.get(0);
        }

        public Progress getFirstProgress() {
            return progresses.get(0);
        }

        public StatusTransition getFirstStatusTransition() {
            return statusTransitions.get(0);
        }
    }
}
