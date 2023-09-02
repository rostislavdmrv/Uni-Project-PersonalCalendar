package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Unbook implements Command {


    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();
        boolean foundElement = false;


        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().equals( new LocalDateAdapter().unmarshal(arguments.get(0)))
                    && event.getStartTime().equals(new LocalTimeAdapter().unmarshal(arguments.get(1)))
                    && event.getEndTime().equals(new LocalTimeAdapter().unmarshal(arguments.get(2))))
            {
                calendar.removeCalendarEvent(event);
                foundElement = true;
                break;

            }

        }
        System.out.println("You have successfully canceled your pre-booked appointment!");
        if (!foundElement){
        System.out.println("No an appointment was found according to your requirements!");}






    }
}
