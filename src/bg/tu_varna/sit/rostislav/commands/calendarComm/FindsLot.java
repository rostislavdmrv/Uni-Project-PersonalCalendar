package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FindsLot implements Command {
    @Override
    public void execute(List<String> arguments) throws Exception {
        LocalDate date = LocalDate.parse(arguments.get(0));
        int desiredDuration = Integer.parseInt(arguments.get(1));
        List<CalendarEvent> eventsForDate = new ArrayList<>();

        MyCalendar calendar = MyCalendar.getInstance();
        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().equals(date))
            {
                eventsForDate.add(event);


            }


        }

        // Търсете свободни слотове с желаната продължителност
        List<LocalTime> freeSlots = findFreeSlots(eventsForDate, desiredDuration);

        if (freeSlots.isEmpty()) {
            System.out.println("Не са намерени свободни слотове за деня.");
        } else {
            System.out.println("Свободни слотове за деня " + date + " с продължителност " + desiredDuration + " часа:");

            for (LocalTime slotStart : freeSlots) {
                LocalTime slotEnd = slotStart.plusHours(desiredDuration);
                System.out.println(slotStart + " - " + slotEnd);
            }
        }

    }
    private List<LocalTime> findFreeSlots(List<CalendarEvent> events, int desiredDuration) {
        List<LocalTime> freeSlots = new ArrayList<>();
        Collections.sort(events, Comparator.comparing(CalendarEvent::getStartTime));

        LocalTime currentTime = LocalTime.of(8, 0);
        LocalTime endOfDay = LocalTime.of(17, 0);

        while (currentTime.isBefore(endOfDay)) {
            boolean slotIsFree = true;

            for (CalendarEvent event : events) {
                if (event.getStartTime().isBefore(currentTime.plusHours(desiredDuration)) &&
                        event.getEndTime().isAfter(currentTime)) {
                    // Намерено е събитие, което се припокрива с текущия интервал
                    slotIsFree = false;
                    currentTime = event.getEndTime();
                    break;
                }
            }

            if (slotIsFree) {
                freeSlots.add(currentTime);
                currentTime = currentTime.plusHours(1); // Преминаване към следващия час
            }
            if (currentTime.plusHours(desiredDuration).isAfter(endOfDay)) {
                break; // Изход от цикъла, ако надвишим края на работния ден
            }
        }

        return freeSlots;
    }
}
