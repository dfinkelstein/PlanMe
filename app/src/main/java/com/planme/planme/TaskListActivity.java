package com.planme.planme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class TaskListActivity extends AppCompatActivity {

    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Task> arrayOfTasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(this, arrayOfTasks);
        ListView taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(tasksAdapter);
        taskListView.setEmptyView(findViewById(R.id.empty));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Task Added!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addTask();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addTask() {
        // TODO Should eventually start new add task activity
//        Task newTask = new Task("Test Task", new Date(), "This is a test task", new Location("Test Location"));
        Task newTask = new Task("Test Task", new Date(), "This is a test task", null);
//        newTask.setStatus(Task.completionStatus.Completed);
        tasksAdapter.add(newTask);
    }

}
