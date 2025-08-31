package de.pjskilltrack.pjskilltrack.service;

import de.pjskilltrack.pjskilltrack.entity.Skill;

import java.util.List;

public interface SkillService
{
    List<Skill> getAllSkillsByFacultyIdAlphabetically(Long facultyId);
}
