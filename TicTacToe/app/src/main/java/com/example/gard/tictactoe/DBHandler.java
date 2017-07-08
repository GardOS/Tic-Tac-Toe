package com.example.gard.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Gard on 31.03.2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "player.db";
    public static final String TABLE_PLAYER = "player";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_SCORE = "score";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY =
                "CREATE TABLE " + TABLE_PLAYER + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_USERNAME + " TEXT, "
                        + COLUMN_SCORE + " INTEGER);";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        onCreate(db);
    }

    public Player addAndUpdatePlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, player.getUsername());
        values.put(COLUMN_SCORE, player.getScore());

        if(player.getId() == 0){
            player.setId((int)db.insert(TABLE_PLAYER, null, values)); //Inserts the player to the DB and returns it with the incremented ID
            if (getHighscoreCount() > 10){
                removePlayerById(getPlayerWithWorstScore().getId()); // Remove 11th place
            }
        } else {
            db.update(TABLE_PLAYER, values, COLUMN_ID + " = ?", new String[]{String.valueOf(player.getId())});
        }
        db.close();
        return player;
    }

    public boolean checkIfTopTen(Player player){
        if (getHighscoreCount() < 10){
            return true;
        }
        return player.getScore() > getPlayerWithWorstScore().getScore();
    }

    private Player getPlayerWithWorstScore(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLAYER + " ORDER BY " + COLUMN_SCORE + " DESC;", null);
        cursor.moveToLast();
        Player player = new Player();
        player.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        player.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
        player.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));
        return player;
    }

    private int getHighscoreCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db, TABLE_PLAYER);
    }

    private void removePlayerById(int playerId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, COLUMN_ID + " = ?", new String[]{String.valueOf(playerId)});
        db.close();
    }

    public List<Player> getTopTen(){
        List<Player> personList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PLAYER + " ORDER BY " + COLUMN_SCORE + " DESC;", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Player player = new Player();
            player.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            player.setScore(cursor.getInt(cursor.getColumnIndex("score")));
            personList.add(player);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return personList;
    }

    public void clearDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_PLAYER);
        db.execSQL("delete from sqlite_sequence where name ='" + TABLE_PLAYER + "';"); //Reset increment value
        db.close();
    }
}
