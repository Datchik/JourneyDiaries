package com.tpandroid.cpe.journeydiaries.database;

import android.content.Context;

/**
 * Created by Camille on 10/10/2017.
 */

public class DatabaseInstance {

    private DatabaseAccess database;

    private DatabaseInstance(Context context) {
        database = DatabaseAccess.getInstance(context);
    }

    private void createJourney(String name, String departureDate, String returnDate){
        database.createJourney(name, departureDate, returnDate);
    }

    private void updateJourney(Integer id, String name, String departureDate, String returnDate){
        database.updateJourney(id, name, departureDate, returnDate);
    }

    private void deleteJourney(Integer id){
        database.deleteJourney(id);
    }
}
