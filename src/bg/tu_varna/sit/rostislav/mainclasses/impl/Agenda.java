package bg.tu_varna.sit.rostislav.mainclasses.impl;


import java.util.List;

public class Agenda {
    public void displayAppointments(List<Book> appointments, String date) {
        System.out.println("Appointments for " + date + ":");
        for (Book appointment : appointments) {
            if (appointment.getDate().equals(date)) {
                System.out.println("Name: " + appointment.getName());
                System.out.println("Note: " + appointment.getNote());
                System.out.println("Start Time: " + appointment.getStartTime());
                System.out.println("End Time: " + appointment.getEndTime());
                System.out.println();
            }
        }
    }
}
