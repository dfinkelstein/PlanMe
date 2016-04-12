package com.planme.planme;

/**
 * Created by Ehsan on 4/6/16.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks.db";
    public static final String TABLE_TASK = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_SDATE = "startDate";
    public static final String COLUMN_EDATE = "endDate";
    public static final String COLUMN_STATUS= "status";
    public static final String COLUMN_TIMESTAMP = "timestmp";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_TASK + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                COLUMN_NAME + " TEXT , " +
                COLUMN_DESCRIPTION + " TEXT , " +
                COLUMN_LOCATION + " TEXT , " +
                COLUMN_SDATE + " TEXT , " +
                COLUMN_EDATE + " TEXT , " +
                COLUMN_STATUS + " TEXT , " +
                COLUMN_TIMESTAMP + " TEXT " +
                ");";
        db.execSQL(query);
    }

    public void deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    //Add a new row to the database
    public void addTask(TasksDB task){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_LOCATION, task.getLocation().getProvider());
        values.put(COLUMN_SDATE, task.getStartDate().toString());
        values.put(COLUMN_EDATE, task.getEndDate().toString());
//        values.put(COLUMN_TIMESTAMP, task.getTimestmp().toString());
        values.put(COLUMN_STATUS, task.getStatus().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASK, null, values);

        db.close();
    }

    public void deleteTask(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE " + COLUMN_ID + "= " + id + ";");
        db.close();
    }

    public List<TasksDB> viewDailyTasks(String date){
        String name = "";
        String sDate = "";
        String eDate = "";
        String description = "";
        String location = "";

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_SDATE + " LIKE '%" + date + "%'";

        Cursor c = db.rawQuery(query, null);

        List<TasksDB> taskList = new ArrayList<>();

        while (c.moveToNext()){
            TasksDB task = new TasksDB();
            if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null){
                task.set_id(Long.parseLong(c.getString(c.getColumnIndex(COLUMN_ID))));
                name = c.getString(c.getColumnIndex(COLUMN_NAME));
                task.setName(name);
                sDate = c.getString(c.getColumnIndex(COLUMN_SDATE));
                task.setStartDate(new Date(sDate));
                eDate = c.getString(c.getColumnIndex(COLUMN_EDATE));
                task.setEndDate(new Date(eDate));
                description = c.getString(c.getColumnIndex(COLUMN_DESCRIPTION));
                task.setDescription(description);
                location = c.getString(c.getColumnIndex(COLUMN_LOCATION));
                task.setLocation(new Location(location));
            }
            taskList.add(task);
        }
        db.close();
        c.close();
        return taskList;
    }

    public List<TasksDB> viewAllTasks(){

        String name = "";
        String sDate = "";
        String eDate = "";
        String description = "";
        String location = "";

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK ;

        Cursor c = db.rawQuery(query, null);

        List<TasksDB> taskList = new ArrayList<>();

        while (c.moveToNext()) {
            TasksDB task = new TasksDB();
            if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null){
                task.set_id(Long.parseLong(c.getString(c.getColumnIndex(COLUMN_ID))));
                name = c.getString(c.getColumnIndex(COLUMN_NAME));
                task.setName(name);
                sDate = c.getString(c.getColumnIndex(COLUMN_SDATE));
                task.setStartDate(new Date(sDate));
                eDate = c.getString(c.getColumnIndex(COLUMN_EDATE));
                task.setEndDate(new Date(eDate));
                description = c.getString(c.getColumnIndex(COLUMN_DESCRIPTION));
                task.setDescription(description);
                location = c.getString(c.getColumnIndex(COLUMN_LOCATION));
                task.setLocation(new Location(location));
            }
            taskList.add(task);
        }
        db.close();
        c.close();
        return taskList;
    }

    public TasksDB viewSpecificTask(long id){
        String name = "";
        String sDate = "";
        String eDate = "";
        String description = "";
        String location = "";


        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_ID  + " = " + id;

        Cursor c = db.rawQuery(query, null);

        TasksDB task = new TasksDB();

        if (c.moveToFirst()) {
            if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null){
                task.set_id(Long.parseLong(c.getString(c.getColumnIndex(COLUMN_ID))));
                name = c.getString(c.getColumnIndex(COLUMN_NAME));
                task.setName(name);
                sDate = c.getString(c.getColumnIndex(COLUMN_SDATE));
                task.setStartDate(new Date(sDate));
                eDate = c.getString(c.getColumnIndex(COLUMN_EDATE));
                task.setEndDate(new Date(eDate));
                description = c.getString(c.getColumnIndex(COLUMN_DESCRIPTION));
                task.setDescription(description);
                location = c.getString(c.getColumnIndex(COLUMN_LOCATION));
                task.setLocation(new Location(location));
            }
        }

        db.close();
        c.close();

        return task;

    }
    public void modifyTask(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_ID + "= " + id + ";");
    }

}
