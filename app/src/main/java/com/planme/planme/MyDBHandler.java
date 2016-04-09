package com.planme.planme;

/**
 * Created by Ehsan on 4/6/16.
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;


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
        String query = "CREATE TABLE " + TABLE_TASK + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_NAME + " TEXT " +
                COLUMN_DESCRIPTION + " TEXT " +
                COLUMN_LOCATION + " TEXT " +
                COLUMN_SDATE + " TEXT " +
                COLUMN_EDATE + " TEXT " +
                COLUMN_STATUS + " TEXT " +
                COLUMN_TIMESTAMP + " TEXT " +
                ");";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_TASK);
        onCreate(db);
    }

    //Add a new row to the database
    public void addTask(TasksDB task){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());
        values.put(COLUMN_LOCATION, task.getLocation().toString());
        values.put(COLUMN_SDATE, task.getStartDate().toString());
        values.put(COLUMN_EDATE, task.getEndDate().toString());
        values.put(COLUMN_TIMESTAMP, task.getTimestmp().toString());
        values.put(COLUMN_STATUS, task.getStatus().toString());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_TASK, null, values);
        db.close();
    }

    public void deleteTask(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE " + COLUMN_ID + "= " + id + ";");
    }

    public String viewDailyTasks(String date){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_SDATE + "= " + date;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("name")) != null){
                dbString += c.getString(c.getColumnIndex("name"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }

    public String viewSpecificTask(String date){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_SDATE + "= " + date;

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("name")) != null){
                dbString += c.getString(c.getColumnIndex("name"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;

    }
    public void modifyTask(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_ID + "= " + id + ";");
    }

}
