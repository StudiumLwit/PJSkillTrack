package de.pjskilltrack.pjskilltrack.repository;

import de.pjskilltrack.pjskilltrack.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long>
{
    @NativeQuery("select * from faculty order by name asc")
    List<Faculty> findAllAlphabetically();
}
