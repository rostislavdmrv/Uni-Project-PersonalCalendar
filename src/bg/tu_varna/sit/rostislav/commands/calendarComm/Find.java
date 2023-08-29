package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.util.List;

public class Find  implements Command {

    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();

        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getName().contains(arguments.get(0)) || event.getNote().contains(arguments.get(0)))
            {
                System.out.println(event.toString());


            }


        }

    }
}
