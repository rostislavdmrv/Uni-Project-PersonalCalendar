package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.contracts.Command;

import java.util.List;

public class Help implements Command {
    @Override
    public void execute(List<String> arguments) {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>       opens <file>");
        System.out.println("close             closes currently opened file");
        System.out.println("save              saves the currently open file");
        System.out.println("save as           saves the currently open file in <file>");
        System.out.println("help              prints this information");
        System.out.println("exit              exits the program");
        System.out.println("book              book an appointment");
        System.out.println("unbook            cancel an appointment");
        System.out.println("agenda            displays a list of all appointments for the day");
        System.out.println("change            change an appointment");
        System.out.println("find              search for an appointment");
        System.out.println("holiday           <date> is marked as inactive");
        System.out.println("busydays          display load statistics for the week");
        System.out.println("findslot          find an available an appointment");
        System.out.println("findslotwith      find an available an appointment synchronized with a given calendar");
        System.out.println("merge             transfers all events from the calendar to the current calendar.");
        System.out.println("Press Enter key to get back to main menu...");

        try {
            System.in.read();
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }

    }
}
