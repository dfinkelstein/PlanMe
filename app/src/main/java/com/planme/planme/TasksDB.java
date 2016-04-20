package com.planme.planme;

import android.location.Location;
import java.sql.Time;
import java.util.Date;
/**
 * Created by Ehsan on 4/6/16.
 */
public class TasksDB {
    private long _id;
    private String name;
    private String description;
    private Location location;
    private Date startDate;
    private Date endDate;
    private Time timestmp;
    private completionStatus status;

    protected enum completionStatus {
        New, Started, Canceled, Completed
    }

    public TasksDB(){

    }
    public TasksDB(String name) {
        this.name = name;
    }

    public TasksDB(Location location) {
        this.location = location;
    }

    public TasksDB(Date startDate) {
        this.startDate = startDate;
    }


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public completionStatus getStatus() {
        return status;
    }

    public void setStatus(completionStatus status) {
        this.status = status;
    }

    public Time getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Time timestmp) {
        this.timestmp = timestmp;
    }
}
