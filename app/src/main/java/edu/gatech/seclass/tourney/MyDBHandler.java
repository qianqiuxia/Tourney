package edu.gatech.seclass.tourney;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "players.db";
    public static final String TABLE_PLAYERS = "players";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_USERNAME = "_username";
    public static final String COLUMN_PHONENUMBER = "_phonenumber";
    public static final String COLUMN_DECKCHOICE = "_deckchoice";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PLAYERS + "{"
                + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT "
                + COLUMN_NAME + " TEXT "
                + COLUMN_USERNAME + " TEXT "
                + COLUMN_PHONENUMBER + " TEXT "
                + COLUMN_DECKCHOICE + " TEXT "
                + "};";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }
        //Add a new player to the database
        public void addPlayer(Players player){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, player.get_name());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PLAYERS, null, values); //insert a new row
        db.close();}

        //Delete a player form the database
        public void deletePlayer(String username){
                SQLiteDatabase db = getWritableDatabase();
                db.execSQL("DELETE FROM "+ TABLE_PLAYERS + "WHERE" + COLUMN_NAME + "=\""  + username + "\";" );
    }

            //Print out the database as a string
            public String databaseToString() {
                String dbString = "";
                SQLiteDatabase db = getWritableDatabase();
                String query = "SELECT * FROM " + TABLE_PLAYERS + "WHERE 1";


            //Cursor point to a location in results
                Cursor c = db.rawQuery(query, null);
                //Move to the first row to your results
                c.moveToFirst();

                while(!c.isAfterLast()){
                if (c.getString(c.getColumnIndex("username"))!=null){
               dbString += c.getString(c.getColumnIndex("username"));
                    dbString += "\n";
                }
                }
                db.close();
                return dbString;

            }
}


