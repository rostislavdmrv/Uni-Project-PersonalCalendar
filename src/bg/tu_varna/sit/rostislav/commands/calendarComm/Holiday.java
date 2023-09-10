package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.BulgarianHolidays;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Holiday implements Command {
    private final MyCalendar myCalendar;
    /**
     * Date to search.
     */
    private LocalDate date;



    public Holiday(MyCalendar myCalendar, List<String> arguments) throws Exception {
        this.myCalendar = myCalendar;
        date= new LocalDateAdapter().unmarshal(arguments.get(1));
    }


    @Override
    public void execute(List<String> arguments) throws Exception {
        if (myCalendar.checkIsHoliday(date)){
            throw new Exception("That date is already holiday");
        }else {
            myCalendar.addHoliday(date);
            for (CalendarEvent event : myCalendar.getCalendarEvent()) {
                if (event.getDate().equals(date)) {
                    myCalendar.removeCalendarEvent(event);
                }

            }
            System.out.println("TODAY is holiday ! All events for today was canceled!");
        }

    }
}



