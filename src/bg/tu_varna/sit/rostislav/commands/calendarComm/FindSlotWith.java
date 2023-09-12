package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindSlotWith implements Command {
    private MyCalendar loadedCalendar;

    /**
     * Date to search from.
     */
    private LocalDate date;

    /**
     * Set of calendars to be merged.
     */
    private Set<MyCalendar> myCalendars;

    /**
     * Sublist of passed arguments.
     */
    private List<String> arguments;

    /**
     * Filtered caledar events from loaded calendar
     */
    private HashSet<CalendarEvent> loadedCalendarEventsFilter;


    public FindSlotWith(CalendarsDatabase calendarsDatabase, List<String> arguments) throws Exception {
        loadedCalendar= calendarsDatabase.getMyCalendarRepository();
        myCalendars=new HashSet<>();
        date= new LocalDateAdapter().unmarshal(arguments.get(0));
        this.arguments=arguments.subList(0,2);

        loadedCalendarEventsFilter=new HashSet<>(loadedCalendar.getCalendarEvent().stream().filter(item -> item.getDate().equals(date)).toList());

        for(int i=2;i<arguments.size();i++) {
            String externalFileDirectory = arguments.get(i);


            File file = new File(externalFileDirectory);

            if (calendarsDatabase.getLoadedFile().equals(file)) {
                System.out.println(ConstantMessages.CAN_NOT_PASS_ARGUMENT);
                continue;
            }

            if (calendarsDatabase.getLoadedFile().exists()) {
                MyCalendar calendar = calendarsDatabase.getParser().readFromFile(new File(externalFileDirectory));
                calendar.setNameOfCalendar(externalFileDirectory);
                myCalendars.add(calendar);
            } else{
                throw new Exception(externalFileDirectory + ExceptionMessages.NOT_EXIST);
            }
        }
    }


    @Override
    public void execute(List<String> arguments) throws Exception {

        if(isExistInCalendar(date,loadedCalendar.getCalendarEvent())) {

            FindSlot findSlot = new FindSlot(loadedCalendar, arguments);
            if (findSlot.findFreeSlots().isEmpty()) {
                throw new Exception(ExceptionMessages.NO_FREE_SPACE);
            }

        }

        for(MyCalendar externalPersonalCalendar:myCalendars){

            if(isExistInCalendar(date,loadedCalendar.getCalendarEvent())) {

                FindSlot findSlot = new FindSlot(externalPersonalCalendar, arguments);

                if (findSlot.findFreeSlots().isEmpty())
                    continue;
            }

            HashSet<CalendarEvent> externalCalendarEventsFiltered=new HashSet<>(externalPersonalCalendar.getCalendarEvent().stream().filter(item -> item.getDate().equals(date)).toList());

            MyCalendar mixedPersonalCalendar =new MyCalendar();
            mixedPersonalCalendar.setCalendarEventSet(uniteCalendars(externalCalendarEventsFiltered));

            System.out.print(ConstantMessages.COMBINE +externalPersonalCalendar.getNameOfCalendar()+"-");
            new FindSlot(mixedPersonalCalendar,arguments).execute( arguments);
        }
    }


    private boolean isExistInCalendar(LocalDate dateToSearch,Set<CalendarEvent> calendarEvents){
        for (CalendarEvent event : calendarEvents) {
            if (event.getDate().equals(dateToSearch)) {
                return true;

            }
        }

        return false;
    }


    private HashSet<CalendarEvent> uniteCalendars(HashSet<CalendarEvent> secondCalendar)  {
        HashSet<CalendarEvent> combinedEventsByDate=new HashSet<>();

        HashSet<CalendarEvent> bannedEvents=new HashSet<>();


        for(CalendarEvent firstCalendarEvent: loadedCalendarEventsFilter){

            for(CalendarEvent secondCalendarEvent: secondCalendar){

                if(combinedEventsByDate.contains(secondCalendarEvent)||combinedEventsByDate.contains(firstCalendarEvent))
                    continue;

                if(firstCalendarEvent.equals(secondCalendarEvent)) {
                    combinedEventsByDate.add(firstCalendarEvent);
                    continue;
                }

                if(!firstCalendarEvent.hasConflict(secondCalendarEvent))
                    continue;

                CalendarEvent newCalendarEvent=generateEvent(firstCalendarEvent,secondCalendarEvent);

                for(CalendarEvent event:combinedEventsByDate){
                    if(newCalendarEvent.hasConflict(event)){
                        combinedEventsByDate.remove(event);
                        newCalendarEvent=generateEvent(newCalendarEvent,event);
                        break;
                    }
                }

                combinedEventsByDate.add(newCalendarEvent);
                bannedEvents.add(firstCalendarEvent);
                bannedEvents.add(secondCalendarEvent);
            }
        }

        combinedEventsByDate.addAll(loadedCalendarEventsFilter);
        combinedEventsByDate.addAll(secondCalendar);

        for(CalendarEvent event:bannedEvents){
            combinedEventsByDate.remove(event);
        }

        return combinedEventsByDate;
    }


    private CalendarEvent generateEvent(CalendarEvent firstCalendarEvent,CalendarEvent secondCalendarEvent)  {
        LocalTime minStartTime;
        LocalTime maxEndTime;

        if(firstCalendarEvent.getStartTime().isAfter(secondCalendarEvent.getStartTime()))
            minStartTime=secondCalendarEvent.getStartTime();
        else
            minStartTime=firstCalendarEvent.getStartTime();

        if(firstCalendarEvent.getEndTime().isAfter(secondCalendarEvent.getEndTime()))
            maxEndTime=firstCalendarEvent.getEndTime();
        else
            maxEndTime=secondCalendarEvent.getEndTime();

        return new CalendarEvent("", date, minStartTime, maxEndTime, "");
    }
}
