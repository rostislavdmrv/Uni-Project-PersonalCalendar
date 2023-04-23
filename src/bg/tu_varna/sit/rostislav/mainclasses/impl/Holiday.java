package bg.tu_varna.sit.rostislav.mainclasses.impl;

import java.util.List;

public class Holiday {
    public void markAsHoliday(List<String> holidays, String date) {
        holidays.add(date);
    }
}
