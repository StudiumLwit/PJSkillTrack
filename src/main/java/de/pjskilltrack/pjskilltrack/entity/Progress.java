package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToMany(mappedBy = "progress", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StatusTransition> statusTransitions;

    private String note;

    public Progress(final Skill skill, final Student student) {
        this.skill = skill;
        this.student = student;
    }

    public Progress() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(final Skill skill) {
        this.skill = skill;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(final Student student) {
        this.student = student;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public Set<StatusTransition> getStatusTransitions() {
        return statusTransitions;
    }

    public void setStatusTransitions(final Set<StatusTransition> statusTransitions) {
        this.statusTransitions = statusTransitions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Progress progress = (Progress) o;
        return Objects.equals(id, progress.id) && Objects.equals(skill, progress.skill) && Objects.equals(student,
                progress.student) && Objects.equals(statusTransitions,
                progress.statusTransitions) && Objects.equals(note, progress.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skill, student, statusTransitions, note);
    }
}
