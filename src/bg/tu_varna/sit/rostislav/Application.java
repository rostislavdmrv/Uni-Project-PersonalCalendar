package bg.tu_varna.sit.rostislav;

import bg.tu_varna.sit.rostislav.cli.Cli;

public class Application {
    public static void main(String[] args) throws Exception {
        Cli cli= new Cli();
        cli.run();
    }
}
