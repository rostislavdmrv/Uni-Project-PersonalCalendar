package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Find  implements Command {
    private Set<CalendarEvent> calendarEvents;
    private String searching;
    private Set<CalendarEvent> foundedEvents;

    public Find(MyCalendar myCalendar, List<String> arguments) {
        foundedEvents = new HashSet<>();
        calendarEvents = myCalendar.getCalendarEvent();
        searching = arguments.get(0).toString().toLowerCase();
    }

    @Override
    public void execute(List<String> arguments) throws EventException {
        for (CalendarEvent event : calendarEvents) {

            if (event.getName().toLowerCase().contains(searching) || event.getNote().toLowerCase().contains(searching)) {
                foundedEvents.add(event);
            }
        }

        if (foundedEvents.isEmpty()){
            throw new EventException(ExceptionMessages.NOT_SUCCESS_FIND+ searching);}

        System.out.println(ConstantMessages.SUCCESS_FIND + searching + "': ");
        for (CalendarEvent event : foundedEvents) {
            System.out.println(event);
        }


    }


}
