package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.repository.FacultyStatusCountRow;
import de.pjskilltrack.pjskilltrack.repository.ProgressRepository;
import de.pjskilltrack.pjskilltrack.service.StatisticsService;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final ProgressRepository progressRepository;
    private final StudentService studentService;

    @Autowired
    public StatisticsServiceImpl(final ProgressRepository progressRepository, final StudentService studentService) {
        this.progressRepository = progressRepository;
        this.studentService = studentService;
    }

    /**
     * @return a Map of faculties to the Map of StatusTypes with their count
     * <p>
     * It may look complex but all that happens is:
     * - get faculty, status, count objects
     * - map it by faculty
     * - create a "sub-map" where the status-count pairs (of each faculty) get merged to one map with the status as key and count as value
     */
    @Override
    public Map<String, Map<StatusType, Long>> assembleStatusCountsPerFaculty() {
        final Student currentStudent = studentService.getCurrentStudent();

        return progressRepository.findFacultyStatusCountsForStudent(currentStudent.getId())
                .stream()
                .collect(groupingBy(
                        FacultyStatusCountRow::faculty,
                        TreeMap::new,
                        toMap(
                                row -> StatusType.valueOf(row.statusType()),
                                FacultyStatusCountRow::count,
                                Long::sum,
                                this::createEmptyStatusCountMap
                        )
                ));
    }

    /**
     * @return Map of all enum statuses to the value 0
     * <p>
     * Create a map with all possible statuses as keys and the initial value of 0
     */
    private Map<StatusType, Long> createEmptyStatusCountMap() {
        return Arrays.stream(StatusType.values())
                .collect(toMap(
                        status -> status,
                        status -> 0L,
                        (a, b) -> a,
                        () -> new EnumMap<>(StatusType.class)
                ));
    }
}
