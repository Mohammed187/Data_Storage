package com.example.test.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseAdapter {

    static DatabaseHelper dbHelper;
    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //Add methods
    //- Write Data
    public long addEntry(PhoneMsgDTO entry) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //open database as writable

        //Content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_PHONE, entry.getPhone());
        contentValues.put(DatabaseHelper.COL_MSG, entry.getMsg());

        //Insert Table
        long id = db.insert(DatabaseHelper.TABLE_PHONE_MSG, null, contentValues);
        return id;
    }

    //- Read Data
    public PhoneMsgDTO getEntry() {
        PhoneMsgDTO entry = null;

        Cursor c;
        SQLiteDatabase db = dbHelper.getReadableDatabase(); //open database as readable

        String[] columns = {DatabaseHelper.COL_PHONE, DatabaseHelper.COL_MSG};
        c = db.query(DatabaseHelper.TABLE_PHONE_MSG, columns, null, null, null, null, null);
        while (c.moveToNext()) {
            entry = new PhoneMsgDTO(c.getString(0), c.getString(1));
        }

        return entry;
    }


    static  class DatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "PHONE_MSG.db";
        public static final String TABLE_PHONE_MSG = "PHONE_MSG";
        public static final String COL_UID = "_id";
        public static final String COL_MSG = "message";
        public static final String COL_PHONE = "phone";

        //Creation Statement
        private static final String CREATE_PHONE_MSG_TABLE = "CREATE TABLE " + TABLE_PHONE_MSG +
                " (" + COL_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_PHONE + " TEXT, " + COL_MSG + " TEXT);" ;

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_PHONE_MSG_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONE_MSG);
            onCreate(db);
        }
    }
}
