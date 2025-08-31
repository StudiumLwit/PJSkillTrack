package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Faculty;
import de.pjskilltrack.pjskilltrack.repository.FacultyRepository;
import de.pjskilltrack.pjskilltrack.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService
{
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository)
    {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFacultiesAlphabetically()
    {
        return facultyRepository.findAllAlphabetically();
    }
}
