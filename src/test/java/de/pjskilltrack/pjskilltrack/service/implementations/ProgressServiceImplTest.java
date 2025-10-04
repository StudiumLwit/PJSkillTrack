package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.StatusTransition;
import de.pjskilltrack.pjskilltrack.entity.StatusType;
import de.pjskilltrack.pjskilltrack.repository.StatusTransitionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgressServiceImplTest {
    @Mock
    private StatusTransitionRepository statusTransitionRepository;

    @InjectMocks
    private ProgressServiceImpl progressService;

    @Test
    void getLatestModification_returnsTimestamp_whenTransitionExists() {
        final Progress progress = new Progress();
        final StatusTransition latest = new StatusTransition();
        final Timestamp ts = Timestamp.from(Instant.parse("2025-10-04T11:00:00.00Z"));
        latest.setChangeTime(ts);
        when(statusTransitionRepository.findFirstByProgressOrderByChangeTimeDesc(progress))
                .thenReturn(Optional.of(latest));

        final Timestamp result = progressService.getLatestModification(progress);

        assertThat(result).isEqualTo(ts);
        final ArgumentCaptor<Progress> captor = ArgumentCaptor.forClass(Progress.class);
        verify(statusTransitionRepository).findFirstByProgressOrderByChangeTimeDesc(captor.capture());
        assertThat(captor.getValue()).isSameAs(progress);
        verifyNoMoreInteractions(statusTransitionRepository);
    }

    @Test
    void getLatestModification_returnsNull_whenNoTransition() {
        final Progress progress = new Progress();
        when(statusTransitionRepository.findFirstByProgressOrderByChangeTimeDesc(progress))
                .thenReturn(Optional.empty());

        final Timestamp result = progressService.getLatestModification(progress);

        assertThat(result).isNull();
        verify(statusTransitionRepository).findFirstByProgressOrderByChangeTimeDesc(progress);
        verifyNoMoreInteractions(statusTransitionRepository);
    }

    @Test
    void getCurrentStatus_returnsStatus_whenTransitionExists() {
        final Progress progress = new Progress();
        final StatusTransition latest = new StatusTransition();
        latest.setNewStatus(StatusType.DONE);
        when(statusTransitionRepository.findFirstByProgressOrderByChangeTimeDesc(progress))
                .thenReturn(Optional.of(latest));

        final StatusType status = progressService.getCurrentStatus(progress);

        assertThat(status).isEqualTo(StatusType.DONE);
        verify(statusTransitionRepository).findFirstByProgressOrderByChangeTimeDesc(progress);
        verifyNoMoreInteractions(statusTransitionRepository);
    }

    @Test
    void getCurrentStatus_returnsNull_whenNoTransition() {
        final Progress progress = new Progress();
        when(statusTransitionRepository.findFirstByProgressOrderByChangeTimeDesc(progress))
                .thenReturn(Optional.empty());

        final StatusType status = progressService.getCurrentStatus(progress);

        assertThat(status).isNull();
        verify(statusTransitionRepository).findFirstByProgressOrderByChangeTimeDesc(progress);
        verifyNoMoreInteractions(statusTransitionRepository);
    }
}
