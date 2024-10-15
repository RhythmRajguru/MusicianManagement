package com.rhythm.musicanmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class myDatabseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="abc.db";
    private static final int DATABASE_VERSION= (int) 1.0;
    private static final String TABLE_NAME="my_event";
    private static final String EVENT_ID="id";
    private static final String EVENT_TITLE="event_title";
    private static final String EVENT_PEOPLE="event_people";
    private static final String EVENT_DATE="event_date";
    private static final String EVENT_TIME="event_time";
    private static final String EVENT_LOCATION="event_location";
    private static final String EVENT_DESCRIPTION="event_description";

    myDatabseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENT_TITLE + " TEXT, "
                + EVENT_PEOPLE + " INTEGER, "
                + EVENT_DATE + " TEXT, "
                + EVENT_TIME + " TEXT, "
                + EVENT_LOCATION + " TEXT, "
                + EVENT_DESCRIPTION + " TEXT);";
        db.execSQL(query);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }
    void addEvent(String title,int peopleCount,String date,String time,String location,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(EVENT_TITLE,title);
        cv.put(EVENT_PEOPLE,peopleCount);
        cv.put(EVENT_DATE,date);
        cv.put(EVENT_TIME,time);
        cv.put(EVENT_LOCATION,location);
        cv.put(EVENT_DESCRIPTION,description);

       long result=db.insert(TABLE_NAME,null,cv);
       if(result==-1){
           Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
       }
       else {
           Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
       }
    }
    Cursor readallData(){
        String query="SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=null;
        if (db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
    void updateData(String row_id, String title, String people, String date, String time, String location, String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(EVENT_TITLE,title);
        cv.put(EVENT_PEOPLE,people);
        cv.put(EVENT_DATE,date);
        cv.put(EVENT_TIME,time);
        cv.put(EVENT_LOCATION,location);
        cv.put(EVENT_DESCRIPTION,description);
        long result=db.update(TABLE_NAME,cv,"id=?", new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Failed to Update..", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOnerow(String row_id){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,"id=?",new String[]{row_id});
        if(result==-1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted..", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
