package com.planme.planme;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds user info and task list
 *
 * Created by Danny Finkelstein on 4/5/16.
 */
public class User {

    private String name;
    private List<Task> taskList;

    public User(String name) {
        this.name = name;
        taskList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        // TODO Implement this
    }

    public void removeTask(Task task) {
        // TODO Implement this
    }

    protected void saveTaskList() {
        // TODO Implement this
    }

}
