package de.pjskilltrack.pjskilltrack.repository;

import de.pjskilltrack.pjskilltrack.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
}
