package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Book implements Command {
    public void execute(List<String> arguments) throws Exception {
        MyCalendar  calendar = MyCalendar.getInstance();

        calendar.addCalendarEvent(new CalendarEvent(arguments.get(0),
                new LocalDateAdapter().unmarshal(arguments.get(1)),
                new LocalTimeAdapter().unmarshal(arguments.get(2)),
                new LocalTimeAdapter().unmarshal(arguments.get(3)),
                arguments.get(4)));

    }
}
