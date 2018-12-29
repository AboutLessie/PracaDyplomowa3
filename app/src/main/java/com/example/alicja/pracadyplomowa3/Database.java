package com.example.alicja.pracadyplomowa3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

    /**
     * Created by Apple on 2017/3/4.
     */

    public class Database extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "alicjamus_baza1.db";
        public static final String USERS_TABLE_NAME = "Users";
        public static final String USERS_COLUMN_ID = "userId";
        public static final String USERS_COLUMN_DATE = "timeOfCreation";
        public static final String USERS_COLUMN_CARDID = "cardId";
        public static final String USERS_COLUMN_PASSWORD = "password";
        public static final String USERS_COLUMN_POINTS = "points";
        public static final String USERS_COLUMN_ACTIVE = "isActive";

        public Database(Context context) {
            super(context, DATABASE_NAME , null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE  TABLE Users " +
                    "(_userId INTEGER PRIMARY KEY  NOT NULL , " +
                    "timeOfCreation TIMESTAMP NOT NULL , " +
                    "cardId BIGINTEGER, " +
                    "password TEXT," +
                    "isActive INTEGER," +
                    "points INTEGER)"
            );
        }

        public boolean insertUsers (String timeOfCreation, String points, String cardId, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("timeOfCreation", timeOfCreation);
            contentValues.put("points", points);
            contentValues.put("cardId", cardId);
            contentValues.put("password", password);
            db.insert("Users", null, contentValues);
            return true;
        }

        public Cursor getData(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from Users where id="+id+"", null );
            return res;
        }

        public int numberOfRows(){
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, "Users");
            return numRows;
        }

        public boolean updateUsers (Integer id, String timeOfCreation, String points, String cardId, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("timeOfCreation", timeOfCreation);
            contentValues.put("points", points);
            contentValues.put("cardId", cardId);
            contentValues.put("password", password);
            db.update("Users", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
            return true;
        }

        public Integer deleteUsers (Integer id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete("Users",
                    "id = ? ",
                    new String[] { Integer.toString(id) });
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Users");
            onCreate(db);
        }

        public ArrayList<String> getAllUsers() {
            ArrayList<String> array_list = new ArrayList<String>();

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select * from Users", null );
            res.moveToFirst();

            while(res.isAfterLast() == false){
                array_list.add(res.getString(res.getColumnIndex("Users")));
                res.moveToNext();
            }
            return array_list;
        }
    }

