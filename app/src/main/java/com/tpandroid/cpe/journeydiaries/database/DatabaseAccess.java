package com.tpandroid.cpe.journeydiaries.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceEntity;
import com.tpandroid.cpe.journeydiaries.Journey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        db.execSQL("Create table Journey(id integer primary key, name String, departureDate String, returnDate String, placeId String, );");
        ContentValues content = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        content.put("name", "Default");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJl4foalHq9EcR8CG75CqrCAQ");
        db.insert("Journey", null, content);

        content.put("name", "City trip in Paris");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJD7fiBh9u5kcRYJSMaMOCCwQ");
        db.insert("Journey", null, content);

        content.put("name", "Chicago");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJ7cv00DwsDogRAMDACa2m4K8");
        db.insert("Journey", null, content);

        content.put("name", "Genève");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJ6-LQkwZljEcRObwLezWVtqA");
        db.insert("Journey", null, content);

        content.put("name", "Colombo");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJA3B6D9FT4joRjYPTMk0uCzI");
        db.insert("Journey", null, content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    void createJourney(String name, String departureDate, String returnDate, String placeId) {

        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("departureDate", departureDate);
        content.put("returnDate", returnDate);
        content.put("placeId", placeId);
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

    void deleteJourney(Integer id) {
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

    void updateJourney(Integer id, String name, String departureDate, String returnDate, String placeId) {
        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("departureDate", departureDate);
        content.put("returnDate", returnDate);
        content.put("placeId", placeId);
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

//    Journey getJourney(String name, String departureDate, String returnDate){
//        String[] columns = new String[]{"id", "name", "departureDate", "returnDate"};
//        String whereClause = "id = ? and name = ?";
//        long id=0;
//        String[] whereArgs = {String.valueOf(id)};
//        //Cursor cursor = db.query(
//              //  "Journey", null, whereClause, whereArgs, groupBy, having, orderBy);
//        Journey j = new Journey();
//        return j;
//    }

    public ArrayList<Journey> getAllJourneys(){
        ArrayList<Journey> journeys = new ArrayList<>();
        Cursor cursor = db.query(
                "Journey", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                Journey j = new Journey();
                j.setName(cursor.getString(cursor.getColumnIndex("name")));
                j.setId(cursor.getInt(cursor.getColumnIndex("id")));
                j.setFrom(cursor.getString(cursor.getColumnIndex("departureDate")));
                j.setTo(cursor.getString(cursor.getColumnIndex("returnDate")));
                j.setPlaceId(cursor.getString(cursor.getColumnIndex("placeId")));
                journeys.add(j);
            }while(cursor.moveToNext());
        }
        return journeys;
    }
}
