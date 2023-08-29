package bg.tu_varna.sit.rostislav.parsers;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter  extends XmlAdapter<String, LocalTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime unmarshal(String timeString) throws Exception {
        return LocalTime.parse(timeString, formatter);
    }

    @Override
    public String marshal(LocalTime time) throws Exception {
        return time.format(formatter);
    }
}
