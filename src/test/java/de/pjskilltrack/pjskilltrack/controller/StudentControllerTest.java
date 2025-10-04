package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.controller.impl.StudentController;
import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import de.pjskilltrack.pjskilltrack.transfer.StudentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void me_returnsStudentDto_withCurrentStudentData() {
        final Student student = new Student();
        student.setEmail("mustermann@test.com");
        student.setName("Max Mustermann");
        when(studentService.getCurrentStudent()).thenReturn(student);

        final StudentDto result = studentController.me();

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("mustermann@test.com");
        assertThat(result.name()).isEqualTo("Max Mustermann");
        verify(studentService, times(1)).getCurrentStudent();
        verifyNoMoreInteractions(studentService);
    }
}
