package de.pjskilltrack.pjskilltrack.service;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.StatusType;

import java.sql.Timestamp;

public interface ProgressService {
    Timestamp getLatestModification(Progress progress);

    StatusType getCurrentStatus(Progress progress);
}
