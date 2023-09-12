package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Merge implements Command {
    private MyCalendar loadedCalendar;

    private File openedFile;

    private Map<String,Set<CalendarEvent>> passedCalendars;

    public Merge(CalendarsDatabase calendarsdatabase, List<String> instructions) throws Exception {
        loadedCalendar=calendarsdatabase.getMyCalendarRepository();
        openedFile=calendarsdatabase.getLoadedFile();
        this.passedCalendars=new HashMap<>();

        for(String fileName: instructions){
            if(calendarsdatabase.getLoadedFile().exists())
                passedCalendars.put(fileName,new HashSet<>(calendarsdatabase.getParser().readFromFile(new File(fileName)).getCalendarEvent()));
            else
                throw new Exception( fileName + ExceptionMessages.NOT_EXIST);
        }
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
        Set<CalendarEvent> loadedEvents= loadedCalendar.getCalendarEvent();

        for(Map.Entry<String,Set<CalendarEvent>> entry:passedCalendars.entrySet()){
            String fileName=entry.getKey();
            Set<CalendarEvent> newCalendarEvents=entry.getValue();

            Map<CalendarEvent, HashSet<CalendarEvent>> collisionMap = new HashMap<>(findCollidedEvents(loadedEvents, newCalendarEvents));

            if(collisionMap.isEmpty()){
                loadedCalendar.addAllEvents(newCalendarEvents);
                continue;
            }
            else{
                if(!submitAnswer(collisionMap,fileName))
                    throw new Exception(ExceptionMessages.MERGE_1 + fileName + " and " + openedFile.getName() + ExceptionMessages.MERGE_2);

                if (!resolveConflicts(loadedEvents, newCalendarEvents))
                    throw new Exception(ExceptionMessages.MERGE_1 + fileName + " and " + openedFile.getName() + ExceptionMessages.MERGE_2);
            }
        }


        System.out.println(ConstantMessages.SUCCESS_MERGE + openedFile.getName() );
    }

    private boolean submitAnswer(Map<CalendarEvent,HashSet<CalendarEvent>> collisionMap,String fileName) throws Exception {
        System.out.println(ConstantMessages.HAS_CONFLICT);
        for (Map.Entry<CalendarEvent, HashSet<CalendarEvent>> entry : collisionMap.entrySet()){
            System.out.println(entry.getKey());
            entry.getValue().stream().forEach(System.out::print);
            System.out.println();
        }

        System.out.println(ConstantMessages.SUBMIT);
        System.out.print(">");

        String option = new Scanner(System.in).nextLine() ;

        return option.equals("Y") || option.equals("y");
    }

    private boolean resolveConflicts(Set<CalendarEvent> loadedEvents,Set<CalendarEvent> newCalendarEvents) throws Exception {

        CalendarEvent collidedEvent=getCollidedEvent(loadedEvents,newCalendarEvents);

        if(collidedEvent==null)
            return true;

        CalendarEvent newEvent=createNewEvent(collidedEvent);

        loadedCalendar.addCalendarEvent(newEvent);
        newCalendarEvents.remove(collidedEvent);

        return resolveConflicts(loadedCalendar.getCalendarEvent(),newCalendarEvents);
    }

    private Map<CalendarEvent, HashSet<CalendarEvent>> findCollidedEvents(Set<CalendarEvent> firstCalendarEvents, Set<CalendarEvent> secondCalendarEvents){
        Map<CalendarEvent, HashSet<CalendarEvent>> collisionMap = new HashMap<>();
        HashSet<CalendarEvent> collisionSet=new HashSet<>();

        for (CalendarEvent firstCalendarEvent : firstCalendarEvents) {


            for (CalendarEvent secondCalendarEvent : secondCalendarEvents)
                if (secondCalendarEvent.getDate().equals(firstCalendarEvent.getDate()) &&
                        secondCalendarEvent.getStartTime().equals(firstCalendarEvent.getStartTime())) {
                    collisionSet.add(secondCalendarEvent);
                }

            if(!collisionSet.isEmpty())
                collisionMap.put(firstCalendarEvent,collisionSet);

        }

        return collisionMap;
    }

    private CalendarEvent getCollidedEvent(Set<CalendarEvent> firstCalendarEvents, Set<CalendarEvent> secondCalendarEvents){

        for (CalendarEvent firstCalendarEvent : firstCalendarEvents)
            for (CalendarEvent secondCalendarEvent : secondCalendarEvents)
                if (!secondCalendarEvent.hasConflict(firstCalendarEvent))
                    return secondCalendarEvent;

        return null;
    }


    private CalendarEvent createNewEvent(CalendarEvent collidedEvent) {
        System.out.println(ConstantMessages.EVENT);
        System.out.println(collidedEvent);
        System.out.println( collidedEvent.getName() + ConstantMessages.EVENT_PATTERN + collidedEvent.getNote());

        while (true) {
            System.out.print(">");
            String newInput =  new Scanner(System.in).nextLine();

            ArrayList<String> newInstructions = new ArrayList<>(List.of(newInput.split("\\s+")));

            if (newInstructions.isEmpty())
            { continue;}

            LocalDate date;
            LocalTime startTime;
            LocalTime endTime;

            try {
                date = parseDate(newInstructions.get(0));
                startTime= parseTime(newInstructions.get(1));
                endTime= parseTime(newInstructions.get(2));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            CalendarEvent newCalendarEvent;

            try {
                newCalendarEvent = new CalendarEvent(collidedEvent.getName(),date, startTime, endTime,  collidedEvent.getNote());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            Set<CalendarEvent> incompatibleEvents=loadedCalendar.compatibleWithCalendar(newCalendarEvent);

            if(incompatibleEvents.isEmpty()) {
                return newCalendarEvent;
            }

            displayIncompatibleEvents(newCalendarEvent,incompatibleEvents);

        }
    }
    private void displayIncompatibleEvents(CalendarEvent newCalendarEvent, Set<CalendarEvent> incompatibleEvents) {
        System.out.println(newCalendarEvent + ConstantMessages.INCOMPATIBLE);

        for (CalendarEvent incompatibleEvent : incompatibleEvents) {
            System.out.println(incompatibleEvent);
        }

    } private LocalDate parseDate(String input) {
        try {
            return new LocalDateAdapter().unmarshal(input);
        } catch (Exception e) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DATE);
        }
    }

    private LocalTime parseTime(String input) {
        try {
            return new LocalTimeAdapter().unmarshal(input);
        } catch (Exception e) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TIME);
        }
    }
}