package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.JAXBParser;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class Open  implements Command {

    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar firstOpen = new JAXBParser().readFromFile(new File(arguments.get(0)));
        Set<CalendarEvent> firstOpenSet = firstOpen.getCalendarEvent();

        for (CalendarEvent firstCalendarEvent : firstOpenSet) {
            String name = firstCalendarEvent.getName();
            LocalDate date = firstCalendarEvent.getDate();
            LocalTime starTime = firstCalendarEvent.getStarTime();
            LocalTime endTime = firstCalendarEvent.getEndTime();
            String note = firstCalendarEvent.getNote();


            MyCalendar  calendar = MyCalendar.getInstance();
            calendar.addCalendarEvent(new CalendarEvent(name,date,starTime,endTime,note));

        }




    }
}
