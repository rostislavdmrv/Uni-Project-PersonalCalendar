package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.time.LocalDate;
import java.util.*;
import java.time.DayOfWeek;
import java.util.stream.Collectors;

public class BusyDays implements Command {
    @Override
    public void execute(List<String> arguments) throws Exception {

        MyCalendar calendar = MyCalendar.getInstance();
        LocalDate startDate = LocalDate.parse(arguments.get(0));
        LocalDate endDate = LocalDate.parse(arguments.get(1));

        List<CalendarEvent> busyEvents = new ArrayList<>();

        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().isAfter(startDate) && event.getDate().isBefore(endDate)) {
                if (!isHoliday(event.getDate())) {
                    busyEvents.add(event);
                } else {
                    System.out.println("<" + (arguments.get(0)) + " > is Holiday !");
                    continue;
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
            System.out.println(dayOfWeek + ":");
            List<CalendarEvent> events = eventsByDayOfWeek.get(dayOfWeek);
            events.sort(Comparator.comparing(CalendarEvent::getStartTime));
            for (CalendarEvent event : events) {
                System.out.println(event.getName() + " - " +
                        event.getStartTime() + " h to " + event.getEndTime() + " h");
            }
        }

    }
    private boolean isHoliday(LocalDate date) {
        MyCalendar calendar = MyCalendar.getInstance();
        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().equals(date) && event.isHoliday()) {
                return true;
            }
        }
        return false;
    }
}
