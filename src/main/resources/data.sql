TRUNCATE TABLE files RESTART IDENTITY;

INSERT INTO files (name, parent_id, file_type)
VALUES ('test 1', null, 'FOLDER'),
       ('test 2', 1, 'FOLDER'),
       ('test 3', 1, 'FOLDER'),
       ('test 4', 1, 'FOLDER');