package de.pjskilltrack.pjskilltrack.transfer;

import de.pjskilltrack.pjskilltrack.entity.StatusType;

public record UpdateSkillOverviewDto(String note, StatusType statusType) {
}
