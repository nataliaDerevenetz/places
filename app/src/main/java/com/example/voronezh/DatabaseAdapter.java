package com.example.voronezh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yandex.mapkit.geometry.Point;

import java.io.InputStream;
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

    public List<Object> getObjectsFilter(int typeObject,String filter,boolean isAccess){
        ArrayList<Object> objects = new ArrayList<>();

        //массив параметров передающихся в запрос
        String[] strParam;
        String strQuery = "SELECT * FROM %s WHERE %s=?";
        String[] filters = null;
        String query = String.format(strQuery,DatabaseHelper.TABLE, DatabaseHelper.COLUMN_TYPE);

        filter = filter.trim();
        filter = filter.replaceAll("\\s+", " ");
        filters = filter.split(" ");

        if(!filter.isEmpty() && !filter.trim().isEmpty()) {
            if(isAccess) { strParam = new String[filters.length + 2];} else {
                strParam = new String[filters.length + 1];
            }
            strParam[0] = String.valueOf(typeObject);
            for (int i = 0; i < filters.length; i++) {
                strParam[i+1] = "%" + filters[i] + "%";
                if (i == 0) {
                    query += " AND (%s LIKE ?";
                    query = String.format(query, DatabaseHelper.COLUMN_NAME);
                } else {
                    query += " AND %s LIKE ?";
                    query = String.format(query, DatabaseHelper.COLUMN_NAME);
                }
            }
            query += ")";
        } else {
            if(isAccess) {strParam = new String[2];} else {
                strParam = new String[1];
            }
            strParam[0] = String.valueOf(typeObject);
        }

        if(isAccess) {
            query += " AND %s=?";
            query = String.format(query, DatabaseHelper.COLUMN_ENVIRON);
            strParam[strParam.length-1] = "1";
        }

        Cursor cursor = database.rawQuery(query, strParam);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            String website = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEBSITE));
            int environ = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENVIRON));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE));
            //Log.d("name",name);
            objects.add(new Object(id,name,address,description,environ,location,type,phone,email,website));
        }
        cursor.close();
        return  objects;
    }

    public List<Object> getObjects(int typeObject,boolean isAccess){
        ArrayList<Object> objects = new ArrayList<>();
        Cursor cursor;
        if(!isAccess) {
            String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_TYPE);
            cursor = database.rawQuery(query, new String[]{String.valueOf(typeObject)});
        } else{
            String query = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_TYPE, DatabaseHelper.COLUMN_ENVIRON);
            cursor = database.rawQuery(query, new String[]{String.valueOf(typeObject),"1"});
        }
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            String website = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WEBSITE));
            int environ = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENVIRON));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE));
           // Log.d("name",name);
            objects.add(new Object(id,name,address,description,environ,location,type,phone,email,website));
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
