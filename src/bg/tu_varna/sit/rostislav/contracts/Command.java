package bg.tu_varna.sit.rostislav.contracts;

import java.util.List;

public interface Command {
     void execute(List<String> arguments);

}
