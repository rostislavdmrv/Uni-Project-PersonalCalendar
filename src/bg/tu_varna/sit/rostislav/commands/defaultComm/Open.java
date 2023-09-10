package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;
import bg.tu_varna.sit.rostislav.models.MyCalendar;

import java.io.File;
import java.util.List;

public class Open  implements Command {
    private  final CalendarsDatabase calendarsDatabase;
    private String fileToOpen;

    public Open(CalendarsDatabase calendarsDatabase, List<String> arguments) {
        this.calendarsDatabase = calendarsDatabase;
        this.fileToOpen = arguments.get(0);
    }

    @Override
    public void execute(List<String> arguments) throws Exception {
        calendarsDatabase.setLoadedFile(new File(fileToOpen));
        calendarsDatabase.setMyCalendarRepository(new MyCalendar());

        if(calendarsDatabase.getLoadedFile().exists()) {

            calendarsDatabase.importToMyCalendarRepository();

            System.out.println("Successfully opened: " + fileToOpen);

            if (calendarsDatabase.getMyCalendarRepository().getCalendarEvent().isEmpty()) {
                System.out.println("File is empty.");
            }

        }else {
            calendarsDatabase.createFile();
            System.out.println("New file is create and load: " + fileToOpen);
        }
    }


}