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
import java.util.HashSet;
import java.util.List;

public class Change implements Command {
    private final MyCalendar myCalendar;

    /**
     * Date of the event to change.
     */
    private LocalDate date;
    /**
     * Start time of the event to change.
     */
    private LocalTime startTime;
    /**
     * New option to change.
     */
    private String option;
    /**
     * New value of the option
     */
    private String newValue;


    public Change(MyCalendar myCalendar, List<String> arguments) throws Exception {
        this.myCalendar = myCalendar;
        date= new LocalDateAdapter().unmarshal(arguments.get(0));
        startTime= new LocalTimeAdapter().unmarshal(arguments.get(1));
        option=arguments.get(2);
        newValue=arguments.get(3);
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
       // може и да не се прави копие на  myCalendar.getCalendarEvent() а дирекно да се изолзва !
        HashSet<CalendarEvent> calendarEvents=new HashSet<>(myCalendar.getCalendarEvent());

        CalendarEvent newEvent = null;

        for (CalendarEvent event : calendarEvents) {
            if (event.getDate().equals(date) && event.getStartTime().equals(startTime)){
                newEvent = event;
                checkOptionAndGetDecision(option,newValue,newEvent,event);
            }

        }
        if(calendarEvents.isEmpty())
        { throw new Exception("There is no such event in the calendar.");}


        System.out.println("The event was successfully updated.");


       /* if (option.equals("date")){
            LocalDate date = new LocalDateAdapter().unmarshal(newValue);
            foundEvent.setDate(date);
            *//*System.out.println("Invalid date for a calendar event.");
                flag = false;*//*
        }
        else if (option.equals("starttime")){
            LocalTime startTime = new LocalTimeAdapter().unmarshal(newValue);
            foundEvent.setStartTime(startTime);
            *//*System.out.println("Invalid starttime for a calendar event.");
            flag = false;*//*

        }
        else if (option.equals("endtime")){
            LocalTime endTime = new LocalTimeAdapter().unmarshal(newValue);
            foundEvent.setEndTime(endTime);
            *//*System.out.println("Invalid endtime for a calendar event.");
                flag = false;*//*
        }
        else if (option.equals("name")){
            foundEvent.setName(newValue);
        }
        else if (option.equals("note")){
            foundEvent.setNote(newValue);
        }
        else {
            throw new Exception(option+" is not recognized as internal command.");
        }

        if (hasConflict(foundEvent.getDate(), foundEvent.getStartTime(), foundEvent.getEndTime(), myCalendar)){
            calendar.addCalendarEvent(foundEvent);
            System.out.println("Successfully change your an appointment");
        } else{
        System.out.println("Conflict: There is already an event scheduled for this time.");
        System.out.println("No changes were made!\nTry again!");
        }*/


    }
    private void checkOptionAndGetDecision(String option,String newValue,CalendarEvent newEvent,CalendarEvent oldEvent) throws Exception {
         if (option.equals("date")){
            LocalDate date = new LocalDateAdapter().unmarshal(newValue);
            newEvent.setDate(date);
            UpdateCalendarEventSet(newEvent,oldEvent);


        }
        else if (option.equals("starttime")){
            LocalTime startTime = new LocalTimeAdapter().unmarshal(newValue);
            newEvent.setStartTime(startTime);
            UpdateCalendarEventSet(newEvent,oldEvent);


        }
        else if (option.equals("endtime")){
            LocalTime endTime = new LocalTimeAdapter().unmarshal(newValue);
            newEvent.setEndTime(endTime);
            UpdateCalendarEventSet(newEvent,oldEvent);

        }
        else if (option.equals("name")){
            newEvent.setName(newValue);
        }
        else if (option.equals("note")){
            newEvent.setNote(newValue);
        }
        else {
            throw new Exception(option+" is not recognized as internal command.");
        }
    }



    private void UpdateCalendarEventSet(CalendarEvent newEvent,CalendarEvent oldEvent) throws Exception {
        boolean isCompatible=true;
        HashSet<CalendarEvent> incompatibleEvents = new HashSet<>();
        HashSet<CalendarEvent> calendarEvents= (HashSet<CalendarEvent>) myCalendar.getCalendarEvent();

        for(CalendarEvent event:calendarEvents)
        {
            if(event.equals(oldEvent))
                continue;

            if(newEvent.hasConflict(event))
            {
                isCompatible=false;
                incompatibleEvents.add(event);
            }
        }

        if(isCompatible) {
            myCalendar.removeCalendarEvent(oldEvent);
            myCalendar.addCalendarEvent(newEvent);
        }
        else {
            StringBuilder descriptionBuilder=new StringBuilder();
            for(CalendarEvent event:incompatibleEvents){
                descriptionBuilder.append(event);
                descriptionBuilder.append("\n");
            }

            throw new Exception("The event you have typed is currently incompatible with event\\s\n:"+descriptionBuilder.toString());
        }
    }
}
