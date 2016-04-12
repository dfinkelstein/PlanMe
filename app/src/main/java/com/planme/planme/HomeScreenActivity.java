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
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeScreenActivity extends AppCompatActivity {

    private TasksAdapter tasksAdapter;
    private MyDBHandler dbHandler;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new MyDBHandler(this, null, null, 1);

        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2);
        calendarView.setMinDate(calendar.getTimeInMillis());
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 2);
        calendarView.setMaxDate(calendar.getTimeInMillis());

        cal = Calendar.getInstance();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                cal.set(year, month, dayOfMonth);
                updateTasks(cal.getTime());
            }
        });


        ArrayList<TasksDB> arrayOfTasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(this, arrayOfTasks);
        final ListView taskListView = (ListView) findViewById(R.id.listViewTasks);
        taskListView.setAdapter(tasksAdapter);
        taskListView.setEmptyView(findViewById(R.id.empty));
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TasksDB task = (TasksDB) taskListView.getItemAtPosition(position);
                modifyTask(task);
            }
        });

        updateTasks(new Date(calendarView.getDate()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    private void updateTasks(Date date) {

        tasksAdapter.clear();

        SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd", Locale.US);

        List<TasksDB> tasks = dbHandler.viewDailyTasks(fmt.format(date));

        tasksAdapter.addAll(tasks);
    }

    private void addTask() {

        startActivityForResult(new Intent(this, AddTaskActivity.class), 1);
    }

    private void modifyTask(TasksDB task) {

        Intent modify = new Intent(this, AddTaskActivity.class);
        modify.putExtra("TASK_ID", task.get_id());
        startActivityForResult(modify, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("Save", false)) {
                    Snackbar.make(findViewById(R.id.fab), "Task Saved!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    updateTasks(cal.getTime());
                } else {
                    Snackbar.make(findViewById(R.id.fab), "Task Deleted!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    updateTasks(cal.getTime());
                }
            }
        }
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
