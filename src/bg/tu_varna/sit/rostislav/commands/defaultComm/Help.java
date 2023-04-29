package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.contracts.Command;

import java.util.List;

public class Help implements Command {
    @Override
    public void execute(List<String> arguments) {
        System.out.println("> help");
        System.out.println("open <file>       opens <file>");
        System.out.println("close             closes currently opened file");
        System.out.println("save              saves the currently open file");
        System.out.println("save as           saves the currently open file in <file>");
        System.out.println("help              prints this information");
        System.out.println("exit              exits the program");
        System.out.println("Press Enter key to get back to main menu...");
        try {
            System.in.read();
        }
        catch(Exception e)
        {}

    }
}
