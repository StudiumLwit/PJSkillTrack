package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.*;
import de.pjskilltrack.pjskilltrack.exception.SkillNotFoundException;
import de.pjskilltrack.pjskilltrack.repository.ProgressRepository;
import de.pjskilltrack.pjskilltrack.repository.SkillRepository;
import de.pjskilltrack.pjskilltrack.repository.StatusTransitionRepository;
import de.pjskilltrack.pjskilltrack.service.ProgressService;
import de.pjskilltrack.pjskilltrack.service.SkillService;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final ProgressRepository progressRepository;
    private final StatusTransitionRepository statusTransitionRepository;
    private final StudentService studentService;
    private final ProgressService progressService;

    @Autowired
    public SkillServiceImpl(final SkillRepository skillRepository, final ProgressRepository progressRepository,
                            final StatusTransitionRepository statusTransitionRepository, final StudentService studentService,
                            final ProgressService progressService) {
        this.skillRepository = skillRepository;
        this.progressRepository = progressRepository;
        this.statusTransitionRepository = statusTransitionRepository;
        this.studentService = studentService;
        this.progressService = progressService;
    }

    @Override
    public List<Skill> getAllSkillsByFacultyNameAlphabetically(final String facultyName) {
        return skillRepository.findByFacultiesNameOrderByName(facultyName);
    }

    @Override
    public Optional<Progress> getProgressForSkill(final Skill skill) {
        final Student loggedInStudent = studentService.getCurrentStudent();
        return progressRepository.findByStudentAndSkill(loggedInStudent, skill);
    }

    @Override
    public Skill updateSkill(final Long skillId, final UpdateSkillOverviewDto updateSkillOverviewDto) {
        final Student loggedInStudent = studentService.getCurrentStudent();

        final Skill skillToUpdate =
                skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException(skillId));
        final Optional<Progress> optProgressToUpdate = progressRepository.findByStudentAndSkill(loggedInStudent, skillToUpdate);
        final Progress progressToUpdate = optProgressToUpdate.orElseGet(() -> progressRepository.save(new Progress(skillToUpdate, loggedInStudent)));

        progressToUpdate.setNote(updateSkillOverviewDto.note());
        final StatusType oldStatus = progressService.getCurrentStatus(progressToUpdate);

        if (oldStatus != updateSkillOverviewDto.statusType()) {
            final StatusTransition newStatusTransition = new StatusTransition();
            newStatusTransition.setProgress(progressToUpdate);
            newStatusTransition.setNewStatus(updateSkillOverviewDto.statusType());
            newStatusTransition.setChangeTime(new Timestamp(System.currentTimeMillis()));
            statusTransitionRepository.save(newStatusTransition);
        }

        progressRepository.save(progressToUpdate);
        return skillToUpdate;
    }
}
