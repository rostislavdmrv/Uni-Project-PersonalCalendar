package bg.tu_varna.sit.rostislav.commands.calendarComm;


import bg.tu_varna.sit.rostislav.common.BulgarianHolidays;
import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Agenda  implements Command {
    private Set<CalendarEvent> calendarEvents;

    private LocalDate date;
    private MyCalendar myCalendar;

    public Agenda(MyCalendar myCalendar, List<String> arguments) throws Exception {
        calendarEvents=myCalendar.getCalendarEvent();
        date= new LocalDateAdapter().unmarshal(arguments.get(0));
        this.myCalendar=myCalendar;
    }

    @Override
    public void execute(List<String> arguments) throws EventException {

        Set<CalendarEvent> calendarEventForThatDay = new TreeSet<>((a,b)->a.getStartTime().compareTo(b.getStartTime()));
        if (!myCalendar.checkIsHoliday(date)) {

            for (CalendarEvent event : calendarEvents) {
                if (event.getDate().equals(date)) {
                    calendarEventForThatDay.add(event);

                }
            }
            System.out.println(ConstantMessages.AGENDA + date + " :\n");

            for (CalendarEvent event : calendarEventForThatDay) {
                System.out.println(event.toString());
            }
        }else {
            System.out.println(ConstantMessages.NOT_AGENDA);
        }

    }
}
