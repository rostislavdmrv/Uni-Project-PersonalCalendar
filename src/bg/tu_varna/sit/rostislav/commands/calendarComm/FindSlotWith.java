package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
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
    private List<String> subListOfInstructions;

    /**
     * Filtered caledar events from loaded calendar
     */
    private HashSet<CalendarEvent> loadedCalendarEventsFiltered;


    public FindSlotWith(CalendarsDatabase calendarsDatabase, List<String> arguments) throws Exception {
        loadedCalendar= calendarsDatabase.getMyCalendarRepository();
        myCalendars=new HashSet<>();
        date= new LocalDateAdapter().unmarshal(arguments.get(0));
        subListOfInstructions=arguments.subList(0,2);

        loadedCalendarEventsFiltered=new HashSet<>(loadedCalendar.getCalendarEvent().stream().filter(item -> item.getDate().equals(date)).toList());

        for(int i=2;i<arguments.size();i++) {
            String externalFileDirectory = arguments.get(i);


            File file = new File(externalFileDirectory);

            if (calendarsDatabase.getLoadedFile().equals(file)) {
                System.out.println("You can't pass as an argument currently opened calendar.\n");
                continue;
            }

            if (calendarsDatabase.getLoadedFile().exists()) {
                MyCalendar calendar = calendarsDatabase.getParser().readFromFile(new File(externalFileDirectory));
                calendar.setNameOfCalendar(externalFileDirectory);
                myCalendars.add(calendar);
            } else{
                throw new Exception("File " + externalFileDirectory + "does not exist.\nOperation cancelled");
            }
        }
    }


    @Override
    public void execute(List<String> arguments) throws Exception {

        if(isExistInCalendar(date,loadedCalendar.getCalendarEvent())) {

            FindSlot findSlot = new FindSlot(loadedCalendar, subListOfInstructions);
            if (findSlot.findFreeSlots().isEmpty()) {
                throw new Exception("There is no free space in loaded calendar");
            }

        }

        for(MyCalendar externalPersonalCalendar:myCalendars){

            if(isExistInCalendar(date,loadedCalendar.getCalendarEvent())) {

                FindSlot findSlot = new FindSlot(externalPersonalCalendar, subListOfInstructions);

                if (findSlot.findFreeSlots().isEmpty())
                    continue;
            }

            HashSet<CalendarEvent> externalCalendarEventsFiltered=new HashSet<>(externalPersonalCalendar.getCalendarEvent().stream().filter(item -> item.getDate().equals(date)).toList());

            MyCalendar mixedPersonalCalendar =new MyCalendar();
            mixedPersonalCalendar.setCalendarEventSet(uniteCalendars(externalCalendarEventsFiltered));

            System.out.print("\n"+externalPersonalCalendar.getNameOfCalendar()+" - ");
            new FindSlot(mixedPersonalCalendar,subListOfInstructions).execute( arguments);
        }
    }

    //region InternalMethods
    /**
     * Checks if there are any events in the specified calendar for the given date.
     * @param dateToSearch      the date to search for events
     * @param calendarEvents    the calendar events to search
     * @return true if there are any events for the given date, false otherwise
     */
    private boolean isExistInCalendar(LocalDate dateToSearch,Set<CalendarEvent> calendarEvents){
        for (CalendarEvent event : calendarEvents) {
            if (event.getDate().equals(dateToSearch)) {
                if (!event.isHoliday()) {
                    return true;
                }
            }
        }

        return false;
    }


    private HashSet<CalendarEvent> uniteCalendars(HashSet<CalendarEvent> secondCalendar) throws Exception {
        HashSet<CalendarEvent> combinedEventsByDate=new HashSet<>();

        HashSet<CalendarEvent> bannedEvents=new HashSet<>();


        for(CalendarEvent firstCalendarEvent: loadedCalendarEventsFiltered){

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

        combinedEventsByDate.addAll(loadedCalendarEventsFiltered);
        combinedEventsByDate.addAll(secondCalendar);

        for(CalendarEvent event:bannedEvents){
            combinedEventsByDate.remove(event);
        }

        return combinedEventsByDate;
    }


    private CalendarEvent generateEvent(CalendarEvent firstCalendarEvent,CalendarEvent secondCalendarEvent) throws Exception {
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
