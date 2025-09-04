package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "skills")
    private Set<Faculty> faculties;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Progress> progresses;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(final Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    public Set<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(final Set<Progress> progresses) {
        this.progresses = progresses;
    }

    public void addProgress(final Progress progress) {
        this.progresses.add(progress);
        progress.setSkill(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) && Objects.equals(name, skill.name) && Objects.equals(description,
                skill.description) && Objects.equals(faculties, skill.faculties) && Objects.equals(progresses,
                skill.progresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, faculties, progresses);
    }
}
