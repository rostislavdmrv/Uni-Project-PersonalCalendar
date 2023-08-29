package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.cli.cli;
import bg.tu_varna.sit.rostislav.contracts.Command;

import java.util.List;

import static bg.tu_varna.sit.rostislav.cli.cli.run;

public class Close implements Command {
    @Override
    public void execute(List<String> arguments) {

        System.out.println("Successfully closed " + arguments );
        run();

    }
}
