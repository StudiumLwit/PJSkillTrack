package de.pjskilltrack.pjskilltrack.controller;

import de.pjskilltrack.pjskilltrack.service.FacultyService;
import de.pjskilltrack.pjskilltrack.transfer.FacultyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController
{
    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService)
    {
        this.facultyService = facultyService;
    }

    @GetMapping
    public List<FacultyDto> getAllFacultiesAlphabetically()
    {
        return facultyService.getAllFacultiesAlphabetically().stream().map(fac -> new FacultyDto(fac.getName())).toList();
    }
}
