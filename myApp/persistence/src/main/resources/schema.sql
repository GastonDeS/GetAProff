CREATE TABLE IF NOT EXISTS users
(
    userId      SERIAL PRIMARY KEY,
    name        varchar(100),
    password    varchar(100),
    mail        varchar(100),
    userrole    INTEGER,
    description varchar(256) default '',
    schedule    varchar(256) default ''
);

CREATE TABLE IF NOT EXISTS images
(
    userId INTEGER,
    image  bytea,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS subject
(
    subjectId SERIAL PRIMARY KEY,
    name      varchar(100)
);

CREATE TABLE IF NOT EXISTS timetable
(
    userId    INTEGER,
    monday    VARCHAR(100),
    tuesday   VARCHAR(100),
    wednesday VARCHAR(100),
    thursday  VARCHAR(100),
    friday    VARCHAR(100),
    saturday  VARCHAR(100),
    sunday    VARCHAR(100),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS teaches
(
    userId    INTEGER,
    subjectId INTEGER,
    price     INTEGER,
    level     INTEGER,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE,
    PRIMARY KEY (userId, subjectId)
);

CREATE TABLE IF NOT EXISTS classes
(
    classId   SERIAL PRIMARY KEY,
    studentId INTEGER NOT NULL,
    teacherId INTEGER NOT NULL,
    level     INTEGER NOT NULL,
    subjectId INTEGER NOT NULL,
    price     INTEGER NOT NULL,
    status    INTEGER NOT NULL,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE
);

CREATE OR REPLACE VIEW front_classes AS
(
SELECT classId, classes.studentId AS studentId,  student.name AS studentName, student.password AS studentPassword, student.mail AS studentMail, student.userrole AS studentRole, classes.teacherId AS teacherId, teacher.name AS teacherName, teacher.mail AS teacherMail, teacher.password AS teacherPassword, teacher.userrole AS teacherRole, teacher.description AS teacherDescription, teacher.schedule AS teacherSchedule, level, classes.subjectId AS subjectID,status, price, subject.name AS subjectName
FROM classes
         LEFT JOIN users student ON student.userId = classes.studentId
         LEFT JOIN users teacher ON teacher.userid = classes.teacherid
         LEFT JOIN subject on classes.subjectid = subject.subjectid
    )

