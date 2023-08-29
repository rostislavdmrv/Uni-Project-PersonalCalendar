package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.util.List;

public class Holiday implements Command {
    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();

        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().equals( new LocalDateAdapter().unmarshal(arguments.get(0))))
            {
                event.setHoliday(true);
            }


        }
        System.out.println("fffff");


    }
}
