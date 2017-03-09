package edu.gatech.seclass.tourneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edchen on 3/2/17.
 * based on:
 * https://developer.android.com/training/basics/data-storage/databases.html
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * https://www.tutorialspoint.com/android/android_sqlite_database.htm
 */

public class DBHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "TourneyManager.db";

    private static final String TABLE_PLAYER = "players";
    private static final String PLAYER_USERNAME = "username";
    private static final String PLAYER_NAME = "name";
    private static final String PLAYER_PHONE_NUMBER = "phone_number";
    private static final String PLAYER_DECK_CHOICE = "deck_choice";

    private static final String TABLE_PRIZES = "prize_history";
    private static final String PRIZES_USERNAME = "username";
    private static final String PRIZES_AMOUNT = "amount";
    private static final String PRIZES_DATE = "date";
    private static final String PRIZES_PLACE = "place";

    private static final String TABLE_PROFITS = "profit_history";
    private static final String PROFITS_DATE = "date";
    private static final String PROFITS_AMOUNT = "profit";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPlayerTable = "CREATE TABLE " + TABLE_PLAYER + "(" +
                PLAYER_USERNAME + " TEXT NOT NULL UNIQUE, " +
                PLAYER_NAME + " TEXT, " +
                PLAYER_PHONE_NUMBER + " TEXT, " +
                PLAYER_DECK_CHOICE + " TEXT" + ")";
        db.execSQL(createPlayerTable);

        String createPrizesTable = "CREATE TABLE " + TABLE_PRIZES + "(" +
                PRIZES_USERNAME + " TEXT, " +
                PRIZES_AMOUNT + " INTEGER, " +
                PRIZES_DATE + " TEXT, " +
                PRIZES_PLACE + " INTEGER" + ")";
        db.execSQL(createPrizesTable);

        String createProfitsTable = "CREATE TABLE " + TABLE_PROFITS + "(" +
                PROFITS_DATE + " TEXT, " +
                PROFITS_AMOUNT + " INTEGER" + ")";
        db.execSQL(createProfitsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO handle schema changes better, for now, erase everything if schema changes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIZES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFITS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addPlayer(Player p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_USERNAME, p.getUsername());
        values.put(PLAYER_NAME, p.getName());
        values.put(PLAYER_PHONE_NUMBER, p.getPhoneNumber());
        values.put(PLAYER_DECK_CHOICE, p.getDeckChoice().name());
        db.insert(TABLE_PLAYER, null, values);
        db.close();
    }

    public void deletePlayer(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = { username };
        db.delete(TABLE_PRIZES, PRIZES_USERNAME + " = ?", args);
        db.delete(TABLE_PLAYER, PLAYER_USERNAME + " = ?", args);
        db.close();
    }

    public ArrayList<String> getAllPlayerUsernames(){
        ArrayList<String> playerList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT " + PLAYER_USERNAME + " FROM " + TABLE_PLAYER, null);
        result.moveToFirst();
        while(result.isAfterLast() == false){
            playerList.add(result.getString(result.getColumnIndex(PLAYER_USERNAME)));
            result.moveToNext();
        }
        return playerList;
    }

    public void recordPrizes(String date,
                             String firstPlacePlayer, int firstPlaceAmount,
                             String secondPlacePlayer, int secondPlaceAmount,
                             String thirdPlacePlayer, int thirdPlaceAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIZES_USERNAME, firstPlacePlayer);
        values.put(PRIZES_AMOUNT, firstPlaceAmount);
        values.put(PRIZES_DATE, date);
        values.put(PRIZES_PLACE, 1);
        db.insert(TABLE_PRIZES, null, values);
        values.clear();
        values.put(PRIZES_USERNAME, secondPlacePlayer);
        values.put(PRIZES_AMOUNT, secondPlaceAmount);
        values.put(PRIZES_DATE, date);
        values.put(PRIZES_PLACE, 2);
        db.insert(TABLE_PRIZES, null, values);
        values.clear();
        values.put(PRIZES_USERNAME, thirdPlacePlayer);
        values.put(PRIZES_AMOUNT, thirdPlaceAmount);
        values.put(PRIZES_DATE, date);
        values.put(PRIZES_PLACE, 3);
        db.insert(TABLE_PRIZES, null, values);
        db.close();

    }
    public ArrayList<PrizeRecord> getPrizes(){
        ArrayList<PrizeRecord> prizes = new ArrayList<PrizeRecord>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(
                "SELECT " + PRIZES_USERNAME + ", SUM(" + PRIZES_AMOUNT + ") AS PLAYER_TOTAL FROM "
                        + TABLE_PRIZES + " GROUP BY " + PRIZES_USERNAME, null);
        result.moveToFirst();
        while(result.isAfterLast() == false){
            PrizeRecord pr = new PrizeRecord(
                    result.getString(result.getColumnIndex(PRIZES_USERNAME)),
                    result.getInt(result.getColumnIndex("PLAYER_TOTAL")));
            prizes.add(pr);
            result.moveToNext();
        }
        db.close();
        return prizes;
    }
    public ArrayList<PrizeRecord> getPrizes(String p){
        ArrayList<PrizeRecord> prizes = new ArrayList<PrizeRecord>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery(
                "SELECT * FROM " + TABLE_PRIZES +
                " WHERE " + PRIZES_USERNAME + " = '" + p + "'", null);
        result.moveToFirst();
        while(result.isAfterLast() == false){
            PrizeRecord pr = new PrizeRecord(
                    result.getString(result.getColumnIndex(PRIZES_USERNAME)),
                    result.getInt(result.getColumnIndex(PRIZES_AMOUNT)),
                    result.getString(result.getColumnIndex(PRIZES_DATE)));
            prizes.add(pr);
            result.moveToNext();
        }
        db.close();
        return prizes;
    }

    public void recordProfit(String date, int profitAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROFITS_DATE, date);
        values.put(PROFITS_AMOUNT, profitAmount);
        db.insert(TABLE_PROFITS, null, values);
        db.close();
    }
    public ArrayList<ProfitRecord> getProfits(){
        ArrayList<ProfitRecord> profits = new ArrayList<ProfitRecord>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_PROFITS +
                " ORDER BY " + PROFITS_DATE, null);
        result.moveToFirst();
        while(result.isAfterLast() == false){
            ProfitRecord pr = new ProfitRecord();
            pr.setDate(result.getString(result.getColumnIndex(PROFITS_DATE)));
            pr.setProfit(Integer.valueOf(result.getString(result.getColumnIndex(PROFITS_AMOUNT))));
            profits.add(pr);
            result.moveToNext();
        }
        db.close();
        return profits;
    }
}
