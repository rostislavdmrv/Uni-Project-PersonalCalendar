package bg.tu_varna.sit.rostislav.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarEvent {

    private String name;
    private LocalDate date;
    private LocalTime starTime;
    private LocalTime endTime;
    private String note;
    private boolean isHoliday;

    public CalendarEvent(String name, LocalDate date, LocalTime starTime, LocalTime endTime, String note, boolean isHoliday) {
        this.name = name;
        this.date = date;
        this.starTime = starTime;
        this.endTime = endTime;
        this.note = note;
        this.isHoliday = isHoliday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStarTime() {
        return starTime;
    }

    public void setStarTime(LocalTime starTime) {
        this.starTime = starTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", starTime=" + starTime +
                ", endTime=" + endTime +
                ", note='" + note + '\'' +
                ", isHoliday=" + isHoliday +
                '}';
    }
}
