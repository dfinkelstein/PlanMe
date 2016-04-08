package com.planme.planme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity {

    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2);
        calendarView.setMinDate(calendar.getTimeInMillis());
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 2);
        calendarView.setMaxDate(calendar.getTimeInMillis());

        ArrayList<Task> arrayOfTasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(this, arrayOfTasks);
        ListView taskListView = (ListView) findViewById(R.id.listViewTasks);
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
    }

    private void addTask() {
        // TODO Should eventually start new add task activity
//        Task newTask = new Task("Test Task", new Date(), "This is a test task", new Location("Test Location"));
        Task newTask = new Task("Test Task", new Date(), "This is a test task", null);
//        newTask.setStatus(Task.completionStatus.Completed);
        tasksAdapter.add(newTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.action_listview:
                startActivity(new Intent(this, TaskListActivity.class));
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }
}
