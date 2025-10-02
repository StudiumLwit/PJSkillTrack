package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.StatusType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/statistics")
public interface StatisticsOperations {

    @GetMapping("/statusPerFaculty")
    Map<String, Map<StatusType, Long>> getStatusCountsPerFaculty();
}
