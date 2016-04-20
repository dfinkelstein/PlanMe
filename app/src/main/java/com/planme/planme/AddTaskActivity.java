package com.planme.planme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private MyEditTextDatePicker startDateText;
    private MyEditTextDatePicker endDateText;
    private MyEditTextTimePicker startTimeText;
    private MyEditTextTimePicker endTimeText;
    private EditText textName;
    private EditText textDescription;
    private EditText textLocation;

    private TasksDB _task = null;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHandler = new MyDBHandler(this, null, null, 1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id = extras.getLong("TASK_ID");
            _task = dbHandler.viewSpecificTask(id);
        }

        textName = (EditText) findViewById(R.id.textTaskName);
        textDescription = (EditText) findViewById(R.id.textDescription);
        textLocation = (EditText) findViewById(R.id.textLocation);

        startDateText = new MyEditTextDatePicker(this, R.id.textStartDate);
        endDateText = new MyEditTextDatePicker(this, R.id.textEndDate);
        startTimeText = new MyEditTextTimePicker(this, R.id.textStartTime);
        endTimeText = new MyEditTextTimePicker(this, R.id.textEndTime);

        if (_task != null) {
            populateFields();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (_task == null) {
            menu.findItem(R.id.action_delete).setEnabled(false);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    private void populateFields() {
        String dateFormat = "M/d/y"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        String timeFormat = "hh:mm a"; //In which you need put here
        SimpleDateFormat stf = new SimpleDateFormat(timeFormat, Locale.US);

        textName.setText(_task.getName());
        textDescription.setText(_task.getDescription());
        textLocation.setText(_task.getLocation().getProvider());
        startDateText._editText.setText(sdf.format(_task.getStartDate()));
        startTimeText._editText.setText(stf.format(_task.getStartDate()));
        endDateText._editText.setText(sdf.format(_task.getEndDate()));
        endTimeText._editText.setText(stf.format(_task.getEndDate()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = new Intent();

        switch (id) {
            case R.id.action_save:
                if (_task == null) {
                    addToDB();
                } else {
                    // TODO Update Task
                }
                intent.putExtra("Save", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.action_delete:
                if (_task != null) {
                    deleteFromDB();
                    intent.putExtra("Save", false);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                return true;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addToDB(){
        TasksDB task = new TasksDB();
        task.setName(textName.getText().toString());
        task.setDescription(textDescription.getText().toString());
        task.setStartDate(getDateTime("Start"));
        task.setEndDate(getDateTime("End"));
        task.setLocation(new Location(textLocation.getText().toString()));
        task.setStatus(TasksDB.completionStatus.New);
        dbHandler.addTask(task);
    }

    public void deleteFromDB() {
        dbHandler.deleteTask(_task.get_id());
    }

    public Date getDateTime(String selector) {

        if (selector.equals("Start")) {
            Date date = startDateText.getDate();
            Date time = startTimeText.getTime();
            return dateTime(date, time);
        } else if (selector.equals("End")) {
            Date date = endDateText.getDate();
            Date time = endTimeText.getTime();
            return dateTime(date, time);
        }

        return new Date();
    }

    public Date dateTime(Date date, Date time) {
        return new Date(
                date.getYear(), date.getMonth(), date.getDate(),
                time.getHours(), time.getMinutes(), time.getSeconds()
        );
    }

    public class MyEditTextDatePicker implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
        EditText _editText;
        private int _day;
        private int _month;
        private int _year;
        private Context _context;
        Calendar myCalendar = Calendar.getInstance();

        public MyEditTextDatePicker(Context context, int editTextViewID)
        {
            Activity act = (Activity)context;
            this._editText = (EditText)act.findViewById(editTextViewID);
            this._editText.setOnClickListener(this);
            this._context = context;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            _year = year;
            _month = monthOfYear;
            _day = dayOfMonth;
            updateDisplay();
        }
        @Override
        public void onClick(View v) {
            DatePickerDialog dialog =  new DatePickerDialog(_context, this, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();

        }

        // updates the date in the birth date EditText
        private void updateDisplay() {

            String myFormat = "M/d/y"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            _editText.setText(sdf.format(new GregorianCalendar(_year, _month, _day).getTime()));
        }

        public Date getDate() {
            Calendar date = Calendar.getInstance();
            date.set(_year, _month, _day);
            return date.getTime();
        }
    }

    public class MyEditTextTimePicker implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
        EditText _editText;
        Context _context;
        private int _hourOfDay;
        private int _minute;
        Calendar myCalendar = Calendar.getInstance();

        public MyEditTextTimePicker(Context context, int editTextViewID) {
            Activity act = (Activity) context;
            this._editText = (EditText) act.findViewById(editTextViewID);
            this._editText.setOnClickListener(this);
            this._context = context;
        }

        @Override
        public void onClick(View v) {
            TimePickerDialog dialog =  new TimePickerDialog(_context, this, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false);
            dialog.show();
        }

        // updates the date in the birth date EditText
        private void updateDisplay() {

            String myFormat = "hh:mm a"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            _editText.setText(sdf.format(new Time(_hourOfDay, _minute, 0)));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            _hourOfDay = hourOfDay;
            _minute = minute;
            updateDisplay();
        }

        public Date getTime() {
            Calendar time = Calendar.getInstance();
            time.set(Calendar.HOUR_OF_DAY, _hourOfDay);
            time.set(Calendar.MINUTE, _minute);
            return time.getTime();
        }
    }
}
