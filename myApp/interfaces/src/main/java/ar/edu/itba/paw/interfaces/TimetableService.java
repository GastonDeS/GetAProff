package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Timetable;

import java.util.List;

public interface TimetableService {
    Timetable findById(int userId);

    List<String> getUserSchedule(int userId);
    int setUserSchedule(int userId, String[] newSchedule);
    Timetable createUserSchedule(int userId, String[] schedule);
}
