package ar.edu.itba.paw.models;

public class Timetable {
    private final static int DAYS = 6;
    private int userId;
    private String[] range = new String[DAYS];

    public Timetable(int userId, String mon, String tue, String wed, String thu, String fri, String sat) {
        this.userId = userId;
        range[Days.MONDAY.ordinal()] = mon;
        range[Days.TUESDAY.ordinal()] = tue;
        range[Days.WEDNESDAY.ordinal()] = wed;
        range[Days.THURSDAY.ordinal()] = thu;
        range[Days.FRIDAY.ordinal()] = fri;
        range[Days.SATURDAY.ordinal()] = sat;
    }
}

enum Days {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY;
}
