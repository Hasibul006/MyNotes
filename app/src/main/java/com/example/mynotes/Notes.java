package com.example.mynotes;

public class Notes {
    String title;
    String contents;
    String date;


    public Notes(String title, String contents, String date, String time) {
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.time = time;
    }

    String time;
    Notes()
    {

    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
