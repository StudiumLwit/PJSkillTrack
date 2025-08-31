CREATE SEQUENCE progress_id_seq;

ALTER TABLE progress
    ALTER COLUMN id SET DEFAULT nextval('progress_id_seq');

ALTER SEQUENCE progress_id_seq OWNED BY progress.id;

SELECT setval('progress_id_seq', COALESCE((SELECT MAX(id) FROM progress), 1), false);
