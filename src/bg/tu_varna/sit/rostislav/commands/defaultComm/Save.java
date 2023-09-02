package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.MyCalendar;
import bg.tu_varna.sit.rostislav.parsers.JAXBParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public class Save implements Command {
    @Override
    public void execute(List<String> arguments) throws JAXBException {

        if (arguments.size() == 0) {
            if (Open.lastOpenedFileName != null) {
                new JAXBParser().writeToFile(MyCalendar.getInstance(), new File(String.valueOf(Open.lastOpenedFileName)));
            } else {
                System.out.println("Error closing file.");
            }
        }
        System.out.println("Successfully saved " + Open.lastOpenedFileName );

    }

}
