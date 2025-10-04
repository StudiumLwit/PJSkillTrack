package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.*;
import de.pjskilltrack.pjskilltrack.exception.SkillNotFoundException;
import de.pjskilltrack.pjskilltrack.repository.ProgressRepository;
import de.pjskilltrack.pjskilltrack.repository.SkillRepository;
import de.pjskilltrack.pjskilltrack.repository.StatusTransitionRepository;
import de.pjskilltrack.pjskilltrack.service.ProgressService;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;
    @Mock
    private ProgressRepository progressRepository;
    @Mock
    private StatusTransitionRepository statusTransitionRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private ProgressService progressService;

    @InjectMocks
    private SkillServiceImpl skillService;

    private Student DEFAULT_STUDENT;
    private Skill DEFAULT_SKILL;

    @BeforeEach
    void setUp() {
        DEFAULT_STUDENT = new Student();
        DEFAULT_STUDENT.setId(42L);
        DEFAULT_STUDENT.setName("Max Mustermann");

        DEFAULT_SKILL = new Skill();
        DEFAULT_SKILL.setId(7L);
        DEFAULT_SKILL.setName("Unit Testing");
    }


    @Test
    void getAllSkillsByFacultyNameAlphabetically_returnsList() {
        final String faculty = "Chirurgie";
        final Skill s1 = new Skill();
        s1.setName("Anamnesegespräch führen");
        final Skill s2 = new Skill();
        s2.setName("Blut abnehmen");
        when(skillRepository.findByFacultiesNameOrderByName(faculty))
                .thenReturn(List.of(s1, s2));

        final List<Skill> result = skillService.getAllSkillsByFacultyNameAlphabetically(faculty);

        assertThat(result).containsExactly(s1, s2);
        verify(skillRepository).findByFacultiesNameOrderByName(faculty);
        verifyNoMoreInteractions(skillRepository, progressRepository, statusTransitionRepository, studentService, progressService);
    }

    @Test
    void getProgressForSkill_usesCurrentStudent() {
        final Progress progress = new Progress(DEFAULT_SKILL, DEFAULT_STUDENT);
        when(studentService.getCurrentStudent()).thenReturn(DEFAULT_STUDENT);
        when(progressRepository.findByStudentAndSkill(DEFAULT_STUDENT, DEFAULT_SKILL)).thenReturn(Optional.of(progress));

        final Optional<Progress> opt = skillService.getProgressForSkill(DEFAULT_SKILL);

        assertThat(opt).contains(progress);
        verify(studentService).getCurrentStudent();
        verify(progressRepository).findByStudentAndSkill(DEFAULT_STUDENT, DEFAULT_SKILL);
        verifyNoMoreInteractions(skillRepository, statusTransitionRepository, progressService);
    }

    @Test
    void updateSkill_skillNotFound_throws() {
        final long missingId = 999L;
        when(studentService.getCurrentStudent()).thenReturn(DEFAULT_STUDENT);
        when(skillRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                skillService.updateSkill(missingId, new UpdateSkillOverviewDto("note", StatusType.DONE)))
                .isInstanceOf(SkillNotFoundException.class);

        verify(skillRepository).findById(missingId);
        verifyNoMoreInteractions(progressRepository, statusTransitionRepository, progressService);
    }

    @Test
    void updateSkill_existingProgress_statusUnchanged_noTransition() {
        final UpdateSkillOverviewDto dto = new UpdateSkillOverviewDto("Neue Notiz", StatusType.DONE);
        final Progress existingProgress = new Progress(DEFAULT_SKILL, DEFAULT_STUDENT);

        when(studentService.getCurrentStudent()).thenReturn(DEFAULT_STUDENT);
        when(skillRepository.findById(DEFAULT_SKILL.getId())).thenReturn(Optional.of(DEFAULT_SKILL));
        when(progressRepository.findByStudentAndSkill(DEFAULT_STUDENT, DEFAULT_SKILL)).thenReturn(Optional.of(existingProgress));
        when(progressService.getCurrentStatus(existingProgress)).thenReturn(StatusType.DONE);

        final Skill returnedSkill = skillService.updateSkill(DEFAULT_SKILL.getId(), dto);

        assertThat(returnedSkill).isEqualTo(DEFAULT_SKILL);
        assertThat(existingProgress.getNote()).isEqualTo("Neue Notiz");
        verify(statusTransitionRepository, never()).save(any(StatusTransition.class));
        verify(progressRepository, times(1)).save(existingProgress);
    }

    @Test
    void updateSkill_noExistingProgress_createsAndSavesProgress() {
        final UpdateSkillOverviewDto dto = new UpdateSkillOverviewDto("Notiz", StatusType.SEEN);
        when(studentService.getCurrentStudent()).thenReturn(DEFAULT_STUDENT);
        when(skillRepository.findById(DEFAULT_SKILL.getId())).thenReturn(Optional.of(DEFAULT_SKILL));
        when(progressRepository.findByStudentAndSkill(DEFAULT_STUDENT, DEFAULT_SKILL)).thenReturn(Optional.empty());
        when(progressService.getCurrentStatus(any(Progress.class))).thenReturn(null);
        // Capture the "saved" progress
        final Answer<Progress> saveAnswer = invocation -> (Progress) invocation.getArgument(0);
        when(progressRepository.save(any(Progress.class))).thenAnswer(saveAnswer);

        final Skill returnedSkill = skillService.updateSkill(DEFAULT_SKILL.getId(), dto);

        assertThat(returnedSkill).isEqualTo(DEFAULT_SKILL);
        // once for initially creating progress, once for updating it
        verify(progressRepository, times(2)).save(any(Progress.class));
        verify(statusTransitionRepository).save(any());
    }

    @Test
    void updateSkill_statusChanged_savesTransition() {
        final UpdateSkillOverviewDto dto = new UpdateSkillOverviewDto("Notiz", StatusType.DONE);
        final Progress existingProgress = new Progress(DEFAULT_SKILL, DEFAULT_STUDENT);
        when(studentService.getCurrentStudent()).thenReturn(DEFAULT_STUDENT);
        when(skillRepository.findById(DEFAULT_SKILL.getId())).thenReturn(Optional.of(DEFAULT_SKILL));
        when(progressRepository.findByStudentAndSkill(DEFAULT_STUDENT, DEFAULT_SKILL)).thenReturn(Optional.of(existingProgress));
        // new status != old status
        when(progressService.getCurrentStatus(existingProgress)).thenReturn(StatusType.SEEN);

        skillService.updateSkill(DEFAULT_SKILL.getId(), dto);

        final ArgumentCaptor<StatusTransition> captor = ArgumentCaptor.forClass(StatusTransition.class);
        verify(statusTransitionRepository).save(captor.capture());
        final StatusTransition transition = captor.getValue();

        assertThat(transition.getProgress()).isEqualTo(existingProgress);
        assertThat(transition.getNewStatus()).isEqualTo(StatusType.DONE);
        assertThat(transition.getChangeTime()).isNotNull();

        verify(progressRepository).save(existingProgress);
    }
}
