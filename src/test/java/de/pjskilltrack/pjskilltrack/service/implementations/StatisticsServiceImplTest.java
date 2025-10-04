package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.repository.FacultyStatusCountRow;
import de.pjskilltrack.pjskilltrack.repository.ProgressRepository;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceImplTest {
    @Mock
    private ProgressRepository progressRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StatisticsServiceImpl statisticsService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Max Mustermann");
    }

    @Test
    void assembleStatusCountsPerFaculty_groupsAndCountsCorrectly() {
        when(studentService.getCurrentStudent()).thenReturn(student);

        final FacultyStatusCountRow row1 = new FacultyStatusCountRow("Chirurgie", "DONE", 3L);
        final FacultyStatusCountRow row2 = new FacultyStatusCountRow("Chirurgie", "ROUTINE", 1L);
        final FacultyStatusCountRow row3 = new FacultyStatusCountRow("Neurologie", "SEEN", 2L);
        final FacultyStatusCountRow row4 = new FacultyStatusCountRow("Pädiatrie", "DONE", 1L);
        when(progressRepository.findFacultyStatusCountsForStudent(student.getId()))
                .thenReturn(List.of(row1, row2, row3, row4));

        final Map<String, Map<StatusType, Long>> result = statisticsService.assembleStatusCountsPerFaculty();

        assertThat(result).containsOnlyKeys("Chirurgie", "Neurologie", "Pädiatrie");
        final Map<StatusType, Long> chirurgieMap = result.get("Chirurgie");
        assertThat(chirurgieMap.get(StatusType.DONE)).isEqualTo(3L);
        assertThat(chirurgieMap.get(StatusType.ROUTINE)).isEqualTo(1L);
        assertThat(chirurgieMap.get(StatusType.SEEN)).isEqualTo(0L);
        assertThat(chirurgieMap.get(StatusType.UNDEFINED)).isEqualTo(0L);
        assertThat(chirurgieMap.keySet()).containsAll(List.of(StatusType.values()));

        verify(progressRepository).findFacultyStatusCountsForStudent(student.getId());
        verify(studentService).getCurrentStudent();
    }

    @Test
    void assembleStatusCountsPerFaculty_returnsEmptyMapWhenNoData() {
        when(studentService.getCurrentStudent()).thenReturn(student);
        when(progressRepository.findFacultyStatusCountsForStudent(student.getId())).thenReturn(List.of());

        Map<String, Map<StatusType, Long>> result = statisticsService.assembleStatusCountsPerFaculty();
        
        assertThat(result).isEmpty();
        verify(progressRepository).findFacultyStatusCountsForStudent(student.getId());
        verify(studentService).getCurrentStudent();
    }
}
