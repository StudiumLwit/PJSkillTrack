package de.pjskilltrack.pjskilltrack.repository;

import de.pjskilltrack.pjskilltrack.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>
{
}
