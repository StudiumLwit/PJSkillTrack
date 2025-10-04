package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.controller.impl.SkillController;
import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.service.SkillService;
import de.pjskilltrack.pjskilltrack.transfer.SkillOverviewDto;
import de.pjskilltrack.pjskilltrack.transfer.UpdateSkillOverviewDto;
import de.pjskilltrack.pjskilltrack.transfer.converter.SkillConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest {
    @Mock
    private SkillService skillService;
    @Mock
    private SkillConverter skillConverter;

    @InjectMocks
    private SkillController skillController;

    @Test
    void getSkillsByFacultyName_mapsAllSkillsWithOrder() {
        final String faculty = "Chirurgie";
        final Skill s1 = new Skill();
        s1.setId(1L);
        s1.setName("Anamnesegespräch führen");
        s1.setDescription("Freundlich sein");
        final Skill s2 = new Skill();
        s2.setId(2L);
        s2.setName("Blut abnehmen");
        s2.setDescription("Vor dem Pieks warnen");
        when(skillService.getAllSkillsByFacultyNameAlphabetically(faculty)).thenReturn(List.of(s1, s2));
        final SkillOverviewDto d1 = new SkillOverviewDto(1L, "Anamnesegespräch führen", "Freundlich sein", null, null, null);
        final SkillOverviewDto d2 = new SkillOverviewDto(2L, "Blut abnehmen", "Vor dem Pieks warnen", null, null, null);
        when(skillConverter.convertSkillToSkillOverviewDto(s1)).thenReturn(d1);
        when(skillConverter.convertSkillToSkillOverviewDto(s2)).thenReturn(d2);

        final List<SkillOverviewDto> result = skillController.getSkillsByFacultyName(faculty);

        assertThat(result).containsExactly(d1, d2);
        verify(skillService).getAllSkillsByFacultyNameAlphabetically(faculty);
        verify(skillConverter).convertSkillToSkillOverviewDto(s1);
        verify(skillConverter).convertSkillToSkillOverviewDto(s2);
        verifyNoMoreInteractions(skillService, skillConverter);
    }

    @Test
    void getSkillsByFacultyName_emptyList() {
        final String faculty = "Chirurgie";
        when(skillService.getAllSkillsByFacultyNameAlphabetically(faculty)).thenReturn(List.of());

        final List<SkillOverviewDto> result = skillController.getSkillsByFacultyName(faculty);

        assertThat(result).isEmpty();
        verify(skillService).getAllSkillsByFacultyNameAlphabetically(faculty);
        verifyNoInteractions(skillConverter);
    }

    @Test
    void updateSkill_delegatesAndConverts() {
        final Long skillId = 1L;
        final UpdateSkillOverviewDto dto = new UpdateSkillOverviewDto("note", StatusType.DONE);
        final Skill updated = new Skill();
        updated.setId(skillId);
        updated.setName("Skill");
        when(skillService.updateSkill(anyLong(), any(UpdateSkillOverviewDto.class))).thenReturn(updated);
        final SkillOverviewDto mapped = new SkillOverviewDto(skillId, "Skill", null, StatusType.DONE, "note", null);
        when(skillConverter.convertSkillToSkillOverviewDto(updated)).thenReturn(mapped);

        final SkillOverviewDto result = skillController.updateSkill(skillId, dto);

        assertThat(result).isSameAs(mapped);
        final ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        final ArgumentCaptor<UpdateSkillOverviewDto> dtoCaptor = ArgumentCaptor.forClass(UpdateSkillOverviewDto.class);
        verify(skillService).updateSkill(idCaptor.capture(), dtoCaptor.capture());
        assertThat(idCaptor.getValue()).isEqualTo(skillId);
        assertThat(dtoCaptor.getValue()).isSameAs(dto);

        verify(skillConverter).convertSkillToSkillOverviewDto(updated);
        verifyNoMoreInteractions(skillService, skillConverter);
    }
}
