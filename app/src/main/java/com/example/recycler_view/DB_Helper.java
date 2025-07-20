package com.example.recycler_view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB_Helper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ContactsDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "contacts";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";
    private static final String COL_IMAGE = "image";

    public DB_Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_IMAGE + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertContact(String name, String phone, int image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_IMAGE, image);
        long result = db.insert(TABLE_NAME, null, values);
        if(result== -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public ArrayList<Contact_class> getAllContacts() {
        ArrayList<Contact_class> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                int image = cursor.getInt(3);
                list.add(new Contact_class(name, phone, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean updateContact(int id, String name, String phone, int image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_IMAGE, image);
        int result = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        if(result== -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        if(result== -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
