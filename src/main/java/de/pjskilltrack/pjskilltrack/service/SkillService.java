package de.pjskilltrack.pjskilltrack.service;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    List<Skill> getAllSkillsByFacultyNameAlphabetically(String facultyName);

    Optional<Progress> getProgressForSkill(Skill skill);

    Skill updateSkill(Long skillId, UpdateSkillOverviewDto updateSkillOverviewDto);
}
