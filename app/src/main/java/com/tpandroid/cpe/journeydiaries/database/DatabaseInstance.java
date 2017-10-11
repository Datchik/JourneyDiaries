package com.tpandroid.cpe.journeydiaries.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tpandroid.cpe.journeydiaries.Journey;

import java.util.ArrayList;

/**
 * Created by Camille on 10/10/2017.
 */

public class DatabaseInstance {

    private static DatabaseInstance  database = null;
    private DatabaseAccess dbAccess;

    public static DatabaseInstance  getInstance(Context context){
        if (database == null){
            database = new DatabaseInstance(context);
        }
        return database;
    }

    public DatabaseInstance(Context context) {
        dbAccess = DatabaseAccess.getInstance(context);
    }

    public void createJourney(String name, String departureDate, String returnDate){
        dbAccess.createJourney(name, departureDate, returnDate);
    }

    public void updateJourney(Integer id, String name, String departureDate, String returnDate){
        dbAccess.updateJourney(id, name, departureDate, returnDate);
    }

    public void deleteJourney(Integer id){
        dbAccess.deleteJourney(id);
    }

    public ArrayList<Journey> getAllJourneys(){
        return dbAccess.getAllJourneys();
    }

}
