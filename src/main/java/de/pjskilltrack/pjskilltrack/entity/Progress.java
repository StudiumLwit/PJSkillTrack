package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Progress
{
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

    @Enumerated(EnumType.STRING)
    private StatusType status;
    private String note;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Skill getSkill()
    {
        return skill;
    }

    public void setSkill(Skill skill)
    {
        this.skill = skill;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    public StatusType getStatus()
    {
        return status;
    }

    public void setStatus(StatusType status)
    {
        this.status = status;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public Set<StatusTransition> getStatusTransitions()
    {
        return statusTransitions;
    }

    public void setStatusTransitions(Set<StatusTransition> statusTransitions)
    {
        this.statusTransitions = statusTransitions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Progress progress = (Progress) o;
        return Objects.equals(id, progress.id) && Objects.equals(skill, progress.skill) && Objects.equals(student,
            progress.student) && Objects.equals(statusTransitions,
            progress.statusTransitions) && status == progress.status && Objects.equals(note, progress.note);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, skill, student, statusTransitions, status, note);
    }
}
