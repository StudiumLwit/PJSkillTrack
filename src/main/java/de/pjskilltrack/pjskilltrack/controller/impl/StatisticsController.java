package de.pjskilltrack.pjskilltrack.controller.impl;

import de.pjskilltrack.pjskilltrack.controller.StatisticsOperations;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController implements StatisticsOperations {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public Map<String, Map<StatusType, Long>> getStatusCountsPerFaculty() {
        return statisticsService.assembleStatusCountsPerFaculty();
    }
}
