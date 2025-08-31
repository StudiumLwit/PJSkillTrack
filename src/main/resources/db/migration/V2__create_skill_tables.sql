CREATE TABLE skill
(
    id          serial primary key,
    name        varchar unique,
    description varchar
);

CREATE TABLE skill_faculty
(
    skill_id   integer references skill (id),
    faculty_id integer references faculty (id)
);

CREATE TYPE status_type AS ENUM ('UNDEFINED', 'SEEN', 'DONE', 'ROUTINE');

CREATE TABLE progress
(
    id            integer primary key,
    skill_id      integer references skill (id),
    student_id    integer references student (id),
    status        status_type,
    note          varchar
);

CREATE TABLE status_transition (
    id            serial primary key,
    progress_id   integer references progress (id),
    old_status    status_type,
    new_status    status_type,
    change_time   timestamp
);
