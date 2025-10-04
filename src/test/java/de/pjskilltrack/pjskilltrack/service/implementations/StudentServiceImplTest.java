package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentStudent_returnsStudent_whenFound() {
        final String loggedInEmail = "test@example.com";
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loggedInEmail, "password");
        SecurityContextHolder.getContext().setAuthentication(auth);
        final Student foundStudent = new Student();
        foundStudent.setId(123L);
        foundStudent.setEmail(loggedInEmail);
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(foundStudent));

        final Student result = studentService.getCurrentStudent();

        assertThat(result).isEqualTo(foundStudent);
        // verify that email from SecurityContext is actually used for looking up the student object
        final ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(studentRepository).findByEmail(emailCaptor.capture());
        assertThat(emailCaptor.getValue()).isEqualTo(loggedInEmail);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void getCurrentStudent_returnsNull_whenAuthNotFound() {
        final String notLoggedInEmail = "no-user@example.com";
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(notLoggedInEmail, "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        final Student result = studentService.getCurrentStudent();

        assertThat(result).isNull();
        final ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(studentRepository).findByEmail(emailCaptor.capture());
        assertThat(emailCaptor.getValue()).isEqualTo(notLoggedInEmail);
        verifyNoMoreInteractions(studentRepository);
    }
}
