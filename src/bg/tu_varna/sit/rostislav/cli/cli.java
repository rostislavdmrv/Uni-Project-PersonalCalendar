package bg.tu_varna.sit.rostislav.cli;

import bg.tu_varna.sit.rostislav.commands.calendarComm.Book;
import bg.tu_varna.sit.rostislav.commands.calendarComm.Unbook;
import bg.tu_varna.sit.rostislav.commands.defaultComm.*;
import bg.tu_varna.sit.rostislav.contracts.Command;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class cli {
    public static void run(){
        while(true) {
            System.out.print(">");
            String input = new Scanner(System.in).nextLine();
            ArrayList<String> parts = new ArrayList<>(List.of(input.split(" ")));
            String stringCommand = parts.get(0);
            List<String> arguments = parts.subList(1, parts.size());

            Command command = null;

            switch (stringCommand.toLowerCase()) {
                case "open":
                    command = new Open();
                    break;
                case "close":
                    command = new Close();
                    break;
                case "exit":
                    command = new Exit();
                    break;
                case "help":
                    command = new Help();
                    break;
                case "save":
                    command = new Save();
                    break;
                case "saveas":
                    command = new SaveAs();
                    break;
                case "book":
                    command = new Book();
                    break;
                case "unbook":
                    command = new Unbook();
                    break;

            }


            command.execute(arguments);
        }

    }
}
