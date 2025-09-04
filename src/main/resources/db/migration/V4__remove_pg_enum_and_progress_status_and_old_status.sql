ALTER TABLE progress
    DROP COLUMN IF EXISTS status;
ALTER TABLE status_transition
    DROP COLUMN IF EXISTS old_status;
ALTER TABLE status_transition
    ALTER COLUMN new_status TYPE varchar;

DROP TYPE IF EXISTS status_type;
