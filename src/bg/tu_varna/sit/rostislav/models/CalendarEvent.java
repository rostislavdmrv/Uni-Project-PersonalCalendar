package bg.tu_varna.sit.rostislav.models;


import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;



@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarEvent  {
    @XmlElement
    private String eventName;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfEvent;
    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime startTime;
    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime endTime;
    @XmlElement
    private String note;
    @XmlAttribute
    private boolean isHoliday;

    public CalendarEvent() {
    }

    public CalendarEvent(String eventName,LocalDate dateOfEvent, LocalTime startTime, LocalTime endTime, String note) {
        this.setName(eventName);
        this.setDate(dateOfEvent);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setNote(note);
    }

    public CalendarEvent(LocalDate dateOfEvent, LocalTime startTime) {
        this.dateOfEvent = dateOfEvent;
        this.startTime = startTime;
    }

    public String getName() {
        return eventName;
    }

    public void setName(String name) {
        this.eventName = name;
    }

    public LocalDate getDate() {
        return dateOfEvent;
    }

    public void setDate(LocalDate dateOfEvent) {
        DayOfWeek dayOfWeek = dateOfEvent.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY ){
            throw new IllegalArgumentException(ExceptionMessages.DATE_CANNOT_BE_A_HOLIDAY);
        }

        this.dateOfEvent = dateOfEvent;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        if(startTime.isBefore(LocalTime.of(7, 59))){
            throw new IllegalArgumentException(ExceptionMessages.INCORRECT_STARTTIME);
        }
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime.isAfter(LocalTime.of(17, 01))){
            throw new IllegalArgumentException(ExceptionMessages.INCORRECT_ENDTIME);
        }
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }
    public boolean hasConflict(CalendarEvent calendarEvent){
        if(this.equals(calendarEvent)) {
            return true;
        }

        if(calendarEvent.dateOfEvent.equals(this.dateOfEvent)) {
            if(calendarEvent.getStartTime().isAfter(this.getStartTime()) && calendarEvent.getStartTime().isBefore(this.getEndTime())) {
                return true;
            }

            if(calendarEvent.getEndTime().isAfter(this.getStartTime()) && calendarEvent.getEndTime().isBefore(this.getEndTime())) {
                return true;
            }

            if(this.getStartTime().isAfter(calendarEvent.getStartTime()) && this.getStartTime().isBefore(calendarEvent.getEndTime())) {
                return true;
            }

            return false;
        }

        return false;

    }

    @Override
    public String toString() {
        String workDay;
        if (isHoliday){
            workDay=" No!";

        }else {
            workDay=" Yes!";

        }
        return "-------------------------------------------------------------------------------------------------------------------------------\n" +
                "CalendarEvent:        \n" +
                "Work day ->" + workDay +" | "+
                "NameOfEvent -> " + eventName + " | " +
                "DateOfEvent -> " + dateOfEvent + " | " +
                "with startTime : " + startTime + " | " +
                "with endTime : " + endTime +" | " +
                "Note -> " + note
                +"\n-------------------------------------------------------------------------------------------------------------------------------";
    }
}
