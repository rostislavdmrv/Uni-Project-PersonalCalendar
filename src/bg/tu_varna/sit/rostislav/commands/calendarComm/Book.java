package bg.tu_varna.sit.rostislav.commands.calendarComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.LocalDateAdapter;
import bg.tu_varna.sit.rostislav.parsers.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Book implements Command {
    private  MyCalendar myCalendar;

    private String name;

    private LocalDate date;


    private LocalTime startTime;

    private LocalTime endTime;



    private String note;


    public Book(MyCalendar myCalendar, List<String> arguments) throws Exception {
        this.myCalendar = myCalendar;
        this.name=arguments.get(0);
        this.date= new LocalDateAdapter().unmarshal(arguments.get(1));
        this.startTime= new LocalTimeAdapter().unmarshal(arguments.get(2));
        this.endTime= new LocalTimeAdapter().unmarshal(arguments.get(3));
        this.note=arguments.get(4);
    }


    @Override
    public void execute(List<String> arguments) throws EventException {


        CalendarEvent calendarEvent=new CalendarEvent(name,date,startTime,endTime,note);
        boolean incompatible= false;
        CalendarEvent incompatibleEvent = null;
        for(CalendarEvent event: myCalendar.getCalendarEvent())
        {
            if(calendarEvent.hasConflict(event))
            {
                incompatible=true;
                incompatibleEvent=event;
            }
        }
        if(myCalendar.checkIsHoliday(date)){
            throw new IllegalArgumentException(ExceptionMessages.HOLIDAY_DATE);
        }

        if(incompatible)
            throw new EventException(ExceptionMessages.IMPOSSIBLE_BOOK+incompatibleEvent);
        else {
            myCalendar.addCalendarEvent(calendarEvent);
            System.out.println(ConstantMessages.SUCCESS_BOOK +calendarEvent);
        }


    }
}







