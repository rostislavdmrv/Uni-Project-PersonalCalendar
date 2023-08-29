package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.util.List;

public class Change implements Command {

    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();
        CalendarEvent foundEvent=null;
        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().equals( new LocalDateAdapter().unmarshal(arguments.get(0)))
                    && event.getStarTime().equals(new LocalTimeAdapter().unmarshal(arguments.get(1)))
            )
            {
                foundEvent = event;

            }


        }


        if (arguments.get(3).equals("date")){
            foundEvent.setDate(new LocalDateAdapter().unmarshal(arguments.get(4)));
        }
        if (arguments.get(3).equals("starttime")){
            foundEvent.setStarTime(new LocalTimeAdapter().unmarshal(arguments.get(4)));
        }
        if (arguments.get(3).equals("enddate")){
            foundEvent.setEndTime(new LocalTimeAdapter().unmarshal(arguments.get(4)));
        }
        if (arguments.get(3).equals("name")){
            foundEvent.setName(arguments.get(4));
        }
        if (arguments.get(3).equals("note")){
            foundEvent.setNote(arguments.get(4));
        }
    }
}
