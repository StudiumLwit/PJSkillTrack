CREATE INDEX IF NOT EXISTS idx_st_progress_time_desc
    ON status_transition (progress_id, change_time DESC, id DESC);

ALTER TABLE progress
    DROP CONSTRAINT IF EXISTS uq_progress_student_skill;
ALTER TABLE progress
    ADD CONSTRAINT uq_progress_student_skill
        UNIQUE (student_id, skill_id);
