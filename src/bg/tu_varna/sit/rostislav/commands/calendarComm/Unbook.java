package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Unbook implements Command {
    private Set<CalendarEvent> loadedCalendarEvents;
    /**
     * Date of the event to unbook
     */
    private LocalDate date;
    /**
     * Start time of the event to unbook.
     */
    private LocalTime startTime;
    /**
     * End time of the event to unbook.
     */
    private LocalTime endTime;
    public Unbook(MyCalendar myCalendar, List<String> arguments) throws Exception {

        this.loadedCalendarEvents=myCalendar.getCalendarEvent();
        this.date= new LocalDateAdapter().unmarshal(arguments.get(0));
        this.startTime= new LocalTimeAdapter().unmarshal(arguments.get(1));
        this.endTime= new LocalTimeAdapter().unmarshal(arguments.get(2));
    }


    @Override
    public void execute(List<String> arguments) throws EventException {

        Iterator<CalendarEvent> iterator = loadedCalendarEvents.iterator();
        boolean eventRemoved = false;

        while (iterator.hasNext()) {
            CalendarEvent foundEvent = iterator.next();
            if (foundEvent.getDate().equals(date) && foundEvent.getStartTime().equals(startTime) && foundEvent.getEndTime().equals(endTime)) {
                iterator.remove();
                eventRemoved = true;
            }
        }

        if (eventRemoved) {
            System.out.printf(ConstantMessages.SUCCESS_UNBOOK);
        } else {
            throw new EventException(ExceptionMessages.NOT_SUCCESS_UNBOOK);
        }




    }
}
