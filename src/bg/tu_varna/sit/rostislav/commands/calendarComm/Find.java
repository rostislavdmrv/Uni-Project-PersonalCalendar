package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Find  implements Command {
    private Set<CalendarEvent> calendarEvents;
    private String searching;
    private Set<CalendarEvent> foundedEvents;

    public Find(MyCalendar myCalendar, List<String> arguments) {
        foundedEvents = new HashSet<>();
        calendarEvents = myCalendar.getCalendarEvent();
        searching = String.join(" ", arguments).toLowerCase();
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
        for (CalendarEvent event : calendarEvents) {

            if (event.getName().contains(searching) || event.getNote().contains(searching)) {
                foundedEvents.add(event);
            }
        }

        if (foundedEvents.isEmpty()){
            throw new Exception("There are no events that contain: " + searching);}

        System.out.println("Here are the events that contain '" + searching + "': ");
        for (CalendarEvent event : foundedEvents) {
            System.out.println(event);
        }


    }


}
