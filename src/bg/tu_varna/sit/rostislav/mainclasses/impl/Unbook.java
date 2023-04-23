package bg.tu_varna.sit.rostislav.mainclasses.impl;

import java.util.List;

public class Unbook  {

    public void cancelAppointment(List<Book> appointments, String date, String startTime, String endTime) {
        for (Book appointment : appointments) {
            if (appointment.getDate().equals(date) && appointment.getStartTime().equals(startTime)
                    && appointment.getEndTime().equals(endTime)) {
                appointments.remove(appointment);
                break;
            }
        }
    }
}
