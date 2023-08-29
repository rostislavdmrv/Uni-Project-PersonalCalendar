package bg.tu_varna.sit.rostislav.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@XmlRootElement
public class MyCalendar implements Serializable {

    private static MyCalendar instance;
    private Set<CalendarEvent> calendarEventSet;

    private MyCalendar() {
        this.calendarEventSet = new TreeSet<>((a,b)->a.getStarTime().compareTo(b.getStarTime()));
    }

    public static MyCalendar getInstance(){
        if(instance == null){
            instance = new MyCalendar();

        }
        return instance;
    }

    public  boolean addCalendarEvent(CalendarEvent calendarEvent){
         return this.calendarEventSet.add(calendarEvent);

    }
    public  boolean removeCalendarEvent(CalendarEvent calendarEvent){

        return this.calendarEventSet.remove(calendarEvent);

    }


@XmlElement
    public Set<CalendarEvent> getCalendarEvent() {
        return calendarEventSet;
    }
}