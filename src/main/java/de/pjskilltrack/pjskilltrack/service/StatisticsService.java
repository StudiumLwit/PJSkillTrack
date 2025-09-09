package de.pjskilltrack.pjskilltrack.service;

import de.pjskilltrack.pjskilltrack.entity.StatusType;

import java.util.Map;

public interface StatisticsService {
    Map<String, Map<StatusType, Long>> assembleStatusCountsPerFaculty();
}
