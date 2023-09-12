package bg.tu_varna.sit.rostislav.commands.calendarComm;


import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.time.LocalDate;
import java.util.*;
import java.time.DayOfWeek;
import java.util.stream.Collectors;

public class BusyDays implements Command {
    private MyCalendar myCalendar;
    private LocalDate startDate;


    private LocalDate endDate;


    private Set<CalendarEvent> calendarEvents;
    List<DayOfWeek> sortedDays;


    public BusyDays(MyCalendar myCalendar, List<String> arguments) throws Exception {
        this.myCalendar = myCalendar;
        startDate= new LocalDateAdapter().unmarshal(arguments.get(0));
        endDate= new LocalDateAdapter().unmarshal(arguments.get(1));
        sortedDays = new ArrayList<>();
        calendarEvents=myCalendar.getCalendarEvent();
    }


    @Override
    public void execute(List<String> arguments) throws EventException {


        List<CalendarEvent> busyEvents = new ArrayList<>();

        for (CalendarEvent event : calendarEvents) {
            if (event.getDate().isAfter(startDate) && event.getDate().isBefore(endDate)) {
                if (!myCalendar.checkIsHoliday(event.getDate())) {
                    busyEvents.add(event);
                }
            }
        }

        Map<DayOfWeek, List<CalendarEvent>> eventsByDayOfWeek = new HashMap<>();
        for (CalendarEvent event : busyEvents) {
            DayOfWeek dayOfWeek = event.getDate().getDayOfWeek();
            eventsByDayOfWeek.computeIfAbsent(dayOfWeek, k -> new ArrayList<>()).add(event);
        }

        List<DayOfWeek> sortedDays = eventsByDayOfWeek.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    long hours1 = entry1.getValue().stream()
                            .mapToLong(event -> event.getEndTime().getHour() - event.getStartTime().getHour())
                            .sum();
                    long hours2 = entry2.getValue().stream()
                            .mapToLong(event -> event.getEndTime().getHour() - event.getStartTime().getHour())
                            .sum();
                    return Long.compare(hours2, hours1);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (DayOfWeek dayOfWeek : sortedDays) {
            List<CalendarEvent> events = eventsByDayOfWeek.get(dayOfWeek);
            events.sort(Comparator.comparing(CalendarEvent::getStartTime));

            int totalHours = 0;

            if (!events.isEmpty()) {
                for (CalendarEvent event : events) {
                    long hours = event.getEndTime().getHour() - event.getStartTime().getHour();
                    totalHours += hours;
                }

                System.out.println(dayOfWeek + ": - " + totalHours + " h");

                for (CalendarEvent event : events) {
                    System.out.println(event.getName() + " - " +
                            event.getStartTime() + " h to " + event.getEndTime() + " h");
                }
            }
        }
    }

}
