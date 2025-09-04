package de.pjskilltrack.pjskilltrack.repository;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.StatusTransition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusTransitionRepository extends JpaRepository<StatusTransition, Long> {
    Optional<StatusTransition> findFirstByProgressOrderByChangeTimeDesc(Progress progress);

}
