package bg.tu_varna.sit.rostislav.mainclasses.impl;

import java.util.List;

public class Change {
    public void updateAppointment(List<Book> appointments, String option, String date, String startTime,
                                  String endTime, String newValue) {
        for (Book appointment : appointments) {
            if (appointment.getDate().equals(date) && appointment.getStartTime().equals(startTime)
                    && appointment.getEndTime().equals(endTime)) {
                switch (option) {
                    case "name":
                        appointment.setName(newValue);
                        break;
                    case "note":
                        appointment.setNote(newValue);
                        break;
                    case "date":
                        // Check if the new date is free
                        if (isDateFree(appointments, newValue, appointment.getStartTime(),
                                appointment.getEndTime())) {
                            appointment.setDate(newValue);
                        } else {
                            System.out.println("Error: The new date is not available.");
                        }
                        break;
                    case "starttime":
                        // Check if the new start time is free
                        if (isTimeFree(appointments, appointment.getDate(), newValue, appointment.getEndTime())) {
                            appointment.setStartTime(newValue);
                        } else {
                            System.out.println("Error: The new start time is not available.");
                        }
                        break;
                    case "endtime":
                        if(isTimeFree(appointments, appointment.getDate(), appointment.getStartTime(), newValue)) {
                            appointment.setEndTime(newValue);
                        } else {
                            System.out.println("Error: The new end time is not available.");
                        }
                        break;
                    default:
                        System.out.println("Error: Invalid option.");
                }
                break;
            }
        }
    }

    private boolean isDateAndTimeFree(List<Book> appointments, String date, String startTime, String endTime) {
        for (Book appointment : appointments) {
            if (appointment.getDate().equals(date)) {
                // Check if there is no overlap with existing appointments
                if (!(endTime.compareTo(appointment.getStartTime()) <= 0
                        || startTime.compareTo(appointment.getEndTime()) >= 0)) {
                    return false;
                }
            }
        }
        return true;}



    public boolean isTimeFree(List<Book> appointments, String date, String time, String endTime) {
        for (Book appointment : appointments) {
            if (appointment.getDate().equals(date)) {

                if (!(time.compareTo(appointment.getStartTime()) >= 0
                        && time.compareTo(appointment.getEndTime()) < 0)) {
                    return false;
                }
            }
        }
        return true;
    }
    public  boolean isDateFree(List<Book> appointments, String date, String startTime, String endTime) {
        for (Book appointment : appointments) {
            if (appointment.getDate().equals(date)) {
                return false;
            }
        }
        return true;
    }


}
