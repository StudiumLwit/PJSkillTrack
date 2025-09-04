package de.pjskilltrack.pjskilltrack.transfer.converter;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.service.ProgressService;
import de.pjskilltrack.pjskilltrack.service.SkillService;
import de.pjskilltrack.pjskilltrack.transfer.SkillOverviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SkillConverter {
    private final SkillService skillService;
    private final ProgressService progressService;

    @Autowired
    public SkillConverter(final SkillService skillService, final ProgressService progressService) {
        this.skillService = skillService;
        this.progressService = progressService;
    }

    public SkillOverviewDto convertSkillToSkillOverviewDto(final Skill skill) {
        final Optional<Progress> optProgress = skillService.getProgressForSkill(skill);
        if (optProgress.isPresent()) {
            final Progress progress = optProgress.get();
            return new SkillOverviewDto(skill.getId(), skill.getName(), skill.getDescription(),
                    progressService.getCurrentStatus(progress), progress.getNote(), progressService.getLatestModification(progress));
        } else {
            return new SkillOverviewDto(skill.getId(), skill.getName(), skill.getDescription(),
                    StatusType.UNDEFINED, "", null);
        }
    }
}
