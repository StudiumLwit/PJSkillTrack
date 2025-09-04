package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity(name = "status_transition")
public class StatusTransition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", nullable = false)
    private Progress progress;

    @Enumerated(EnumType.STRING)
    private StatusType newStatus;
    private Timestamp changeTime;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public StatusType getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(final StatusType newStatus) {
        this.newStatus = newStatus;
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(final Timestamp changeTime) {
        this.changeTime = changeTime;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(final Progress progress) {
        this.progress = progress;
    }
}
