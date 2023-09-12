package bg.tu_varna.sit.rostislav.factory;

import bg.tu_varna.sit.rostislav.commands.calendarComm.*;
import bg.tu_varna.sit.rostislav.commands.defaultComm.*;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.exception.EventException;
import bg.tu_varna.sit.rostislav.exception.ExceptionMessages;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;

import java.util.List;

public class CalendarFactory {
    private CalendarsDatabase calendarsDatabase;

    public CalendarFactory(CalendarsDatabase calendarsDatabase) {
        this.calendarsDatabase = calendarsDatabase;
    }

    public Command startOperation(String stringCommand, List<String> arguments) throws Exception {

        if(calendarsDatabase.getLoadedFile() != null) {
            switch (stringCommand.toLowerCase()) {
                case "open": throw new EventException(ExceptionMessages.OPENED_FILE + calendarsDatabase.getLoadedFile().getAbsolutePath());
                case "close": return new Close(calendarsDatabase);
                case "save":return new Save(calendarsDatabase);
                case "help": return new Help();
                case "exit": return new Exit();
                case "saveas": return new SaveAs(calendarsDatabase, arguments);
                case "book": return new Book(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "unbook": return new Unbook(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "agenda": return new Agenda(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "change": return new Change(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "find": return new Find(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "holiday": return new Holiday(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "busydays": return new BusyDays(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "findslot": return new FindSlot(calendarsDatabase.getMyCalendarRepository(), arguments);
                case "findslotwith": return new FindSlotWith(calendarsDatabase, arguments);
                case "merge": return new Merge(calendarsDatabase, arguments);
                default: throw new EventException(ExceptionMessages.CURRENT_OPERATION);

            }
        }
        else {
            switch (stringCommand.toLowerCase()) {
                case "exit": return new Exit();
                case "help": return new Help();
                case "open": return new Open(calendarsDatabase, arguments);
                default: throw new EventException(ExceptionMessages.NO_OPENED_FILE);
            }

        }
    }


    /**
     * Checks whether a file is loaded or not.
     * @return true if a file is loaded, false otherwise.
     */

}
