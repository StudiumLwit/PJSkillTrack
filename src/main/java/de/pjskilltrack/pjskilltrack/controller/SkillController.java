package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.service.SkillService;
import de.pjskilltrack.pjskilltrack.transfer.SkillOverviewDto;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import de.pjskilltrack.pjskilltrack.transfer.converter.SkillConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill")
public class SkillController {
    private final SkillService skillService;
    private final SkillConverter skillConverter;

    @Autowired
    public SkillController(final SkillService skillService, final SkillConverter skillConverter) {
        this.skillService = skillService;
        this.skillConverter = skillConverter;
    }

    @GetMapping()
    public List<SkillOverviewDto> getSkillsByFacultyName(@RequestParam final String facultyName) {
        return skillService.getAllSkillsByFacultyNameAlphabetically(facultyName)
                .stream()
                .map(skillConverter::convertSkillToSkillOverviewDto)
                .toList();
    }

    @PutMapping("/{skillId}")
    public SkillOverviewDto updateSkill(@PathVariable final Long skillId, @RequestBody final UpdateSkillOverviewDto updateSkillOverviewDto) {
        final Skill updatedSkill = skillService.updateSkill(skillId, updateSkillOverviewDto);
        return skillConverter.convertSkillToSkillOverviewDto(updatedSkill);
    }
}
