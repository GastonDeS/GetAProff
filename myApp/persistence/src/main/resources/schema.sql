CREATE TABLE IF NOT EXISTS users (
                userId SERIAL PRIMARY KEY,
                name varchar(100),
                password varchar(100),
                mail varchar(100)
            );

CREATE TABLE IF NOT EXISTS subject (
                subjectId INTEGER PRIMARY KEY,
                name varchar(100)
            );

CREATE TABLE IF NOT EXISTS timetable (
                userId INTEGER,
                monday VARCHAR(100),
                tuesday VARCHAR(100),
                wednesday VARCHAR(100),
                thursday VARCHAR(100),
                friday VARCHAR(100),
                saturday VARCHAR(100),
                sunday VARCHAR(100),
                FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
                PRIMARY KEY (userId)
            );

CREATE TABLE IF NOT EXISTS teaches (
                 userId INTEGER,
                 subjectId INTEGER,
                 price INTEGER,
                 FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
                 FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE,
                 PRIMARY KEY (userId,subjectId)
               );