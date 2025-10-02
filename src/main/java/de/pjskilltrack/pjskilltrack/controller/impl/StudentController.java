package de.pjskilltrack.pjskilltrack.controller.impl;

import de.pjskilltrack.pjskilltrack.controller.StudentOperations;
import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import de.pjskilltrack.pjskilltrack.transfer.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class StudentController implements StudentOperations {
    private final StudentService studentService;

    @Autowired
    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public StudentDto me() {
        final Student currentStudent = studentService.getCurrentStudent();
        return new StudentDto(currentStudent.getUsername(), currentStudent.getName());
    }
}
