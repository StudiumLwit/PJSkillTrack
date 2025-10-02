package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.transfer.SkillOverviewDto;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/skill")
public interface SkillOperations {
    @GetMapping()
    List<SkillOverviewDto> getSkillsByFacultyName(@RequestParam final String facultyName);

    @PutMapping("/{skillId}")
    SkillOverviewDto updateSkill(@PathVariable final Long skillId, @RequestBody final UpdateSkillOverviewDto updateSkillOverviewDto);
}
