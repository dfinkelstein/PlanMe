package com.planme.planme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private TasksAdapter tasksAdapter;
    private MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new MyDBHandler(this, null, null, 1);

        ArrayList<TasksDB> arrayOfTasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(this, arrayOfTasks);
        final ListView taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(tasksAdapter);
        taskListView.setEmptyView(findViewById(R.id.empty));
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksDB task = (TasksDB) taskListView.getItemAtPosition(position);
                modifyTask(task);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTasks();
    }

    private void addTask() {

        startActivityForResult(new Intent(this, AddTaskActivity.class), 1);
    }

    private void modifyTask(TasksDB task) {

        Intent modify = new Intent(this, AddTaskActivity.class);
        modify.putExtra("TASK_ID", task.get_id());
        startActivityForResult(modify, 1);
    }

    public void getTasks() {
        tasksAdapter.clear();
        List<TasksDB> tasks = dbHandler.viewAllTasks();
        tasksAdapter.addAll(tasks);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("Save", false)) {
                    Snackbar.make(findViewById(R.id.fab_task), "Task Saved!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    getTasks();
                } else {
                    Snackbar.make(findViewById(R.id.fab_task), "Task Deleted!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    getTasks();
                }
            }
        }
    }

}
