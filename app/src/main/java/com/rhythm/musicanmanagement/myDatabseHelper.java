package com.rhythm.musicanmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class myDatabseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="abc.db";
    private static final int DATABASE_VERSION= (int) 1.0;
    private static final String TABLE_NAME="my_event";
    private static final String EVENT_ID="id";
    private static final String EVENT_TITLE="event_title";
    private static final String EVENT_PEOPLE="event_title";
    private static final String EVENT_DATE="event_title";
    private static final String EVENT_TIME="event_title";
    private static final String EVENT_LOCATION="event_location";
    private static final String EVENT_DESCRIPTION="event_location";

    public myDatabseHelper(@Nullable Context context) {
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
}
