package bg.tu_varna.sit.rostislav.models;

import bg.tu_varna.sit.rostislav.common.BulgarianHolidays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement
public class MyCalendar {
    private String nameOfCalendar;
    private Set<CalendarEvent> calendarEventSet;
    private BulgarianHolidays bulgarianHolidays;

    public MyCalendar() {

        this.calendarEventSet = new HashSet<>();
        this.bulgarianHolidays = new BulgarianHolidays();
    }

    public  boolean addCalendarEvent(CalendarEvent calendarEvent){

         return this.calendarEventSet.add(calendarEvent);

    }
    public  boolean removeCalendarEvent(CalendarEvent calendarEvent){

        return this.calendarEventSet.remove(calendarEvent);

    }
    public  void clearEvents()  {
         this.calendarEventSet.clear();
    }

    public String getNameOfCalendar() {
        return nameOfCalendar;
    }

    public void setNameOfCalendar(String nameOfCalendar) {
        this.nameOfCalendar = nameOfCalendar;
    }


    @XmlElement
    public Set<CalendarEvent> getCalendarEvent() {
        return calendarEventSet;
    }

    public void setCalendarEventSet(Set<CalendarEvent> calendarEventSet) {
        this.calendarEventSet = calendarEventSet;
    }
    public boolean addAllEvents(Set<CalendarEvent> events){
        return calendarEventSet.addAll(events);
    }

    /**
     * Checks if this Calendar is empty.
     * @return true if this Calendar is empty, false otherwise
     */
    public boolean isEmpty(){
        return calendarEventSet.isEmpty();
    }

    public void addHoliday(LocalDate date) {
        bulgarianHolidays.addHoliday(date);
    }

    public boolean checkIsHoliday(LocalDate date) {

        return bulgarianHolidays.containHoliday(date);
    }
    public List<LocalDate> getHolidays() {
        return bulgarianHolidays.getHolidays();
    }

    public Set<CalendarEvent> compatibleWithCalendar(CalendarEvent calendarEvent) {
        Set<CalendarEvent> incompatibleEvents=new HashSet<>();


        for(CalendarEvent event :getCalendarEvent())
        {
            if(event.hasConflict(calendarEvent))
            {
                incompatibleEvents.add(event);
            }
        }

        return incompatibleEvents;
    }
}
