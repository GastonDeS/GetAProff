CREATE TABLE IF NOT EXISTS users
(
    userId      BIGINT default nextval('users_userid_seq'::regclass),
    name        VARCHAR(100),
    password    VARCHAR(100),
    mail        VARCHAR(100),
    description VARCHAR(256) default '',
    schedule    VARCHAR(256) default '',
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS rating
(
    teacherId   BIGINT,
    userId      BIGINT NOT NULL,
    rate        REAL check (rate <= 5),
    review      VARCHAR(256),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,userId)
);

CREATE TABLE IF NOT EXISTS favourites
(
    teacherId bigint,
    studentId bigint NOT NULL,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,studentId)
);

CREATE TABLE IF NOT EXISTS images
(
    userId bigint,
    image  bytea,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS posts
(
    postid      BIGINT default nextval('posts_postid_seq'::regclass),
    userid      BIGINT,
    message     VARCHAR(100) default ''::character varying,
    classid     BIGINT,
    file        BYTEA,
    filename    VARCHAR(100),
    time        TIMESTAMP default CURRENT_TIMESTAMP,
    type        VARCHAR(100),
    FOREIGN KEY (userid) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (classid) REFERENCES classes ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS subject
(
    subjectId SERIAL PRIMARY KEY,
    name      varchar(100)
);

CREATE TABLE IF NOT EXISTS teaches
(
    userId    BIGINT,
    subjectId BIGINT,
    price     BIGINT,
    level     INTEGER default 0,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE,
    PRIMARY KEY (userId, subjectId, level)
);

CREATE TABLE IF NOT EXISTS user_file
(
    userId   BIGINT,
    fileId   BIGINT DEFAULT nextval('user_file_fileid_seq'::regclass),
    fileName VARCHAR,
    file     BYTEA,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId, fileId)
);

CREATE TABLE IF NOT EXISTS subject_files
(
    userId          BIGINT,
    fileId          BIGINT PRIMARY KEY,
    fileName        VARCHAR,
    file            BYTEA,
    subjectid       BIGINT,
    subjectlevel    INTEGER,
    FOREIGN KEY (userid,subjectid,subjectlevel) REFERENCES teaches(userId, subjectId, level) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shared
(
    classId BIGINT,
    fileId  BIGINT,
    FOREIGN KEY (classId) REFERENCES classes ON DELETE CASCADE,
    FOREIGN KEY (fileId) REFERENCES subject_files ON DELETE CASCADE,
    PRIMARY KEY(classId, fileId)
);

CREATE TABLE IF NOT EXISTS classes
(
    classId   BIGINT default nextval('classes_classid_seq'::regclass) PRIMARY KEY,
    studentId BIGINT NOT NULL,
    teacherId BIGINT NOT NULL,
    level     INTEGER NOT NULL,
    subjectId BIGINT NOT NULL,
    price     INTEGER NOT NULL,
    status    INTEGER NOT NULL,
    notifications INTEGER,
    studentlasttime TIMESTAMP default CURRENT_TIMESTAMP,
    teacherlasttime TIMESTAMP default CURRENT_TIMESTAMP,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS roles
(
    roleId BIGINT PRIMARY KEY,
    role varchar(100)
    );

CREATE TABLE IF NOT EXISTS userRoles
(
    roleId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    PRIMARY KEY (roleId, userId),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
    );