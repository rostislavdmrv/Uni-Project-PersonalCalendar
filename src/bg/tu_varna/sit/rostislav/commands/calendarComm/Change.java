package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.BulgarianHolidays;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Change implements Command {

    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();
        CalendarEvent foundEvent=null;
        boolean flag = true;
        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (event.getDate().equals( new LocalDateAdapter().unmarshal(arguments.get(0)))
                    && event.getStartTime().equals(new LocalTimeAdapter().unmarshal(arguments.get(1)))
            )
            {
                foundEvent = event;
                calendar.removeCalendarEvent(event);

            }


        }


        if (arguments.get(2).equals("date")){
            LocalDate date = new LocalDateAdapter().unmarshal(arguments.get(3));
            if (isWeekday(date) && !isHoliday(date)){
                foundEvent.setDate(date);

            }else{
                System.out.println("Invalid date for a calendar event.");
                flag = false;
            }
        }
        if (arguments.get(2).equals("starttime")){
            LocalTime starttime = new LocalTimeAdapter().unmarshal(arguments.get(3));
            if (isWithinWorkingHours(starttime)){
                foundEvent.setStartTime(starttime);
            }else{
                System.out.println("Invalid starttime for a calendar event.");
                flag = false;
            }

        }
        if (arguments.get(2).equals("endtime")){
            LocalTime endtime = new LocalTimeAdapter().unmarshal(arguments.get(3));
            if (isWithinWorkingHours(endtime)){
                foundEvent.setEndTime(endtime);
            }else{
                System.out.println("Invalid endtime for a calendar event.");
                flag = false;
            }
        }
        if (arguments.get(2).equals("name")){
            foundEvent.setName(arguments.get(3));
        }
        if (arguments.get(2).equals("note")){
            foundEvent.setNote(arguments.get(3));
        }

        if (!hasConflict(foundEvent.getDate(), foundEvent.getStartTime(), foundEvent.getEndTime(), calendar)){
            calendar.addCalendarEvent(foundEvent);
            System.out.println("Successfully change your an appointment");
        } else{
        System.out.println("Conflict: There is already an event scheduled for this time.");
        System.out.println("No changes were made!\nTry again!");
        }


    }
    private boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
    private boolean hasConflict(LocalDate date, LocalTime startTime, LocalTime endTime, MyCalendar calendar) {
        for (CalendarEvent event : calendar.getCalendarEvent()) {
            if (date.equals(event.getDate())) {
                if ((startTime.isBefore(event.getEndTime()) && endTime.isAfter(event.getStartTime()))) {
                    return true; // Има конфликт
                }
            }
        }
        return false; // Няма конфликт
    }

    private boolean isWithinWorkingHours(LocalTime time) {
        return !time.isBefore(LocalTime.of(7, 59)) && !time.isAfter(LocalTime.of(17, 01));

    }
    private boolean isHoliday(LocalDate date) {
        return new BulgarianHolidays().containHoliday(date);
    }
}
