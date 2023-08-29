package bg.tu_varna.sit.rostislav.contracts;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface Command {
     void execute(List<String> arguments) throws Exception;

}
