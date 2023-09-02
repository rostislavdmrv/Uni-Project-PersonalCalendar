package bg.tu_varna.sit.rostislav.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BulgarianHolidays {
    private List<LocalDate> holidays;

    public BulgarianHolidays() {
        this.holidays = new ArrayList<>();
    }

    public List<LocalDate> getHolidays() {
        return holidays;
    }
    public boolean addHoliday(LocalDate localDate){
        return holidays.add(localDate);
    }
    public boolean containHoliday(LocalDate localDate){
        return holidays.contains(localDate);

    }
}
