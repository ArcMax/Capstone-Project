package com.explore.archana.swimmingtechniques.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by archana on 7/11/2016.
 */
public class SwimTechDBHelper extends SQLiteOpenHelper {

    private static final String TAG = SwimTechDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "swim.db";
    private static final int DATABASE_VERSION = 12;


    public SwimTechDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_SWIMTECH_TABLE = "CREATE TABLE IF NOT EXISTS " + YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM + "(" + YoutubeContract.YoutubeSwimmingTechniques._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + YoutubeContract.YoutubeSwimmingTechniques.SWIM_ID + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_THUMBNAIL + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_DURATION + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_VIEWCOUNT + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_LIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_DISLIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_TITLE + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_DESCRIPTION + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_CHANNELTITLE + " TEXT NOT NULL, " +
                YoutubeContract.YoutubeSwimmingTechniques.SWIM_FAVORATELIST + " NULL);";

        db.execSQL(CREATE_SWIMTECH_TABLE);

        final String CREATE_SWIMTECH_BREATH = "CREATE TABLE IF NOT EXISTS " + YoutubeContract.Breathing.TABLE_SWIM_BREATH + "(" + YoutubeContract.Breathing._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + YoutubeContract.Breathing.SWIM_ID + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_THUMBNAIL + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_DURATION + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_VIEWCOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_LIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_DISLIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_TITLE + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_DESCRIPTION + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_CHANNELTITLE + " TEXT NOT NULL, " +
                YoutubeContract.Breathing.SWIM_FAVORATELIST + " NULL);";

        db.execSQL(CREATE_SWIMTECH_BREATH);

        final String CREATE_SWIMTECH_BACKSTROKE = "CREATE TABLE IF NOT EXISTS " + YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE + "(" + YoutubeContract.Backstroke._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + YoutubeContract.Backstroke.SWIM_ID + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_THUMBNAIL + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_DURATION + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_VIEWCOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_LIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_DISLIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_TITLE + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_DESCRIPTION + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_CHANNELTITLE + " TEXT NOT NULL, " +
                YoutubeContract.Backstroke.SWIM_FAVORATELIST + " NULL);";

        db.execSQL(CREATE_SWIMTECH_BACKSTROKE);

        final String CREATE_SWIMTECH_BUTTERFLY = "CREATE TABLE IF NOT EXISTS " + YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY + "(" + YoutubeContract.Butterfly._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + YoutubeContract.Butterfly.SWIM_ID + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_THUMBNAIL + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_DURATION + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_VIEWCOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_LIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_DISLIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_TITLE + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_DESCRIPTION + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_CHANNELTITLE + " TEXT NOT NULL, " +
                YoutubeContract.Butterfly.SWIM_FAVORATELIST + " NULL);";

        db.execSQL(CREATE_SWIMTECH_BUTTERFLY);

        final String CREATE_SWIMTECH_FAVORITE = "CREATE TABLE IF NOT EXISTS " + YoutubeContract.Favorite.TABLE_SWIM_FAVORITE + "(" + YoutubeContract.Favorite._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + YoutubeContract.Favorite.SWIM_ID + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_THUMBNAIL + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_DURATION + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_VIEWCOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_LIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_DISLIKECOUNT + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_TITLE + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_DESCRIPTION + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_CHANNELTITLE + " TEXT NOT NULL, " +
                YoutubeContract.Favorite.SWIM_FAVORATELIST + " NULL);";

        db.execSQL(CREATE_SWIMTECH_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM + "'");

        db.execSQL("DROP TABLE IF EXISTS " + YoutubeContract.Breathing.TABLE_SWIM_BREATH);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                YoutubeContract.Breathing.TABLE_SWIM_BREATH + "'");

        db.execSQL("DROP TABLE IF EXISTS " + YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE + "'");

        db.execSQL("DROP TABLE IF EXISTS " + YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY + "'");
        db.execSQL("DROP TABLE IF EXISTS " + YoutubeContract.Favorite.TABLE_SWIM_FAVORITE);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                YoutubeContract.Favorite.TABLE_SWIM_FAVORITE + "'");

        // re-create database
        onCreate(db);
    }


}
