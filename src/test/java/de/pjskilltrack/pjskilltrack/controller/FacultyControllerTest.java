package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.controller.impl.FacultyController;
import de.pjskilltrack.pjskilltrack.entity.Faculty;
import de.pjskilltrack.pjskilltrack.service.FacultyService;
import de.pjskilltrack.pjskilltrack.transfer.FacultyDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyControllerTest {
    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    void getAllFacultiesAlphabetically_mapsEntitiesToDtos_preservesOrder() {
        final Faculty f1 = new Faculty();
        f1.setName("Chirurgie");
        final Faculty f2 = new Faculty();
        f2.setName("Neurologie");
        when(facultyService.getAllFacultiesAlphabetically()).thenReturn(List.of(f1, f2));

        final List<FacultyDto> result = facultyController.getAllFacultiesAlphabetically();

        assertThat(result)
                .hasSize(2)
                .extracting(FacultyDto::name)
                .containsExactly("Chirurgie", "Neurologie");

        verify(facultyService, times(1)).getAllFacultiesAlphabetically();
        verifyNoMoreInteractions(facultyService);
    }

    @Test
    void getAllFacultiesAlphabetically_returnsEmptyList_whenServiceEmpty() {
        when(facultyService.getAllFacultiesAlphabetically()).thenReturn(List.of());

        final List<FacultyDto> result = facultyController.getAllFacultiesAlphabetically();

        assertThat(result).isEmpty();
        verify(facultyService).getAllFacultiesAlphabetically();
        verifyNoMoreInteractions(facultyService);
    }

    @Test
    void getAllFacultiesAlphabetically_mapsNullNameSafely() {
        // faculty name does not exist
        final Faculty broken = new Faculty();
        when(facultyService.getAllFacultiesAlphabetically()).thenReturn(List.of(broken));

        final List<FacultyDto> result = facultyController.getAllFacultiesAlphabetically();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isNull();
        verify(facultyService).getAllFacultiesAlphabetically();
    }
}
