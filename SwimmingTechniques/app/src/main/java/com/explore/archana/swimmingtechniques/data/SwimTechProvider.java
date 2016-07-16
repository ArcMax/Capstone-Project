package com.explore.archana.swimmingtechniques.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


/**
 * Created by archana on 7/11/2016.
 */
public class SwimTechProvider extends ContentProvider {

    private static final String TAG = SwimTechProvider.class.getSimpleName();
    private static final UriMatcher mMatcher = buildUriMatcher();
    private SwimTechDBHelper swimTechDBHelper;

    //code for UriMatcher
    private static final int YOUTUBESWIM = 100;
    private static final int YOUTUBESWIM_WITH_ID = 101;
    private static final int YOUTUBE_BREATHING = 200;
    private static final int YOUTUBE_BREATHING_WITH_ID = 201;
    private static final int YOUTUBE_BACKSTROKE = 300;
    private static final int YOUTUBE_BACKSTROKE_WITH_ID = 301;
    private static final int YOUTUBE_BUTTERFLY = 400;
    private static final int YOUTUBE_BUTTERFLY_WITH_ID = 401;
    private static final int YOUTUBE_FAVORITE = 500;
    private static final int YOUTUBE_FAVORITE_WITH_ID = 501;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = YoutubeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM, YOUTUBESWIM);
        matcher.addURI(authority, YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM + "/#", YOUTUBESWIM_WITH_ID);

        matcher.addURI(authority, YoutubeContract.Breathing.TABLE_SWIM_BREATH, YOUTUBE_BREATHING);
        matcher.addURI(authority, YoutubeContract.Breathing.TABLE_SWIM_BREATH + "/#", YOUTUBE_BREATHING_WITH_ID);

        matcher.addURI(authority, YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE, YOUTUBE_BACKSTROKE);
        matcher.addURI(authority, YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE + "/#", YOUTUBE_BACKSTROKE_WITH_ID);

        matcher.addURI(authority, YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY, YOUTUBE_BUTTERFLY);
        matcher.addURI(authority, YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY + "/#", YOUTUBE_BUTTERFLY_WITH_ID);

        matcher.addURI(authority, YoutubeContract.Favorite.TABLE_SWIM_FAVORITE, YOUTUBE_FAVORITE);
        matcher.addURI(authority, YoutubeContract.Favorite.TABLE_SWIM_FAVORITE + "/#", YOUTUBE_FAVORITE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        swimTechDBHelper = new SwimTechDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (mMatcher.match(uri)) {
            case YOUTUBESWIM: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBESWIM_WITH_ID: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_BREATHING: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Breathing.TABLE_SWIM_BREATH,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_BREATHING_WITH_ID: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Breathing.TABLE_SWIM_BREATH,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_BACKSTROKE: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_BACKSTROKE_WITH_ID: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_BUTTERFLY: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_BUTTERFLY_WITH_ID: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_FAVORITE: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case YOUTUBE_FAVORITE_WITH_ID: {
                cursor = swimTechDBHelper.getReadableDatabase().query(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        Context context = getContext();
        if (null != context) {
            cursor.setNotificationUri(context.getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = mMatcher.match(uri);

        switch (match) {
            case YOUTUBESWIM: {
                return YoutubeContract.YoutubeSwimmingTechniques.CONTENT_DIR_TYPE;
            }
            case YOUTUBESWIM_WITH_ID: {
                return YoutubeContract.YoutubeSwimmingTechniques.CONTENT_ITEM_TYPE;
            }
            case YOUTUBE_BREATHING: {
                return YoutubeContract.Breathing.CONTENT_DIR_BREATH_TYPE;
            }
            case YOUTUBE_BREATHING_WITH_ID: {
                return YoutubeContract.Breathing.CONTENT_ITEM_BREATH_TYPE;
            }
            case YOUTUBE_BACKSTROKE: {
                return YoutubeContract.Backstroke.CONTENT_DIR_BACKSTROKE_TYPE;
            }
            case YOUTUBE_BACKSTROKE_WITH_ID: {
                return YoutubeContract.Backstroke.CONTENT_ITEM_BACKSTROKE_TYPE;
            }
            case YOUTUBE_BUTTERFLY: {
                return YoutubeContract.Butterfly.CONTENT_DIR_BUTTERFLY_TYPE;
            }
            case YOUTUBE_BUTTERFLY_WITH_ID: {
                return YoutubeContract.Butterfly.CONTENT_ITEM_BUTTERFLY_TYPE;
            }
            case YOUTUBE_FAVORITE: {
                return YoutubeContract.Favorite.CONTENT_DIR_FAVORITE_TYPE;
            }
            case YOUTUBE_FAVORITE_WITH_ID: {
                return YoutubeContract.Favorite.CONTENT_ITEM_FAVORITE_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase liteDatabase = swimTechDBHelper.getWritableDatabase();
        Uri swimUri;

        switch (mMatcher.match(uri)) {
            case YOUTUBESWIM: {
                long _id = liteDatabase.insert(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    swimUri = YoutubeContract.YoutubeSwimmingTechniques.buildSwimTechUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            case YOUTUBE_BREATHING: {
                long _id = liteDatabase.insert(YoutubeContract.Breathing.TABLE_SWIM_BREATH, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    swimUri = YoutubeContract.Breathing.buildBreathUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            case YOUTUBE_BACKSTROKE: {
                long _id = liteDatabase.insert(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    swimUri = YoutubeContract.Backstroke.buildBackStrokeUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            case YOUTUBE_BUTTERFLY: {
                long _id = liteDatabase.insert(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    swimUri = YoutubeContract.Butterfly.buildButterflyUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            case YOUTUBE_FAVORITE: {
                long _id = liteDatabase.insert(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    swimUri = YoutubeContract.Favorite.buildFavoriteUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return swimUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = swimTechDBHelper.getWritableDatabase();
        final int match = mMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case YOUTUBESWIM:
                numDeleted = db.delete(
                        YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM + "'");
                break;
            case YOUTUBESWIM_WITH_ID:
                numDeleted = db.delete(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM,
                        YoutubeContract.YoutubeSwimmingTechniques._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM + "'");
                break;

            case YOUTUBE_BREATHING:
                numDeleted = db.delete(
                        YoutubeContract.Breathing.TABLE_SWIM_BREATH, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Breathing.TABLE_SWIM_BREATH + "'");
                break;
            case YOUTUBE_BREATHING_WITH_ID:
                numDeleted = db.delete(YoutubeContract.Breathing.TABLE_SWIM_BREATH,
                        YoutubeContract.Breathing._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Breathing.TABLE_SWIM_BREATH + "'");

                break;

            case YOUTUBE_BACKSTROKE:
                numDeleted = db.delete(
                        YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE + "'");
                break;
            case YOUTUBE_BACKSTROKE_WITH_ID:
                numDeleted = db.delete(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE,
                        YoutubeContract.Backstroke._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE + "'");

                break;

            case YOUTUBE_BUTTERFLY:
                numDeleted = db.delete(
                        YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY + "'");
                break;
            case YOUTUBE_BUTTERFLY_WITH_ID:
                numDeleted = db.delete(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY,
                        YoutubeContract.Butterfly._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY + "'");

                break;

            case YOUTUBE_FAVORITE:
                numDeleted = db.delete(
                        YoutubeContract.Favorite.TABLE_SWIM_FAVORITE, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Favorite.TABLE_SWIM_FAVORITE + "'");
                break;
            case YOUTUBE_FAVORITE_WITH_ID:
                numDeleted = db.delete(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE,
                        YoutubeContract.Favorite.SWIM_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        YoutubeContract.Favorite.TABLE_SWIM_FAVORITE + "'");

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = swimTechDBHelper.getWritableDatabase();
        int numUpdated = 0;

        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (mMatcher.match(uri)) {
            case YOUTUBESWIM: {
                numUpdated = db.update(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case YOUTUBESWIM_WITH_ID: {
                numUpdated = db.update(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM,
                        values,
                        YoutubeContract.YoutubeSwimmingTechniques._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case YOUTUBE_BREATHING: {
                numUpdated = db.update(YoutubeContract.Breathing.TABLE_SWIM_BREATH,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case YOUTUBE_BREATHING_WITH_ID: {
                numUpdated = db.update(YoutubeContract.Breathing.TABLE_SWIM_BREATH,
                        values,
                        YoutubeContract.Breathing._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case YOUTUBE_BACKSTROKE: {
                numUpdated = db.update(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case YOUTUBE_BACKSTROKE_WITH_ID: {
                numUpdated = db.update(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE,
                        values,
                        YoutubeContract.Backstroke._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case YOUTUBE_BUTTERFLY: {
                numUpdated = db.update(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case YOUTUBE_BUTTERFLY_WITH_ID: {
                numUpdated = db.update(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY,
                        values,
                        YoutubeContract.Butterfly._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case YOUTUBE_FAVORITE: {
                numUpdated = db.update(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case YOUTUBE_FAVORITE_WITH_ID: {
                numUpdated = db.update(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE,
                        values,
                        YoutubeContract.Favorite._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = swimTechDBHelper.getWritableDatabase();
        final int match = mMatcher.match(uri);
        switch (match) {
            case YOUTUBESWIM:
                //allow for multiple transaction
                db.beginTransaction();
                //keep track of successful inserts
                int numInserted = 0;
                try {
                    for (ContentValues contentValues : values) {
                        if (contentValues == null)
                            throw new IllegalArgumentException("Cannot have null content values");

                        long _id = -1;
                        _id = db.insertOrThrow(YoutubeContract.YoutubeSwimmingTechniques.TABLE_SWIM, null, contentValues);
                        if (_id != -1)
                            numInserted++;
                    }
                    if (numInserted > 0)
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (numInserted > 0)
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                return numInserted;
            case YOUTUBE_BREATHING:
                //allow for multiple transaction
                db.beginTransaction();
                //keep track of successful inserts
                int numInserted1 = 0;
                try {
                    for (ContentValues contentValues : values) {
                        if (contentValues == null)
                            throw new IllegalArgumentException("Cannot have null content values");

                        long _id = -1;
                        _id = db.insertOrThrow(YoutubeContract.Breathing.TABLE_SWIM_BREATH, null, contentValues);
                        if (_id != -1)
                            numInserted1++;
                    }
                    if (numInserted1 > 0)
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (numInserted1 > 0)
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                return numInserted1;

            case YOUTUBE_BACKSTROKE:
                //allow for multiple transaction
                db.beginTransaction();
                //keep track of successful inserts
                int numInserted2 = 0;
                try {
                    for (ContentValues contentValues : values) {
                        if (contentValues == null)
                            throw new IllegalArgumentException("Cannot have null content values");

                        long _id = -1;
                        _id = db.insertOrThrow(YoutubeContract.Backstroke.TABLE_SWIM_BACKSTROKE, null, contentValues);
                        if (_id != -1)
                            numInserted2++;
                    }
                    if (numInserted2 > 0)
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (numInserted2 > 0)
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                return numInserted2;
            case YOUTUBE_BUTTERFLY:
                //allow for multiple transaction
                db.beginTransaction();
                //keep track of successful inserts
                int numInserted3 = 0;
                try {
                    for (ContentValues contentValues : values) {
                        if (contentValues == null)
                            throw new IllegalArgumentException("Cannot have null content values");

                        long _id = -1;
                        _id = db.insertOrThrow(YoutubeContract.Butterfly.TABLE_SWIM_BUTTERFLY, null, contentValues);
                        if (_id != -1)
                            numInserted3++;
                    }
                    if (numInserted3 > 0)
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (numInserted3 > 0)
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                return numInserted3;
            case YOUTUBE_FAVORITE:
                //allow for multiple transaction
                db.beginTransaction();
                //keep track of successful inserts
                int numInserted4 = 0;
                try {
                    for (ContentValues contentValues : values) {
                        if (contentValues == null)
                            throw new IllegalArgumentException("Cannot have null content values");

                        long _id = -1;
                        _id = db.insertOrThrow(YoutubeContract.Favorite.TABLE_SWIM_FAVORITE, null, contentValues);
                        if (_id != -1)
                            numInserted4++;
                    }
                    if (numInserted4 > 0)
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                if (numInserted4 > 0)
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                return numInserted4;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    /*// You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    public void shutdown() {
        swimTechDBHelper.close();
        super.shutdown();
    }*/
}
