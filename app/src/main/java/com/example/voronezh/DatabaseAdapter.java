package com.example.voronezh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
        dbHelper.create_db();
    }

    public DatabaseAdapter open(){
        database = dbHelper.open();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public List<Object> getObjects(int typeObject){
        ArrayList<Object> objects = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_TYPE);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(typeObject)});
        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            Log.d("name",name);
            //int year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_));
            objects.add(new Object(id, name));

        }

        cursor.close();
        return  objects;
    }
    /*

    public User getUser(long id){
        User user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            int year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
            user = new User(id, name, year);
        }
        cursor.close();
        return  user;
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_YEAR};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            int year = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR));
            users.add(new User(id, name, year));
        }
        cursor.close();
        return  users;
    }


     */

}
