package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.transfer.StudentDto;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface StudentOperations {
    @RequestMapping("/me")
    StudentDto me();
}
