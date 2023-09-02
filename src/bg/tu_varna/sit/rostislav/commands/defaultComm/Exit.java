package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.contracts.Command;

import java.util.List;

public class Exit implements Command {
    @Override
    public void execute(List<String> arguments) {
        System.out.println("Exiting the program...");
        System.exit(0);

    }
}
