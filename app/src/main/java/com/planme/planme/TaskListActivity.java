package com.planme.planme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addTask() {

        startActivityForResult(new Intent(this, AddTaskActivity.class), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("Save", false)) {
                    Snackbar.make(findViewById(R.id.fab_task), "Task Saved!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(findViewById(R.id.fab_task), "Task Deleted!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }
    }

}
