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
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

public class Change implements Command {
    private final MyCalendar myCalendar;

    /**
     * Date of the event to change.
     */
    private LocalDate date;
    /**
     * Start time of the event to change.
     */
    private LocalTime startTime;
    /**
     * New option to change.
     */
    private String option;
    /**
     * New value of the option
     */
    private String newValue;


    public Change(MyCalendar myCalendar, List<String> arguments) throws Exception {
        this.myCalendar = myCalendar;
        date= new LocalDateAdapter().unmarshal(arguments.get(0));
        startTime= new LocalTimeAdapter().unmarshal(arguments.get(1));
        option=arguments.get(2);
        newValue=arguments.get(3);
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
       HashSet<CalendarEvent> calendarEvents=new HashSet<>(myCalendar.getCalendarEvent());

        CalendarEvent newEvent = null;

        for (CalendarEvent event : calendarEvents) {
            if (event.getDate().equals(date) && event.getStartTime().equals(startTime)){
                newEvent = event;
                changeWithNewValue(option,newValue,newEvent,event);
            }

        }
        if(calendarEvents.isEmpty()) {
            throw new EventException(ExceptionMessages.NO_SUCH_EVENT);
        }


        System.out.println(ConstantMessages.SUCCESS_CHANGE);


    }
    private void changeWithNewValue(String option,String newValue,CalendarEvent newEvent,CalendarEvent oldEvent) throws Exception {
         if (option.equals("date")){
            LocalDate date = new LocalDateAdapter().unmarshal(newValue);
            newEvent.setDate(date);
            UpdateCalendarEventSet(newEvent,oldEvent);


        }
        else if (option.equals("starttime")){
            LocalTime startTime = new LocalTimeAdapter().unmarshal(newValue);
            newEvent.setStartTime(startTime);
            UpdateCalendarEventSet(newEvent,oldEvent);


        }
        else if (option.equals("endtime")){
            LocalTime endTime = new LocalTimeAdapter().unmarshal(newValue);
            newEvent.setEndTime(endTime);
            UpdateCalendarEventSet(newEvent,oldEvent);

        }
        else if (option.equals("name")){
            newEvent.setName(newValue);
        }
        else if (option.equals("note")){
            newEvent.setNote(newValue);
        }
        else {
            throw new Exception(option +ExceptionMessages.WRONG_OPTION);
        }
    }



    private void UpdateCalendarEventSet(CalendarEvent newEvent,CalendarEvent oldEvent) throws EventException {
        boolean isCompatible=true;
        HashSet<CalendarEvent> incompatibleEvents = new HashSet<>();
        HashSet<CalendarEvent> calendarEvents= (HashSet<CalendarEvent>) myCalendar.getCalendarEvent();

        for(CalendarEvent event:calendarEvents)
        {
            if(event.equals(oldEvent))
                continue;

            if(newEvent.hasConflict(event))
            {
                isCompatible=false;
                incompatibleEvents.add(event);
            }
        }

        if(isCompatible) {
            myCalendar.removeCalendarEvent(oldEvent);
            myCalendar.addCalendarEvent(newEvent);
        }
        else {
            String description = "";
            for (CalendarEvent event : incompatibleEvents) {
                description += String.format("%s\n", event);
            }

            throw new EventException(ExceptionMessages.WRONG_CHANGE + description);
        }
    }
}
