package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.commands.calendarComm.Holiday;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.JAXBParser;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;
import com.sun.xml.bind.v2.TODO;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class Open  implements Command {
    public static String lastOpenedFileName ;

    @Override
    public void execute(List<String> arguments) throws Exception {
        MyCalendar calendar = MyCalendar.getInstance();
        File file = new File(arguments.get(0));


        if (!file.exists()){
            LocalDate localDate=  LocalDate.MIN;
            LocalTime startTime= LocalTime.MIN;
            LocalTime endTime= LocalTime.MIN;
            String name = "null";
            String note = "null";
            CalendarEvent calendarEvent=new  CalendarEvent(name,localDate,startTime,endTime,note);
            //calendar.addCalendarEvent(calendarEvent);

            JAXBParser jaxbParser=new JAXBParser();
            jaxbParser.writeToFile(calendar,file);
            lastOpenedFileName = arguments.get(0);
            System.out.println("Successfully opened "+arguments.get(0));
            //todo да направя валидацията за почивен ако е деля --- и въпроса с празния файл

        }else {

            MyCalendar firstOpen = new JAXBParser().readFromFile(new File(arguments.get(0)));
            Set<CalendarEvent> firstOpenSet = firstOpen.getCalendarEvent();


            for (CalendarEvent firstCalendarEvent : firstOpenSet) {
                String name = firstCalendarEvent.getName();
                LocalDate date = firstCalendarEvent.getDate();
                LocalTime startTime = firstCalendarEvent.getStartTime();
                LocalTime endTime = firstCalendarEvent.getEndTime();
                String note = firstCalendarEvent.getNote();
                boolean holiday = firstCalendarEvent.isHoliday();


                if (isWeekday(date) && isWithinWorkingHours(startTime) && isWithinWorkingHours(endTime)) {
                    if (!holiday){
                        if (!hasConflict(date, startTime, endTime, calendar)){
                            calendar.addCalendarEvent(new CalendarEvent(name,date, startTime, endTime,note ));
                            System.out.println("You have successfully booked an appointment");
                        }else {
                        System.out.println("Conflict: There is already an event scheduled for this time.");
                        }
                    }
                    else {
                        System.out.println("You can not booked an appointment.That day is Holiday!");
                    }

                } else {
                    System.out.println("Invalid date or time for a calendar event.");
                }

            }
            lastOpenedFileName = arguments.get(0);
            System.out.println("Successfully opened "+arguments.get(0));
        }
    }
    private boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    private boolean isWithinWorkingHours(LocalTime time) {
        return !time.isBefore(LocalTime.of(7, 59)) && !time.isAfter(LocalTime.of(17, 01));

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

}