package com.explore.archana.swimmingtechniques.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.data.YoutubeContract;
import com.explore.archana.swimmingtechniques.fragment.SwimDetailFragment;
import com.explore.archana.swimmingtechniques.fragment.SwimListFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by archana on 7/8/2016.
 */
public class SwimDetailActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "SwimDetailActivity";
    private static final int CURSOR_DETAIL_LOADER_ID = 0;
    public static Uri mUri;
    private Cursor mDetailCursor;

    private SwimDetailFragment swimDetailFragment;
    private TextView title;
    private TextView likeDislike;
    private TextView description;
    private String channelTitle, id,videoId;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swim_detail_layout);

        id = String.valueOf(getIntent().getIntExtra("click", 0));
        Log.d(TAG,"mUri"+mUri);

        Bundle args = new Bundle();
        args.putInt("position", getIntent().getIntExtra("click", 0));

        if (channelTitle != null)
            getActionBar().setTitle("By: " + channelTitle);

        getSupportLoaderManager().initLoader(CURSOR_DETAIL_LOADER_ID, args, SwimDetailActivity.this);

        swimDetailFragment = (SwimDetailFragment) getSupportFragmentManager().findFragmentById(R.id.swim_detail_container);

        title = (TextView) findViewById(R.id.title_textview);
        likeDislike = (TextView) findViewById(R.id.likesDislike_textview);
        description = (TextView) findViewById(R.id.description_body);
        description.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));

        findViewById(R.id.share).setOnClickListener(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.favo_fab);
        floatingActionButton.setOnClickListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, SwimListActivity.DETAILFRAGMENT, swimDetailFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        swimDetailFragment = (SwimDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, SwimListActivity.DETAILFRAGMENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(SwimDetailActivity.this)
                        .setType("text/plain")
                        .setText("https://www.youtube.com/watch?v="+videoId)
                        .getIntent(), "Share Swimmimg Techniques"));
                break;

            case R.id.favo_fab:
                Log.d(TAG, "check is selected" + floatingActionButton.isSelected());
                if (!floatingActionButton.isSelected()) {
                    updataDB("true");
                    floatingActionButton.setSelected(true);
                    Log.d(TAG, "selected click" + true);
                } else {
                    updataDB("false");
                    deleteFavo();
                    floatingActionButton.setSelected(false);
                    Log.d(TAG, "selected click" + false);
                }


                floatingActionButton.setImageResource(floatingActionButton.isSelected() ? R.mipmap.ic_heart : R.mipmap.ic_heart_dark);
                Drawable drawable = floatingActionButton.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }

                break;

            default:
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        String[] selectionArgs = null;
        if (args != null) {
            selection = YoutubeContract.YoutubeSwimmingTechniques._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(getIntent().getIntExtra("click", 0))};
        }
        return new CursorLoader(SwimDetailActivity.this,
                mUri,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDetailCursor = data;
        mDetailCursor.moveToFirst();
        DatabaseUtils.dumpCursor(data);

        channelTitle = mDetailCursor.getString(9);
        videoId = mDetailCursor.getString(1);
        swimDetailFragment.setVideoId(videoId);
        Log.d(TAG, "favo value" + mDetailCursor.getString(10));
        floatingActionButton.setSelected(Boolean.parseBoolean(mDetailCursor.getString(10)));
        floatingActionButton.setImageResource(floatingActionButton.isSelected() ? R.mipmap.ic_heart : R.mipmap.ic_heart_dark);
        title.setText(mDetailCursor.getString(7));
        likeDislike.setText("Likes: " + mDetailCursor.getString(5) + "  " + "Dislikes: " + mDetailCursor.getString(6));
        description.setText(mDetailCursor.getString(8));

        Log.d(TAG, "id" + mDetailCursor.getString(1));
        Log.d(TAG, "videothumbnail" + mDetailCursor.getString(2));
        Log.d(TAG, "duration" + mDetailCursor.getString(3));
        Log.d(TAG, "viewcount" + mDetailCursor.getString(4));
        Log.d(TAG, "likes" + mDetailCursor.getString(5));
        Log.d(TAG, "dislikes" + mDetailCursor.getString(6));
        Log.d(TAG, "title" + mDetailCursor.getString(7));
        Log.d(TAG, "description" + mDetailCursor.getString(8));
        Log.d(TAG, "channelid" + mDetailCursor.getString(9));
        Log.d(TAG, "favorate" + mDetailCursor.getString(10));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDetailCursor = null;
    }

    private void updataDB(String aTrue) {
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(YoutubeContract.Favorite.SWIM_ID, mDetailCursor.getString(1));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_THUMBNAIL, mDetailCursor.getString(2));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_DURATION, mDetailCursor.getString(3));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_VIEWCOUNT, mDetailCursor.getString(4));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_LIKECOUNT, mDetailCursor.getString(5));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_DISLIKECOUNT, mDetailCursor.getString(6));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_TITLE, mDetailCursor.getString(7));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_DESCRIPTION, mDetailCursor.getString(8));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_CHANNELTITLE, mDetailCursor.getString(9));
        dataToInsert.put(YoutubeContract.Favorite.SWIM_FAVORATELIST, aTrue);
        getContentResolver().insert(YoutubeContract.Favorite.CONTENT_FAVORITE_URI, dataToInsert);

        ContentValues updateOnlyFavoField = new ContentValues();
        updateOnlyFavoField.put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_FAVORATELIST, aTrue);
        String where = "_id" + "=" + id;
        getContentResolver().update(YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI, updateOnlyFavoField, where, null);
    }

    private void deleteFavo() {
        getContentResolver().delete(YoutubeContract.Favorite.CONTENT_FAVORITE_URI,mDetailCursor.getString(1),null);
    }

}