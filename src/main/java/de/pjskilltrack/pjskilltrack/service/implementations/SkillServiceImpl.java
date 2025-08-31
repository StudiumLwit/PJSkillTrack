package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.repository.SkillRepository;
import de.pjskilltrack.pjskilltrack.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService
{
    private final SkillRepository skillRepository;

    @Autowired
    public SkillServiceImpl(SkillRepository skillRepository)
    {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> getAllSkillsByFacultyIdAlphabetically(Long facultyId)
    {
        return skillRepository.findByFacultiesIdOrderByName(facultyId);
    }
}
