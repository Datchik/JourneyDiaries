package com.tpandroid.cpe.journeydiaries;

import java.util.Calendar;

/**
 * Created by Louis-Marie on 09/10/2017.
 */

public class Journey {
    private Integer id;
    private String name;
    private String from;
    private String to;
    public Journey() {
        name ="";
        from = "01/01/70";
        to = "01/01/70";
    }
    public Journey(Integer id, String name, String from, String to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
