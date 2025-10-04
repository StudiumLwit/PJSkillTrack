package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Faculty;
import de.pjskilltrack.pjskilltrack.repository.FacultyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {
    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void getAllFacultiesAlphabetically_returnsList() {
        final Faculty f1 = new Faculty();
        f1.setName("Chirurgie");
        final Faculty f2 = new Faculty();
        f2.setName("Neurologie");
        final List<Faculty> expected = List.of(f1, f2);
        when(facultyRepository.findAllAlphabetically()).thenReturn(expected);

        final List<Faculty> result = facultyService.getAllFacultiesAlphabetically();

        assertThat(result).containsExactly(f1, f2);
        verify(facultyRepository, times(1)).findAllAlphabetically();
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void getAllFacultiesAlphabetically_returnsEmptyList() {
        when(facultyRepository.findAllAlphabetically()).thenReturn(List.of());
        
        final List<Faculty> result = facultyService.getAllFacultiesAlphabetically();

        assertThat(result).isEmpty();
        verify(facultyRepository).findAllAlphabetically();
        verifyNoMoreInteractions(facultyRepository);
    }
}
