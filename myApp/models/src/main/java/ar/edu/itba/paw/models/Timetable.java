package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Timetable {
    public final static int DAYS = 7;
    private final int userId;

    public int getUserId() {
        return userId;
    }

    public enum Days {
        MONDAY("Lunes"),
        TUESDAY("Martes"),
        WEDNESDAY("Miercoles"),
        THURSDAY("Jueves"),
        FRIDAY("Viernes"),
        SATURDAY("Sabado"),
        SUNDAY("Domingo");

        public String getValue(){
            return value;
        }

        private final String value;

        Days(String value) {
            this.value = value;
        }
    }

    private final EnumMap<Days,String> schedule;

    public Timetable(int userId, String [] daySchedule) {
        this.userId = userId;
        schedule = new EnumMap<>(Days.class);
        for(Days d : Days.values()){
            schedule.put(d,daySchedule[d.ordinal()]);
        }

    }

    public List<String> getSchedule() {
        return new ArrayList<>(this.schedule.values());
    }

    public void setSchedule(String[] days){
        if(days != null && days.length == DAYS) {
            for (Days d : Days.values()) {
                this.schedule.put(d,days[d.ordinal()]);
            }
        }
    }
}

