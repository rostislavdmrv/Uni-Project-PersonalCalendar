package bg.tu_varna.sit.rostislav.commands.defaultComm;

import bg.tu_varna.sit.rostislav.common.ConstantMessages;
import bg.tu_varna.sit.rostislav.contracts.Command;
import bg.tu_varna.sit.rostislav.models.CalendarsDatabase;

import java.io.File;
import java.util.List;

public class SaveAs implements Command {
    private  final CalendarsDatabase calendarsDatabase;

    private String newFile;

    public SaveAs(CalendarsDatabase calendarsDatabase, List<String> instructions) {
        this.calendarsDatabase = calendarsDatabase;
        this.newFile=instructions.get(0);
    }


    @Override
    public void execute(List<String> arguments) throws Exception {

        if (calendarsDatabase.getLoadedFile().equals(newFile)){
            System.out.println(ConstantMessages.USE_SAVE);

        }else {

            calendarsDatabase.setLoadedFile(new File(newFile));

            calendarsDatabase.exportFromMyCalendarRepository();

            System.out.println(ConstantMessages.SUCCESS_SAVEAS + calendarsDatabase.getLoadedFile().getAbsolutePath());
        }

    }

}


