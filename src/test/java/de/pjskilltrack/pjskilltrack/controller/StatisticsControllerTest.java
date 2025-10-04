package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.controller.impl.StatisticsController;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticsControllerTest {
    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private StatisticsController statisticsController;

    @Test
    void getStatusCountsPerFaculty_returnsServiceResult() {
        final Map<String, Map<StatusType, Long>> serviceResult = new HashMap<>();
        final Map<StatusType, Long> chirurgieMap = new EnumMap<>(StatusType.class);
        chirurgieMap.put(StatusType.DONE, 3L);
        chirurgieMap.put(StatusType.SEEN, 2L);
        final Map<StatusType, Long> neuroMap = new EnumMap<>(StatusType.class);
        neuroMap.put(StatusType.DONE, 1L);
        serviceResult.put("Chirurgie", chirurgieMap);
        serviceResult.put("Neurologie", neuroMap);
        when(statisticsService.assembleStatusCountsPerFaculty()).thenReturn(serviceResult);

        final Map<String, Map<StatusType, Long>> result = statisticsController.getStatusCountsPerFaculty();

        assertThat(result).isSameAs(serviceResult);
        verify(statisticsService, times(1)).assembleStatusCountsPerFaculty();
        verifyNoMoreInteractions(statisticsService);
    }

    @Test
    void getStatusCountsPerFaculty_returnsEmpty_whenServiceEmpty() {
        when(statisticsService.assembleStatusCountsPerFaculty()).thenReturn(Map.of());

        final Map<String, Map<StatusType, Long>> result = statisticsController.getStatusCountsPerFaculty();
        
        assertThat(result).isEmpty();
        verify(statisticsService).assembleStatusCountsPerFaculty();
        verifyNoMoreInteractions(statisticsService);
    }
}
