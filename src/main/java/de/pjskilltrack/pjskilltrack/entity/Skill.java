package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Skill
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "skills")
    private Set<Faculty> faculties;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Progress> progresses;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Set<Faculty> getFaculties()
    {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties)
    {
        this.faculties = faculties;
    }

    public Set<Progress> getProgresses()
    {
        return progresses;
    }

    public void setProgresses(Set<Progress> progresses)
    {
        this.progresses = progresses;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) && Objects.equals(name, skill.name) && Objects.equals(description,
            skill.description) && Objects.equals(faculties, skill.faculties) && Objects.equals(progresses,
            skill.progresses);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, description, faculties, progresses);
    }
}
