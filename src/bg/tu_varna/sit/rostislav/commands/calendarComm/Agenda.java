package bg.tu_varna.sit.rostislav.commands.calendarComm;


import bg.tu_varna.sit.rostislav.common.BulgarianHolidays;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Agenda  implements Command {

    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();
        Set<CalendarEvent> calendarEventForThatDay = new TreeSet<>((a,b)->a.getStartTime().compareTo(b.getStartTime()));
        if (!new BulgarianHolidays().containHoliday(new LocalDateAdapter().unmarshal(arguments.get(0)))) {

            for (CalendarEvent event : calendar.getCalendarEvent()) {
                if (event.getDate().equals(new LocalDateAdapter().unmarshal(arguments.get(0)))) {
                    calendarEventForThatDay.add(event);

                }
            }
            System.out.println("Chronological list of all commitments for that day < " + arguments.get(0) + " >:");
            //todo да си оправя принтирването!
            for (CalendarEvent event : calendarEventForThatDay) {
                System.out.println(event.toString());
            }
        }else {
            System.out.println("Today is holiday!There are no booked appointments!");
        }

    }
}
