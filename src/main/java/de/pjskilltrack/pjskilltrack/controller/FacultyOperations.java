package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.transfer.FacultyDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/faculty")
public interface FacultyOperations {

    @GetMapping
    List<FacultyDto> getAllFacultiesAlphabetically();
}
