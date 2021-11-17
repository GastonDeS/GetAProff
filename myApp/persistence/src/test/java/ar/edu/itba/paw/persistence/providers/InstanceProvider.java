package ar.edu.itba.paw.persistence.providers;

import ar.edu.itba.paw.models.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

public class InstanceProvider {

    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final String REVIEW = "Muy buen profesor";
    private static final String SUBJECT = "MATE 1";
    private static final Integer PRICE = 500;
    private static final Integer MAX_LEVEL = 3;
    private static final Integer MIN_LEVEL = 1;
    private static final Integer MAXPRICE = 1400;
    private static final Float HIGH_RATE = 5f;
    private static final Float LOW_RATE = 1f;
    private static final Integer STATUS = 1;
    private static final Integer ANY_LEVEL = 0;
    private static final String FILENAME = "FILENAME";

    public InstanceProvider() {
        //
    }

    public static User getUser() {
        return new User.Builder(USER_MAIL).description(DESCRIPTION)
                .schedule(SCHEDULE).name(USERNAME).password(USER_PASS).favourites(new ArrayList<>()).build();
    }

    public static Subject getSubject() {
        return new Subject(SUBJECT, null);
    }

    public static User getNewUser(int number) {
        String name = "user " + number;
        String mail = "user" + number + "@mail.com";
        return new User.Builder(mail).name(name).password(USER_PASS).favourites(new ArrayList<>()).build();
    }

    public static Subject getNewSubject(int number) {
        return new Subject("Mate " + number, null);
    }

    public static Teaches getNewTeaches(User user, Subject subject) {
        return new Teaches(user, subject, PRICE, ANY_LEVEL);
    }

    public static Teaches getNewHighLevelTeaches(User user, Subject subject) {
        return new Teaches(user, subject, PRICE, MAX_LEVEL);
    }

    public static Teaches getNewLowLevelTeaches(User user, Subject subject) {
        return new Teaches(user, subject, PRICE, MIN_LEVEL);
    }

    public static Teaches getNewHighPriceTeaches(User user, Subject subject) {
        return new Teaches(user, subject, MAXPRICE, MAX_LEVEL);
    }

    public static Rating getNewHighRating(User teacher, User student) {
        return new Rating(HIGH_RATE, REVIEW, teacher, student);
    }

    public static Rating getNewLowRating(User teacher, User student) {
        return new Rating(LOW_RATE, REVIEW, teacher, student);
    }

    public static Lecture getNewLecture(User teacher, User student, Subject subject) {
        return new Lecture.Builder(teacher, student, subject).sharedFilesByTeacher(new ArrayList<>())
                .price(PRICE).level(ANY_LEVEL)
                .studentLastTime(Timestamp.from(Instant.now()))
                .teacherLastTime(Timestamp.from(Instant.now()))
                .build();
    }

    public static Lecture getNewStatusLecture(User teacher, User student, Subject subject) {
        return new Lecture.Builder(teacher, student, subject).status(STATUS)
                .price(PRICE).level(ANY_LEVEL)
                .studentLastTime(Timestamp.from(Instant.now()))
                .teacherLastTime(Timestamp.from(Instant.now()))
                .build();
    }

    public static Lecture getNewTimeLecture(User teacher, User student, Subject subject) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        return new Lecture.Builder(teacher, student, subject).teacherLastTime(timestamp)
                .price(PRICE).level(ANY_LEVEL)
                .studentLastTime(Timestamp.from(Instant.now()))
                .build();
    }

    public static Post getNewPost(User uploader, Lecture lecture) {
        Calendar calendar = Calendar.getInstance();
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        return new Post.Builder().uploader(uploader).associatedLecture(lecture)
                .time(timestamp).build();
    }

    public static SubjectFile getNewSubjectFile(Teaches teachesInfo) {
        return new SubjectFile.Builder().teachesInfo(teachesInfo)
                .file(new byte[]{})
                .fileName(FILENAME)
                .build();
    }

}
