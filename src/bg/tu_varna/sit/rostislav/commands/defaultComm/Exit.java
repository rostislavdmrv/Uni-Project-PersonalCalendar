package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;

import java.util.List;

public class Exit implements Command {
    public Exit() {
    }

    @Override
    public void execute(List<String> arguments) {
        System.out.println(ConstantMessages.EXIT);
        System.exit(0);

    }
}
