package de.pjskilltrack.pjskilltrack.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity(name = "status_transition")
public class StatusTransition
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progress_id", nullable = false)
    private Progress progress;

    @Enumerated(EnumType.STRING)
    private StatusType oldStatus;
    @Enumerated(EnumType.STRING)
    private StatusType newStatus;
    private Timestamp changeTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public StatusType getOldStatus()
    {
        return oldStatus;
    }

    public void setOldStatus(StatusType oldStatus)
    {
        this.oldStatus = oldStatus;
    }

    public StatusType getNewStatus()
    {
        return newStatus;
    }

    public void setNewStatus(StatusType newStatus)
    {
        this.newStatus = newStatus;
    }

    public Timestamp getChangeTime()
    {
        return changeTime;
    }

    public void setChangeTime(Timestamp changeTime)
    {
        this.changeTime = changeTime;
    }

    public Progress getProgress()
    {
        return progress;
    }

    public void setProgress(Progress progress)
    {
        this.progress = progress;
    }
}
