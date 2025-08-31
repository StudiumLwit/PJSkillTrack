package de.pjskilltrack.pjskilltrack.repository;

import de.pjskilltrack.pjskilltrack.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>
{
}
