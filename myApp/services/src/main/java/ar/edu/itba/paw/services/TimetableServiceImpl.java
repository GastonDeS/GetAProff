package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private TimetableDao timetableDao;

    @Override
    public Timetable findById(int userId) {
        return timetableDao.get(userId);
    }

    @Override
    public List<String> getUserSchedule(int userId) {
        return timetableDao.getUserSchedule(userId);
    }

    @Override
    public int setUserSchedule(int userId, String[] schedule) {
        if(schedule != null && schedule.length == Timetable.DAYS){
            return timetableDao.setUserSchedule(userId, schedule);
        }
        return 1;
    }

    @Override
    public Timetable createUserSchedule(int userId, String[] schedule) {
        return timetableDao.createUserSchedule(userId,schedule);
    }

}
