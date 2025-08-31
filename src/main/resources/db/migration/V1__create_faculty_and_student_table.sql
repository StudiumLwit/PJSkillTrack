CREATE TABLE faculty
(
    id   serial primary key,
    name varchar unique
);

CREATE TABLE student
(
    id       serial primary key,
    email    varchar unique,
    name     varchar,
    password varchar
);
