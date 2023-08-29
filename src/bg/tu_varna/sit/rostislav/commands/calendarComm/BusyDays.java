package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BusyDays implements Command {
    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();
        Set<CalendarEvent> busyDays  = new TreeSet<>((a,b)->a.getStarTime().compareTo(b.getStarTime()));

        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().isAfter( new LocalDateAdapter().unmarshal(arguments.get(0))) && event.getDate().isBefore( new LocalDateAdapter().unmarshal(arguments.get(1))) )
            {
                busyDays.add(event);

            }

        }
        for (CalendarEvent event : busyDays) {
            System.out.println(event.toString());

        }

    }
}
