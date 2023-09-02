package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.cli.cli;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.JAXBParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

import static bg.tu_varna.sit.rostislav.cli.cli.run;

public class Close implements Command {
    @Override
    public void execute(List<String> arguments) throws JAXBException {
        MyCalendar calendar = MyCalendar.getInstance();

        if (arguments.size() == 0) {
            if (Open.lastOpenedFileName != null) {
                calendar.clearEvents();
                new JAXBParser().writeToFile(MyCalendar.getInstance(), new File(String.valueOf(Open.lastOpenedFileName)));
            } else {
                System.out.println("Error closing file.");
            }
        }

        System.out.println("Successfully closed " + Open.lastOpenedFileName );
        System.out.println();
        System.out.println("To continue using the program you must open/create a file!");
        run();

    }
}
