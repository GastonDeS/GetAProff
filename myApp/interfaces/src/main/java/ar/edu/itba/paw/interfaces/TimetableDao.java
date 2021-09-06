package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Timetable;

public interface TimetableDao {
    Timetable get(int userId);
}
