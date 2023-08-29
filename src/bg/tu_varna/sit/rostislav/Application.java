package bg.tu_varna.sit.rostislav;

import bg.tu_varna.sit.rostislav.cli.cli;
import bg.tu_varna.sit.rostislav.commands.calendarComm.Unbook;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.models.CalendarEvent;
import bg.tu_varna.sit.rostislav.parsers.JAXBParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) throws Exception {

        cli.run();
        /*MyCalendar myCalendar = MyCalendar.getInstance();
        LocalDate localDate= LocalDate.parse("2023-08-29");
        LocalTime startTime= LocalTime.parse("18:33");
        LocalTime endTime= LocalTime.parse("19:00");
        String name = "null";
        String note = "null";
        CalendarEvent calendarEvent=new  CalendarEvent(name,localDate,startTime,endTime,note);
        System.out.println(myCalendar.addCalendarEvent(calendarEvent));
        System.out.println(myCalendar.getCalendarEvent());


       JAXBParser jaxbParser=new JAXBParser();
        jaxbParser.writeToFile(myCalendar,new File("file.xml"));*/

        //MyCalendar myCalendar1 = MyCalendar.getInstance();
       /* LocalDate localDate1= LocalDate.parse("2023-08-28");
        LocalTime startTime1= LocalTime.parse("14:59");
        LocalTime endTime1= LocalTime.parse("15:36");
        String name1 = "fff";
        String note1 = "ggg";
        CalendarEvent calendarEvent1=new  CalendarEvent(name1,localDate1,startTime1,endTime1,note1);
        System.out.println(myCalendar.addCalendarEvent(calendarEvent1));
        System.out.println(myCalendar.getCalendarEvent());

        JAXBParser jaxbParser1=new JAXBParser();
        jaxbParser1.writeToFile(myCalendar,new File("file.xml"));*/

       /* List<String> proba = new ArrayList<>();
        proba.add(String.valueOf(localDate));
        proba.add(String.valueOf(startTime));
        proba.add(String.valueOf(endTime));

        new Unbook().execute(proba);
        System.out.println(myCalendar.getCalendarEvent());*/







    }
}
