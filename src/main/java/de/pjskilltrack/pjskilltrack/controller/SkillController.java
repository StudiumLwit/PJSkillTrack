package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.service.SkillService;
import de.pjskilltrack.pjskilltrack.transfer.SkillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skill")
public class SkillController
{
    SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService)
    {
        this.skillService = skillService;
    }

    @GetMapping()
    public List<SkillDto> getSkillsByFacultyId(@RequestParam Long facultyId)
    {
        return skillService.getAllSkillsByFacultyIdAlphabetically(facultyId).stream().map(skill -> new SkillDto(skill.getName(), skill.getDescription())).toList();
    }
}
