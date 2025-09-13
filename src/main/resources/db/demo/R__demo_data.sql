-- ---------- FACULTIES
INSERT INTO faculty (id, name)
VALUES (1, 'Innere Medizin'),
       (2, 'Chirurgie'),
       (3, 'Pädiatrie'),
       (4, 'Anästhesiologie'),
       (5, 'Notfallmedizin')
ON CONFLICT (id) DO NOTHING;

-- ---------- SKILLS
INSERT INTO skill (id, name, description)
VALUES (1, 'Blut abnehmen', 'Venöse Blutentnahme am Patienten'),
       (2, 'EKG schreiben', 'Anlegen von Elektroden, 12-Kanal-EKG'),
       (3, 'Wundversorgung', 'Reinigung, Desinfektion und Verband'),
       (4, 'Reanimation', 'Basic Life Support nach Leitlinien'),
       (5, 'Intubation', 'Endotracheale Intubation unter Aufsicht'),
       (6, 'Ultraschall Abdomen', 'Sonographie Abdomenorgane'),
       (7, 'Blasenkatheter legen', 'Aseptische Katheterisierung'),
       (8, 'Blutdruck messen', 'Manuelle/automatische Messung'),
       (9, 'Impfung verabreichen', 'i.m. und s.c. Injektionen'),
       (10, 'Nähte legen', 'Nahttechniken in der Wundversorgung'),
       (11, 'Pädiatrische Untersuchung', 'Routine-Check bei Kindern'),
       (12, 'Narkoseeinleitung', 'Einleitung einer Allgemeinanästhesie')
ON CONFLICT (id) DO NOTHING;

-- ---------- Zuordnung SKILL ↔ FACULTY
INSERT INTO skill_faculty (skill_id, faculty_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 5),
       (5, 4),
       (6, 1),
       (7, 2),
       (8, 1),
       (9, 3),
       (10, 2),
       (11, 3),
       (12, 4)
ON CONFLICT DO NOTHING;

-- ---------- STUDENTS (Passwort = "demo")
INSERT INTO student (id, email, name, password)
VALUES (1, 'max.mueller@demo.org', 'Max Müller', '$2a$12$1m6xm./2DSnfkDgHIQVWveW1INeGirT.Nf2QwYUrHCBDF6hyBe/o2'),
       (2, 'sara.schmidt@demo.org', 'Sara Schmidt', '$2a$12$1m6xm./2DSnfkDgHIQVWveW1INeGirT.Nf2QwYUrHCBDF6hyBe/o2'),
       (3, 'leon.meier@demo.org', 'Leon Meier', '$2a$12$1m6xm./2DSnfkDgHIQVWveW1INeGirT.Nf2QwYUrHCBDF6hyBe/o2')
ON CONFLICT (id) DO NOTHING;

-- ---------- PROGRESS

-- Max: Innere/Notfall
INSERT INTO progress (id, student_id, skill_id, note)
VALUES (101, 1, 1, 'erste Versuche im Praktikum (Blut abnehmen)'),
       (102, 1, 2, 'EKG sicher angelegt'),
       (103, 1, 8, 'regelmäßig Blutdruck gemessen'),
       (104, 1, 4, 'BLS im Simulationszentrum wiederholt'),

-- Sara: Chirurgie/Pädiatrie
       (201, 2, 3, 'Wundversorgung nach OP geübt'),
       (202, 2, 10, 'Nahttechniken im Skills Lab'),
       (203, 2, 11, 'Kinderuntersuchung in der Ambulanz'),
       (204, 2, 9, 'Impfungen in der Praxis durchgeführt'),

-- Leon: Anästhesie/Chirurgie/Innere
       (301, 3, 12, 'Narkoseeinleitung mit Anästhesist'),
       (302, 3, 5, 'Intubation unter Aufsicht'),
       (303, 3, 6, 'Abdomen-Sonographie begonnen'),
       (304, 3, 7, 'Blasenkatheterisierung durchgeführt')
ON CONFLICT (id) DO NOTHING;

-- ---------- STATUS_TRANSITIONS
-- Max Müller
INSERT INTO status_transition (id, progress_id, new_status, change_time)
VALUES (1001, 101, 'UNDEFINED', now() - interval '25 days'),
       (1002, 101, 'SEEN', now() - interval '20 days'),
       (1003, 101, 'DONE', now() - interval '12 days'),
       (1004, 101, 'ROUTINE', now() - interval '2 days'),

       (1005, 102, 'UNDEFINED', now() - interval '18 days'),
       (1006, 102, 'SEEN', now() - interval '13 days'),
       (1007, 102, 'ROUTINE', now() - interval '3 days'),

       (1008, 103, 'UNDEFINED', now() - interval '8 days'),
       (1009, 103, 'SEEN', now() - interval '4 days'),

       (1010, 104, 'UNDEFINED', now() - interval '15 days'),
       (1011, 104, 'DONE', now() - interval '7 days'),
       (1012, 104, 'ROUTINE', now() - interval '1 days')
ON CONFLICT (id) DO NOTHING;

-- Sara Schmidt
INSERT INTO status_transition (id, progress_id, new_status, change_time)
VALUES (2001, 201, 'UNDEFINED', now() - interval '16 days'),
       (2002, 201, 'DONE', now() - interval '9 days'),

       (2003, 202, 'UNDEFINED', now() - interval '14 days'),
       (2004, 202, 'SEEN', now() - interval '10 days'),
       (2005, 202, 'DONE', now() - interval '6 days'),

       (2006, 203, 'UNDEFINED', now() - interval '7 days'),
       (2007, 203, 'SEEN', now() - interval '3 days'),

       (2008, 204, 'UNDEFINED', now() - interval '12 days'),
       (2009, 204, 'SEEN', now() - interval '11 days'),
       (2010, 204, 'DONE', now() - interval '5 days'),
       (2011, 204, 'ROUTINE', now() - interval '2 days')
ON CONFLICT (id) DO NOTHING;

-- Leon Meier
INSERT INTO status_transition (id, progress_id, new_status, change_time)
VALUES (3001, 301, 'UNDEFINED', now() - interval '20 days'),
       (3002, 301, 'SEEN', now() - interval '15 days'),

       (3003, 302, 'UNDEFINED', now() - interval '18 days'),
       (3004, 302, 'DONE', now() - interval '9 days'),
       (3005, 302, 'ROUTINE', now() - interval '2 days'),

       (3006, 303, 'UNDEFINED', now() - interval '11 days'),

       (3007, 304, 'UNDEFINED', now() - interval '13 days'),
       (3008, 304, 'SEEN', now() - interval '8 days'),
       (3009, 304, 'DONE', now() - interval '4 days')
ON CONFLICT (id) DO NOTHING;
