package bg.tu_varna.sit.rostislav.models;

import bg.tu_varna.sit.rostislav.parsers.JAXBParser;

import java.io.File;

public class CalendarsDatabase {
    private File loadedFile;
    private MyCalendar myCalendarRepository;
    private JAXBParser parser;

    public CalendarsDatabase(MyCalendar myCalendarRepository, JAXBParser parser) {
        this.myCalendarRepository = myCalendarRepository;
        this.parser = parser;
    }
    public void importToMyCalendarRepository()  throws Exception{
        myCalendarRepository = parser.readFromFile(loadedFile);
    }


    public void exportFromMyCalendarRepository() throws Exception {
        parser.writeToFile(myCalendarRepository,loadedFile);
    }


    public void createFile() throws Exception {
        parser.writeToFile(myCalendarRepository,loadedFile);
    }

    //region Setters and Getters

    /**
     * Gets the currently loaded file.
     * @return The loaded file.
     */
    public File getLoadedFile() {
        return loadedFile;
    }

    /**
     * Sets the loaded file.
     * @param loadedFile The file to load.
     */
    public void setLoadedFile(File loadedFile) {
        this.loadedFile = loadedFile;
    }

    /**
     * Gets the myCalendarRepository.
     * @return The myCalendarRepository.
     */
    public MyCalendar getMyCalendarRepository() {
        return myCalendarRepository;
    }

    /**
     * Sets the myCalendarRepository.
     * @param myCalendarRepository The myCalendarRepository to use.
     */
    public void setMyCalendarRepository(MyCalendar myCalendarRepository) {
        this.myCalendarRepository = myCalendarRepository;
    }
    public JAXBParser getParser() {
        return parser;
    }

}
