package de.pjskilltrack.pjskilltrack.service.implementations;

import de.pjskilltrack.pjskilltrack.entity.Student;
import de.pjskilltrack.pjskilltrack.repository.StudentRepository;
import de.pjskilltrack.pjskilltrack.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getCurrentStudent() {
        final String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return studentRepository.findByEmail(currentUser).orElse(null);
    }
}
