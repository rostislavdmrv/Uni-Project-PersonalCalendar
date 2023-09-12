package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class FindSlot implements Command {
    private Set<CalendarEvent> calendarEvents;
    private LocalDate date;
    private int hours;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<CalendarEvent> filteredCalendarEvents;

    public FindSlot(MyCalendar myCalendar, List<String> arguments) throws Exception {
        calendarEvents = myCalendar.getCalendarEvent();
        date = new LocalDateAdapter().unmarshal(arguments.get(0));
        hours = Integer.parseInt(arguments.get(1));
        startTime = LocalTime.of(8, 0);
        endTime = LocalTime.of(17, 0);
        filteredCalendarEvents = new ArrayList<>();
    }

    @Override
    public void execute(List<String> arguments) throws EventException {
        for (CalendarEvent event : calendarEvents) {
            if (event.getDate().equals(date)) {
                filteredCalendarEvents.add(event);
            }
        }

        // Търсете свободни слотове с желаната продължителност
        List<LocalTime> freeSlots = findFreeSlots();

        if (freeSlots.isEmpty()) {
            System.out.println(ConstantMessages.NO_FIND_SLOT);
        } else {
            System.out.println(ConstantMessages.AVAILABLE_SLOTS_1 + date + ConstantMessages.AVAILABLE_SLOTS_2 + hours + ConstantMessages.AVAILABLE_SLOTS_3);

            for (LocalTime slotStart : freeSlots) {
                LocalTime slotEnd = slotStart.plusHours(hours);
                System.out.println(slotStart + " - " + slotEnd +" h");
            }
        }
    }

    public List<LocalTime> findFreeSlots() {
        List<LocalTime> freeSlots = new ArrayList<>();
        Collections.sort(filteredCalendarEvents, Comparator.comparing(CalendarEvent::getStartTime));

        while (startTime.isBefore(endTime)) {
            boolean slotIsFree = true;

            for (CalendarEvent event : filteredCalendarEvents) {
                if (event.getStartTime().isBefore(startTime.plusHours(hours)) &&
                        event.getEndTime().isAfter(startTime)) {
                    slotIsFree = false;
                    startTime = event.getEndTime();
                    break;
                }
            }

            if (slotIsFree) {
                freeSlots.add(startTime);
                startTime = startTime.plusHours(1);
            }
            if (startTime.plusHours(hours).isAfter(endTime)) {
                break;
            }
        }

        return freeSlots;
    }
}
