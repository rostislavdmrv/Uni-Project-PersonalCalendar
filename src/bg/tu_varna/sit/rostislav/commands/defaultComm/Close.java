package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;

import java.util.List;


public class Close implements Command {
    private final CalendarsDatabase calendarsDataBase;

    /**
     * Constructs a Close object with the provided {@link CalendarsDatabase} object.
     * @param calendarsDataBase The CalendarService object that will be used to parse the calendar file.
     */
    public Close(CalendarsDatabase calendarsDataBase) {
        this.calendarsDataBase = calendarsDataBase;
    }

    @Override
    public void execute(List<String> arguments) throws EventException {
        System.out.println(ConstantMessages.SUCCESS_CLOSE + calendarsDataBase.getLoadedFile().getAbsolutePath());
        calendarsDataBase.setLoadedFile(null);
        calendarsDataBase.setMyCalendarRepository(null);
    }
}
