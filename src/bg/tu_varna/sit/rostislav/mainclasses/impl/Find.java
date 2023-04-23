package bg.tu_varna.sit.rostislav.mainclasses.impl;

import java.util.List;

public class Find {
    public void searchAppointments(List<Book> appointments, String searchString) {
        System.out.println("Matching appointments for " + " + searchString + "+":");
        for (Book appointment : appointments) {
            if (appointment.getName().contains(searchString) || appointment.getNote().contains(searchString)) {
                System.out.println("Name: " + appointment.getName());
                System.out.println("Note: " + appointment.getNote());
                System.out.println("Date: " + appointment.getDate());
                System.out.println("Start Time: " + appointment.getStartTime());
                System.out.println("End Time: " + appointment.getEndTime());
                System.out.println();
            }
        }
    }
}
