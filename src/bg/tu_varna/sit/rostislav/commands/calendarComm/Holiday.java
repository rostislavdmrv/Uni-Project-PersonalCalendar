package bg.tu_varna.sit.rostislav.commands.calendarComm;


import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;


import java.time.LocalDate;
import java.util.List;

public class Holiday implements Command {
    private final MyCalendar myCalendar;
    /**
     * Date to search.
     */
    private LocalDate date;



    public Holiday(MyCalendar myCalendar, List<String> arguments) throws Exception {
        this.myCalendar = myCalendar;
        date= new LocalDateAdapter().unmarshal(arguments.get(0));
    }


    @Override
    public void execute(List<String> arguments) throws EventException {
        if (myCalendar.checkIsHoliday(date)){
            throw new EventException(ExceptionMessages.ALREADY_HOLIDAY_DATE);
        }else {
            myCalendar.addHoliday(date);
            for (CalendarEvent event : myCalendar.getCalendarEvent()) {
                if (event.getDate().equals(date)) {
                    myCalendar.removeCalendarEvent(event);
                }

            }
            System.out.println(ConstantMessages.HOLIDAY);
        }

    }
}



