package bg.tu_varna.sit.rostislav.cli;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.factory.CalendarFactory;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.JAXBParser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cli {
    private CalendarsDatabase calendarsDatabase;
    private CalendarFactory calendarFactory;
    private MyCalendar myCalendar;
    private JAXBParser jaxbParser;

    public Cli() {
        myCalendar = new MyCalendar();
        jaxbParser = new JAXBParser();
        calendarsDatabase = new CalendarsDatabase(myCalendar,jaxbParser);
        calendarFactory = new CalendarFactory(calendarsDatabase);
    }

    public void run() {
        System.out.println("<---\t\t MY PERSONAL CALENDAR \t\t--->\n");

        while (true) {
            System.out.print(">");
            String input = new Scanner(System.in).nextLine();
            ArrayList<String> parts = new ArrayList<>(Arrays.asList(input.split("\\s+")));
            String stringCommand = parts.get(0);
            List<String> arguments = parts.subList(1, parts.size());

            try {
                Command command = calendarFactory.startOperation(stringCommand, arguments);
                if (command != null) {
                    command.execute(arguments);
                } else {
                    System.out.println("Unknown command: " + stringCommand);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    /*private void executeCommand(String stringCommand, List<String> arguments) throws Exception {
        Command command = null;

        switch (stringCommand.toLowerCase()) {
            case "exit":
                command = new Exit();
                break;
            case "help":
                command = new Help();
                break;
            case "close":
                command = new Close(calendarService);
                break;
            case "save":
                command = new Save(calendarService);
                break;
            case "saveas":
                command = new SaveAs(calendarService, arguments);
                break;
            case "open":
                if (checkIfFileIsLoaded()) {
                    throw new Exception("There is currently opened file:" + calendarService.getLoadedFile().getAbsolutePath());
                }
                command = new Open(calendarService, arguments);
                break;
            case "book":
                command = new Book(calendarService.getRepository(), arguments);
                break;
            case "unbook":
                command = new Unbook(calendarService.getRepository(), arguments);
                break;
            case "agenda":
                command = new Agenda(calendarService.getRepository(), arguments);
                break;
            case "change":
                command = new Change(calendarService.getRepository(), arguments);
                break;
            case "find":
                command = new Find(calendarService.getRepository(), arguments);
                break;
            case "holiday":
                command = new Holiday(calendarService.getRepository(), arguments);
                break;
            case "busydays":
                command = new Busydays(calendarService.getRepository(), arguments);
                break;
            case "findslot":
                command = new FindSlot(calendarService.getRepository(), arguments);
                break;
            case "findslotwith":
                command = new FindSlotWith(calendarService, arguments);
                break;
            case "merge":
                command = new Merge(calendarService, arguments);
                break;
            default:
                throw new Exception("Command not recognized!");
        }

        command.execute(arguments);
    }

    private boolean checkIfFileIsLoaded() {
        return calendarService.getLoadedFile() != null;
    }*/
}
