package de.pjskilltrack.pjskilltrack.repository;

import de.pjskilltrack.pjskilltrack.entity.Progress;
import de.pjskilltrack.pjskilltrack.entity.Skill;
import de.pjskilltrack.pjskilltrack.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByStudentAndSkill(Student student, Skill skill);

    /**
     * This is complex but inevitable if db normalization is to be kept.
     * The use of views would also be possible but much boilerplate code as well.
     * SQL indices are used to speed this up.
     * <p>
     * progress_latest_status: Mapping of progress_id to its latest status transition
     * "UNDEFINED" is used as a fallback status if 1) a progress has no transition yet or 2) the progress itself is non-existent yet
     *
     * @param studentId the id of the student, used to determine the progress statuses for the faculties
     * @return list of entries of faculty, status and the count of appearances of this status for this faculty (and this student)
     */
    @Query(value = """
            WITH progress_latest_status AS (
                SELECT DISTINCT ON (st.progress_id)
                    st.progress_id, st.new_status
                FROM status_transition st
                ORDER BY st.progress_id, st.change_time DESC, st.id DESC
            )
            SELECT
                f.name AS faculty_name,
                COALESCE(pls.new_status, 'UNDEFINED') AS status,
                COUNT(*) AS status_count
            FROM skill_faculty sf
                     JOIN faculty f
                          ON f.id = sf.faculty_id
                     LEFT JOIN progress p
                               ON p.skill_id = sf.skill_id
                                   AND p.student_id = :studentId
                     LEFT JOIN progress_latest_status pls
                               ON pls.progress_id = p.id
            GROUP BY f.name, status
            ORDER BY f.name;
            """,
            nativeQuery = true)
    List<FacultyStatusCountRow> findFacultyStatusCountsForStudent(@Param("studentId") Long studentId);
}
