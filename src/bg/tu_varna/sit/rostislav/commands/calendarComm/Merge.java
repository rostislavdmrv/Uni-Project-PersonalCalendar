package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Merge implements Command {
    private MyCalendar loadedCalendar;
    private File openedFile;
    private Map<String, Set<CalendarEvent>> passedCalendars;

    public Merge(CalendarsDatabase calendarsDatabase, List<String> arguments) throws Exception {
        loadedCalendar = calendarsDatabase.getMyCalendarRepository();
        openedFile = calendarsDatabase.getLoadedFile();
        this.passedCalendars = new HashMap<>();

        for (String fileName : arguments) {

            File file = new File(fileName);
            if (file.exists()) {
                passedCalendars.put(fileName, new HashSet<>(calendarsDatabase.getParser().readFromFile(file).getCalendarEvent()));
            } else {
                throw new Exception("File " + fileName + " does not exist.\nMerging was canceled");
            }
        }
    }

    public void execute(List<String>arguments) throws Exception {
        Set<CalendarEvent> loadedEvents = loadedCalendar.getCalendarEvent();

        for (Map.Entry<String, Set<CalendarEvent>> entry : passedCalendars.entrySet()) {
            String fileName = entry.getKey();
            Set<CalendarEvent> newCalendarEvents = entry.getValue();

            Map<CalendarEvent, Set<CalendarEvent>> collisionMap = findCollidedEvents(loadedEvents, newCalendarEvents);

            if (collisionMap.isEmpty()) {
                loadedEvents.addAll(newCalendarEvents);
                /*for (CalendarEvent newEvent : newCalendarEvents) {
                    loadedCalendar.addCalendarEvent(newEvent);
                }*/
            } else {
                if (!submitAnswer(collisionMap, fileName)) {
                    throw new Exception("Merging between " + fileName + " and " + openedFile.getName() + " was stopped.");
                }

                if (!resolveConflicts(loadedEvents, newCalendarEvents)) {
                    throw new Exception("Merging between " + fileName + " and " + openedFile.getName() + " was stopped.");
                }
            }
        }

        System.out.println("All calendars were successfully merged to " + openedFile.getName() + ".");
    }
    private boolean submitAnswer(Map<CalendarEvent, Set<CalendarEvent>> collisionMap, String fileName) throws Exception {
        displayConflictEventInfo(collisionMap, fileName);
        System.out.println("You need to edit your events.Press 'Y' to accept.\n > ");
        String option = new Scanner(System.in).nextLine();
        return option.equalsIgnoreCase("Y");
    }

    private void displayConflictEventInfo(Map<CalendarEvent, Set<CalendarEvent>> collisionMap, String fileName) {
        System.out.println("There is conflict between events: ");
        for (Map.Entry<CalendarEvent, Set<CalendarEvent>> entry : collisionMap.entrySet()) {
            System.out.println(entry.getKey() + " from " + openedFile.getName() + " conflict with:");
            entry.getValue().forEach(System.out::print);
            System.out.println("from: " + fileName + "\n");
        }
    }
    private boolean resolveConflicts(Set<CalendarEvent> loadedEvents, Set<CalendarEvent> newCalendarEvents) throws Exception {
        CalendarEvent collidedEvent = findConflictedEvent(loadedEvents, newCalendarEvents);

        if (collidedEvent == null) {
            return true;
        }

        CalendarEvent newEvent = createNewEvent(collidedEvent);
        loadedCalendar.addCalendarEvent(newEvent);
        newCalendarEvents.remove(collidedEvent);

        return resolveConflicts(loadedCalendar.getCalendarEvent(), newCalendarEvents);

    }
    private CalendarEvent findConflictedEvent(Set<CalendarEvent> firstCalendarEvents, Set<CalendarEvent> secondCalendarEvents) {
        for (CalendarEvent firstCalendarEvent : firstCalendarEvents) {
            for (CalendarEvent secondCalendarEvent : secondCalendarEvents) {
                if (secondCalendarEvent.hasConflict(firstCalendarEvent)) {
                    return secondCalendarEvent;
                }
            }
        }

        return null;
    }
    private Map<CalendarEvent, Set<CalendarEvent>> findCollidedEvents(
            Set<CalendarEvent> firstCalendarEvents, Set<CalendarEvent> secondCalendarEvents) {
        Map<CalendarEvent, Set<CalendarEvent>> collisionMap = new HashMap<>();

        firstCalendarEvents.forEach(firstEvent -> {
            Set<CalendarEvent> collisionSet = secondCalendarEvents.stream()
                    .filter(secondEvent -> secondEvent.hasConflict(firstEvent))
                    .collect(Collectors.toSet());

            if (!collisionSet.isEmpty()) {
                collisionMap.put(firstEvent, collisionSet);
            }
        });

        return collisionMap;
    }


    private CalendarEvent createNewEvent(CalendarEvent collidedEvent) {
        System.out.println();
        System.out.println("Event to change: ");
        System.out.println(collidedEvent);
        System.out.println("New values: <date> <startTime> <endTime> " + collidedEvent.getName() + " " + collidedEvent.getNote());

        while (true) {
            String newInput = getValidInput();
            String[] newInstructions = newInput.split("\\s+");
            LocalDate date = parseDate(newInstructions[0]);
            LocalTime startTime = parseTime(newInstructions[1]);
            LocalTime endTime = parseTime(newInstructions[2]);
            CalendarEvent newCalendarEvent = createCalendarEvent(collidedEvent, date, startTime, endTime);
            Set<CalendarEvent> incompatibleEvents = loadedCalendar.compatibleWithCalendar(newCalendarEvent);
            if (incompatibleEvents.isEmpty()) {
                return newCalendarEvent;
            }
            displayIncompatibleEvents(newCalendarEvent, incompatibleEvents);
        }
    }

    private String getValidInput() {
        while (true) {
            System.out.print(">");
            String newInput = new Scanner(System.in).nextLine();

            if (!newInput.isEmpty()) {
                return newInput;
            }
        }
    }

    private LocalDate parseDate(String input) {
        try {
            return new LocalDateAdapter().unmarshal(input);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }

    private LocalTime parseTime(String input) {
        try {
            return new LocalTimeAdapter().unmarshal(input);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format.");
        }
    }

    private CalendarEvent createCalendarEvent(CalendarEvent conflictEvent, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            return new CalendarEvent(
                    conflictEvent.getName(), date, startTime, endTime, conflictEvent.getNote());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Failed to create a new calendar event.");
        }
    }

    private void displayIncompatibleEvents(CalendarEvent newCalendarEvent, Set<CalendarEvent> incompatibleEvents) {
        System.out.println(newCalendarEvent + "\n is currently incompatible with:");

        for (CalendarEvent incompatibleEvent : incompatibleEvents) {
            System.out.println(incompatibleEvent);
        }

        System.out.println("Please type again");
    }
}
