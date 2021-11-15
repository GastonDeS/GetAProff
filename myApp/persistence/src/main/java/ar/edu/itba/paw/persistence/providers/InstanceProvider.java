package ar.edu.itba.paw.persistence.providers;

import ar.edu.itba.paw.models.User;

public class InstanceProvider {

    private static final double DELTA = 1e-15;
    private static final String USERNAME = "John Doe";
    private static final String USER_MAIL = "John@Doe.com";
    private static final String USERNAME_EXTRA = "Jane Doe";
    private static final String USER_MAIL_EXTRA = "jane@doe.com";
    private static final String USER_PASS = "1234";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final String SCHEDULE = "todos los dias habiles de 8 a 16";
    private static final String REVIEW = "Muy buen profesor";
    private static final Long USER_ID = 1L;
    private static final String SUBJECT_ONE = "MATE 1";
    private static final String SUBJECT_TWO = "MATE 2";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Integer PRICE = 500;
    private static final Integer MAX_LEVEL = 3;
    private static final Integer MIN_LEVEL = 1;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final Integer RATE = 4;
    private static final Integer FILTER_RATE = 3;
    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, GET_ALL = 0;

    private static User user;

    public InstanceProvider() {
        user = new User.Builder(USER_MAIL).description(DESCRIPTION)
                .schedule(SCHEDULE).name(USERNAME).password(USER_PASS).build();
    }

    public User getUser() {
        return user;
    }

    public User getNewUser(int number) {
        String name = "user " + number;
        String mail = "user" + number + "@mail.com";
        return new User.Builder(mail).description(DESCRIPTION)
                .schedule(SCHEDULE).name(name).password(USER_PASS).build();
    }
}
