package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @ManyToMany(mappedBy = "faculties")
    private Set<Skill> skills = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(final Set<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(final Skill skill) {
        this.skills.add(skill);
        skill.getFaculties().add(this);
    }

    public void removeSkill(final Skill skill) {
        this.skills.remove(skill);
        skill.getFaculties().remove(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Faculty faculty = (Faculty) o;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name) && Objects.equals(skills,
                faculty.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, skills);
    }
}
