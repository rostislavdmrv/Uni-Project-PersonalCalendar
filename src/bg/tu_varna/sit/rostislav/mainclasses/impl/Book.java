package bg.tu_varna.sit.rostislav.mainclasses.impl;

public class Book {
    private String name;
    private String note;
    private String date;
    private String startTime;
    private String endTime;

    public Book(String name, String note, String date, String startTime, String endTime) {
        this.name = name;
        this.note = note;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
