package com.tpandroid.cpe.journeydiaries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Camille on 10/10/2017.
 */

public class DatabaseInstance extends DatabaseAccess {

    private static DatabaseInstance INSTANCE ;//= new DatabaseInstance();

    private DatabaseInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DatabaseInstance getInstance()
    {	return INSTANCE;
    }
}
