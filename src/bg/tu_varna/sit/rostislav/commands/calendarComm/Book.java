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
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;

public class Book implements Command {
    public void execute(List<String> arguments) throws Exception {

        MyCalendar calendar = MyCalendar.getInstance();

        LocalDate date = new LocalDateAdapter().unmarshal(arguments.get(1));
        LocalTime startTime = new LocalTimeAdapter().unmarshal(arguments.get(2));
        LocalTime endTime = new LocalTimeAdapter().unmarshal(arguments.get(3));




        if (isWeekday(date) && isWithinWorkingHours(startTime) && isWithinWorkingHours(endTime)) {
            if (!isHoliday(date)){
                if (!hasConflict(date, startTime, endTime, calendar)){
                    calendar.addCalendarEvent(new CalendarEvent(arguments.get(0),date, startTime, endTime, arguments.get(4) ));
                    System.out.println("You have successfully booked an appointment");}
                else
                {System.out.println("Conflict: There is already an event scheduled for this time.");}
            }
            else {
                System.out.println("You can not booked an appointment.That day is Holiday!");
            }

        } else {
            System.out.println("Invalid date or time for a calendar event.");
        }
    }

    private boolean isHoliday(LocalDate date) {
        return new BulgarianHolidays().containHoliday(date);
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

    private boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private boolean isWithinWorkingHours(LocalTime time) {
        return !time.isBefore(LocalTime.of(7, 58)) && !time.isAfter(LocalTime.of(17, 02));

    }


}
