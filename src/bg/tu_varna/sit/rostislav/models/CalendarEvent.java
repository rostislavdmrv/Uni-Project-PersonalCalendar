package bg.tu_varna.sit.rostislav.models;


import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;



@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarEvent  implements Serializable {
    @XmlElement
    private String name;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime starTime;
    @XmlElement
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime endTime;
    @XmlElement
    private String note;
    @XmlAttribute
    private boolean isHoliday;

    public CalendarEvent() {
    }

    public CalendarEvent(String name,LocalDate date, LocalTime starTime, LocalTime endTime, String note) {
        this.name = name;
        this.date = date;
        this.starTime = starTime;
        this.endTime = endTime;
        this.note = note;
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
