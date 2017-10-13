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
        db.execSQL("Create table Journey(id integer primary key, name String, departureDate String, returnDate String, placeId String, note String);");
        ContentValues content = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

        content.put("name", "City trip in Paris");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJD7fiBh9u5kcRYJSMaMOCCwQ");
        content.put("note", "J’en rêve depuis que je suis toute petite. Pas seulement de la tour Eiffel, mais de la vie parisienne. Si vous saviez comment je préfère un bon croissant frais accompagné d’un latte comme petit-déj’, plutôt que quelques tranches de bacon. D’ailleurs, je crois définitivement être née dans la mauvaise ville : Paris m’est destiné ou plutôt, je suis destinée à Paris. Pour sa cuisine, son amour pour les chiens, son architecture, ses Parisiens et Parisiennes toujours impeccablement vêtus et ses confiseries qu’il faut goûter au moins une fois dans une vie. Et puis là, je termine mon baccalauréat le 26 avril, officiellement, la vie d’étudiante sera derrière moi. Ce n’est quand même pas rien. On s’envolera donc, ma mère et moi, pour Paris. Un autre check sur ma bucket list! Une semaine dans la Ville lumière pour la première fois");
        db.insert("Journey", null, content);

        content.put("name", "Chicago");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJ7cv00DwsDogRAMDACa2m4K8");
        content.put("note", "Chicago est véritablement l’une de mes villes préférées, je suis amoureuse de son architecture, de sa cuisine, de ses canaux, de son Navy Pier et de tant de choses que je vais essayer de vous transmettre.");
        db.insert("Journey", null, content);

        content.put("name", "Genève");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJ6-LQkwZljEcRObwLezWVtqA");
        content.put("note", "Comme vous l'avez peut être déjà vu sur mon Instagram, il y a quelques temps, j'ai passé un week end entre filles à Genève, avec Azzed, Violette, Elodie, Cyrielle et Fanny. Nous avons eu la chance d'être invitées par l'office de tourisme Genevois pour découvrir la ville et tout ce qu'elle a à offrir, en suivant le guide Geneva Girl's Guide, qui recense les bonnes adresses shopping, restau et bien être de Genève (et pas forcément les plus connues, donc il y a moyen de découvrir des perles !).");
        db.insert("Journey", null, content);

        content.put("name", "Colombo");
        content.put("departureDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("returnDate", sdf.format(Calendar.getInstance().getTime()));
        content.put("placeId", "ChIJA3B6D9FT4joRjYPTMk0uCzI");
        content.put("note", "Le Sri Lanka est un de mes gros coups de cœur. Je suis tombé sous le charme de cette destination en 2012 lors de mon premier voyage sur l’île. J’avais adoré les visites et la variété des paysages et peuples. Par manque de temps, il m’était impossible de rejoindre le nord.");
        db.insert("Journey", null, content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    void createJourney(String name, String departureDate, String returnDate, String placeId, String note) {

        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("departureDate", departureDate);
        content.put("returnDate", returnDate);
        content.put("placeId", placeId);
        content.put("note", note);
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

    void updateJourney(Integer id, String name, String departureDate, String returnDate, String placeId, String note) {
        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("departureDate", departureDate);
        content.put("returnDate", returnDate);
        content.put("placeId", placeId);
        content.put("note", note);
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
                j.setNote(cursor.getString(cursor.getColumnIndex("note")));
                journeys.add(j);
            }while(cursor.moveToNext());
        }
        return journeys;
    }
}
