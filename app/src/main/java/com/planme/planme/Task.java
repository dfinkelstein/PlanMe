package com.planme.planme;

import android.location.Location;

import java.util.Date;

/**
 * Class that contains info for a Task
 *
 * Created by Danny Finkelstein on 4/5/16.
 */
public class Task {

    private String name;
    private Date dueDate;
    private String description;
    private Location location;
    private completionStatus status;

    protected enum completionStatus {
        New, Started, Canceled, Completed
    }

    public Task(String name, Date dueDate, String description, Location location) {
        this.name = name;
        this.dueDate = dueDate;
        this.description = description;
        this.location = location;
        this.status = completionStatus.New;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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

    public completionStatus getStatus() {
        return status;
    }

    public void setStatus(completionStatus status) {
        this.status = status;
    }

    public long getRemainingTime() {
        Date now = new Date();
        return dueDate.getTime() - now.getTime();
    }
}
