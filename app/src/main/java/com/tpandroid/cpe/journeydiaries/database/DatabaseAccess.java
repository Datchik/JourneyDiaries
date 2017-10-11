package com.tpandroid.cpe.journeydiaries.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

/**
 * Created by Camille on 10/10/2017.
 */

public class DatabaseAccess extends SQLiteOpenHelper {

    private static DatabaseAccess  database = null;
    private SQLiteDatabase db;

    public static DatabaseAccess  getInstance(Context context){
        if (database == null){
            database = new DatabaseAccess(context, "MyDatabase.db", null, 1);
        }
        return database;
    }

    private DatabaseAccess(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Journey(id integer primary key, name String, departureDate String, returnDate String);");
        ContentValues content = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        content.put("name", "");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        db.insert("Journey", null, content);
        content.put("name", "City trip in Paris");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        db.insert("Journey", null, content);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void createJourney(String name, String departureDate, String returnDate) {

        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("departureDate", departureDate);
        content.put("returnDate", returnDate);
        db.beginTransaction();
        try{
            db.insert("Journey", null, content);
            db.setTransactionSuccessful();
        }
        catch(Exception e){
            System.out.println("Insert fail");
        }
        finally{
            db.endTransaction();
        }
    }

    public void deleteJourney(Integer id) {
        String where = "id = " + String.valueOf(id);
        db.beginTransaction();
        try{
            db.delete("Journey", where, null);
            db.setTransactionSuccessful();
        }
        catch(Exception e){
            System.out.println("Delete fail");
        }
        finally{
            db.endTransaction();
        }
    }

    public void updateJourney(Integer id, String name, String departureDate, String returnDate) {
        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("departureDate", departureDate);
        content.put("returnDate", returnDate);
        String where = "id = " + String.valueOf(id);
        db.beginTransaction();
        try{
            db.update("Journey", content, where, null);
            db.setTransactionSuccessful();
        }
        catch(Exception e){
            System.out.println("Update fail");
        }
        finally{
            db.endTransaction();
        }
    }
}
