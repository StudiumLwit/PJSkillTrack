package de.pjskilltrack.pjskilltrack.transfer;

import de.pjskilltrack.pjskilltrack.entity.StatusType;

import java.sql.Timestamp;

public record SkillOverviewDto(Long id, String name, String description, StatusType statusType, String note,
                               Timestamp lastModified) {
}
