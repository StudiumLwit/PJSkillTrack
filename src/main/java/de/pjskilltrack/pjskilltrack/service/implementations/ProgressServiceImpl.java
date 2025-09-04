package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.StatusTransition;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.repository.StatusTransitionRepository;
import de.pjskilltrack.pjskilltrack.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final StatusTransitionRepository statusTransitionRepository;

    @Autowired
    public ProgressServiceImpl(final StatusTransitionRepository statusTransitionRepository) {
        this.statusTransitionRepository = statusTransitionRepository;
    }

    @Override
    public Timestamp getLatestModification(final Progress progress) {
        final Optional<StatusTransition> optStatusTransition = statusTransitionRepository.findFirstByProgressOrderByChangeTimeDesc(progress);
        return optStatusTransition.map(StatusTransition::getChangeTime).orElse(null);

    }

    @Override
    public StatusType getCurrentStatus(final Progress progress) {
        final Optional<StatusTransition> optStatusTransition = statusTransitionRepository.findFirstByProgressOrderByChangeTimeDesc(progress);
        return optStatusTransition.map(StatusTransition::getNewStatus).orElse(null);
    }
}
