package edu.gatech.seclass.tourneymanager;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dbiesan on 3/9/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DBHelperTest {
    private DBHelper helper;
    private SQLiteDatabase reader;
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

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        helper = new DBHelper(appContext);

        helper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        helper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_PRIZES);
        helper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_PROFITS);

        reader = helper.getReadableDatabase();
        helper.onCreate(reader);
    }

    @Test
    public void testOpen() throws Exception {
        SQLiteDatabase db = helper.getWritableDatabase();
        assertTrue(db.isOpen());
        db.close();
    }

    private static Player getPlayer(String username, String name, String phone, Player.DeckChoice choice) {
        Player player = new Player();
        player.setUsername(username);
        player.setName(name);
        player.setPhoneNumber(phone);
        player.setDeckChoice(choice);

        return player;
    }
    @Test
    public void addPlayer() throws Exception {
        helper.addPlayer(getPlayer("Username", "Name", "1234566", Player.DeckChoice.Buzz));

        reader = helper.getReadableDatabase();
        String[] columns = {PLAYER_USERNAME, PLAYER_NAME};
        Cursor cursor = reader.query(TABLE_PLAYER, columns, " username = \"Username\"",
                null, null, null, null);

        assertTrue(cursor != null);
        cursor.moveToFirst();
        assertEquals(1, cursor.getCount());
        assertEquals("Name", cursor.getString(1));
    }

    @Test
    public void deletePlayer() throws Exception {
        helper.addPlayer(getPlayer("Username", "Name", "1234566", Player.DeckChoice.Buzz));
        helper.deletePlayer("Username");

        reader = helper.getReadableDatabase();
        String[] columns = {PLAYER_USERNAME, PLAYER_NAME};
        Cursor cursor = reader.query(TABLE_PLAYER, columns, " username = \"Username\"",
                null, null, null, null);
        cursor.moveToFirst();

        assertEquals(0, cursor.getCount());
    }

    @Test
    public void getAllPlayerUsernames() throws Exception {
        helper.addPlayer(getPlayer("Username", "Name", "1111111", Player.DeckChoice.Buzz));
        helper.addPlayer(getPlayer("Username2", "Name2", "2222222", Player.DeckChoice.Buzz));
        helper.addPlayer(getPlayer("Username3", "Name2", "3333333", Player.DeckChoice.Buzz));

        ArrayList<String> playerList = helper.getAllPlayerUsernames();

        assertEquals(3, playerList.size());
    }

    @Test
    public void recordPrizes() throws Exception {
        Player player1 = getPlayer("Username", "Name", "1111111", Player.DeckChoice.Buzz);
        Player player2 = getPlayer("Username2", "Name2", "2222222", Player.DeckChoice.Buzz);
        Player player3 = getPlayer("Username3", "Name2", "3333333", Player.DeckChoice.Buzz);

        helper.recordPrizes("1/1/2016", player1.getUsername(), 100, player2.getUsername(), 50, player3.getUsername(), 0);

        reader = helper.getReadableDatabase();
        String[] columns = {PRIZES_USERNAME, PRIZES_AMOUNT};
        Cursor cursor = reader.query(TABLE_PRIZES, columns, null,
                null, null, null, null);
        cursor.moveToFirst();

        assertEquals(3, cursor.getCount());
        assertEquals(player1.getUsername(), cursor.getString(0));
        assertEquals("100", cursor.getString(1));

        cursor.moveToNext();
        assertEquals(player2.getUsername(), cursor.getString(0));
        assertEquals("50", cursor.getString(1));

        cursor.moveToNext();
        assertEquals(player3.getUsername(), cursor.getString(0));
        assertEquals("0", cursor.getString(1));
    }

    @Test
    public void getPrizes() throws Exception {
        Player player1 = getPlayer("Username", "Name", "1111111", Player.DeckChoice.Buzz);
        Player player2 = getPlayer("Username2", "Name2", "2222222", Player.DeckChoice.Buzz);
        Player player3 = getPlayer("Username3", "Name2", "3333333", Player.DeckChoice.Buzz);
        helper.recordPrizes("1/1/2016", player1.getUsername(), 100, player2.getUsername(), 50, player3.getUsername(), 0);

        ArrayList<PrizeRecord> prizes = helper.getPrizes();

        assertEquals(3, prizes.size());
        assertEquals("Username", prizes.get(0).getPlayer());
        assertEquals(100, prizes.get(0).getAmount());
        assertEquals("Username2", prizes.get(1).getPlayer());
        assertEquals(50, prizes.get(1).getAmount());
        assertEquals("Username3", prizes.get(2).getPlayer());
        assertEquals(0, prizes.get(2).getAmount());
    }

    @Test
    public void getPrizesGivenUsername() throws Exception {
        Player player1 = getPlayer("Username", "Name", "1111111", Player.DeckChoice.Buzz);
        Player player2 = getPlayer("Username2", "Name2", "2222222", Player.DeckChoice.Buzz);
        Player player3 = getPlayer("Username3", "Name2", "3333333", Player.DeckChoice.Buzz);
        helper.recordPrizes("1/1/2016", player1.getUsername(), 100, player2.getUsername(), 50, player3.getUsername(), 0);

        ArrayList<PrizeRecord> prizes = helper.getPrizes(player1.getUsername());

        assertEquals(1, prizes.size());
        assertEquals("Username", prizes.get(0).getPlayer());
    }

    @Test
    public void recordProfit() throws Exception {
        helper.recordProfit("12/20/2016", 100);

        reader = helper.getReadableDatabase();
        String[] columns = {PROFITS_DATE, PROFITS_AMOUNT};
        Cursor cursor = reader.query(TABLE_PROFITS, columns, null,
                null, null, null, null);
        cursor.moveToFirst();

        assertEquals("12/20/2016", cursor.getString(0));
        assertEquals("100", cursor.getString(1));
    }

    @Test
    public void getProfits() throws Exception {
        helper.recordProfit("12/20/2016", 100);

        ArrayList<ProfitRecord> profits = helper.getProfits();

        assertEquals(1, profits.size());
        assertEquals("12/20/2016", profits.get(0).getDate());
        assertEquals(100, profits.get(0).getProfit());
    }

}