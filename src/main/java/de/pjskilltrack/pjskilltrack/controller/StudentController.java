package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import de.pjskilltrack.pjskilltrack.transfer.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class StudentController
{
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @RequestMapping("/me")
    public StudentDto me() {
        Student currentStudent =  studentService.getCurrentStudent();
        return new StudentDto(currentStudent.getUsername(), currentStudent.getName());
    }
}
