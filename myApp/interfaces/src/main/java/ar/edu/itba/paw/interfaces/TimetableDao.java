package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Timetable;

import java.util.List;

public interface TimetableDao {
    Timetable get(int userId);

    List<String> getUserSchedule(int userId);

    int setUserSchedule(int userId, String[] newSchedule);

    Timetable createUserSchedule(int userId, String[] schedule);
}
