package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;

import java.util.List;

public class Save implements Command {
    private final CalendarsDatabase calendarsDatabase;

    /**
     * The ArrayList containing the instructions for the operation.
     * @param calendarsDatabase The CalendarService object that will be used to parse the calendar.
     */
    public Save(CalendarsDatabase calendarsDatabase) {
        this.calendarsDatabase = calendarsDatabase;
    }


    @Override
    public void execute(List<String> arguments) throws Exception {
        calendarsDatabase.exportFromMyCalendarRepository();

        System.out.println(ConstantMessages.SUCCESS_SAVE + calendarsDatabase.getLoadedFile().getAbsolutePath());
    }
}
