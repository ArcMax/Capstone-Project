package com.explore.archana.swimmingtechniques.fragment;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.activity.PoolMapActivity;
import com.explore.archana.swimmingtechniques.activity.SwimDetailActivity;
import com.explore.archana.swimmingtechniques.adapter.GridImageAdapter;
import com.explore.archana.swimmingtechniques.connector.YoutubeConnector;
import com.explore.archana.swimmingtechniques.data.YoutubeContract;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;
import com.explore.archana.swimmingtechniques.utility.Constants;

import java.util.List;

/**
 * Created by archana on 7/8/2016.
 */
@SuppressWarnings("unchecked")
public class SwimListFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "SwimListFragment";
    private GridView gridView;
    private List<SearchedVideoList> searchedVideoLists;
    private GridImageAdapter gridImageAdapter;
    private int mLoaderID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate");
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "oncreateview");
        View view = inflater.inflate(R.layout.swimlist_layout, null);
        gridView = (GridView) view.findViewById(R.id.swim_gridView);
        gridView.setOnItemClickListener(this);
        if (isNetworkConnected()) {
            new initiateLoader(getResources().getString(R.string.search_freestyle), Constants.CURSOR_LOADER_ID).execute();
        } else {
            getLoaderManager().restartLoader(Constants.CURSOR_LOADER_ID, null, SwimListFragment.this);
        }
        return view;
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(getContext().getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(getContext().getResources().getString(R.string.check_internet));
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void updateAdapter() {
        gridView.setAdapter(gridImageAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.swim_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Breathing:
                Log.d(TAG, "Breathing");
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.breathing);
                new initiateLoader(getActivity().getResources().getString(R.string.search_breathing), Constants.CURSOR_LOADER_BREATH_ID).execute();
                mLoaderID = Constants.CURSOR_LOADER_BREATH_ID;
                break;
            case R.id.Backstroke:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.backstroke);
                new initiateLoader(getActivity().getResources().getString(R.string.search_backstroke), Constants.CURSOR_LOADER_BACKSTROKE).execute();
                mLoaderID = Constants.CURSOR_LOADER_BACKSTROKE;
                break;
            case R.id.ButterFly:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.butterfly);
                new initiateLoader(getActivity().getResources().getString(R.string.search_butterfly), Constants.CURSOR_LOADER_BUTTERFLY).execute();
                mLoaderID = Constants.CURSOR_LOADER_BUTTERFLY;
                break;
            case R.id.Freestyle:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.freestyle);
                new initiateLoader(getActivity().getResources().getString(R.string.search_freestyle), Constants.CURSOR_LOADER_ID).execute();
                mLoaderID = Constants.CURSOR_LOADER_ID;
                break;
            case R.id.Favorite:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.favorite);
                getLoaderManager().restartLoader(Constants.CURSOR_LOADER_FAVORITE, null, SwimListFragment.this);
                mLoaderID = Constants.CURSOR_LOADER_FAVORITE;
                break;
            case R.id.poolLocation:
                Intent intent = new Intent(getActivity().getApplicationContext(), PoolMapActivity.class);
                startActivity(intent);
                break;
            default:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                new initiateLoader(getActivity().getResources().getString(R.string.app_name), Constants.CURSOR_LOADER_ID).execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    @TargetApi(21)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // increment the position to match Database Ids indexed starting at 1
        Cursor cPosition = (Cursor) parent.getItemAtPosition(position);
        Log.d(TAG, "cursor position" + cPosition.getPosition() + "cursor getcount" + cPosition.getCount() + "position" + position + "cursor at string" + cPosition.getString(1));
        int uriId = position + 1;
        String cString = cPosition.getString(1);
        try {
            Intent intent = new Intent(getActivity(), SwimDetailActivity.class);
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
            intent.putExtra("click", uriId);
            intent.putExtra("cString", cString);
            updateUri(mLoaderID, uriId);
            getContext().startActivity(intent, bundle);
        } catch (ClassCastException e) {
        }
    }

    //insert data into database
    private void insertData() {
        if (searchedVideoLists != null) {
            ContentValues[] swimValuesArr = new ContentValues[searchedVideoLists.size()];
            for (int i = 0; i < searchedVideoLists.size(); i++) {
                swimValuesArr[i] = new ContentValues();
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_ID, searchedVideoLists.get(i).getId());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_THUMBNAIL, searchedVideoLists.get(i).getThumbnailURL());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DURATION, searchedVideoLists.get(i).getDuration());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_VIEWCOUNT, searchedVideoLists.get(i).getViewCount());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_LIKECOUNT, searchedVideoLists.get(i).getLikesCount());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DISLIKECOUNT, searchedVideoLists.get(i).getDislikeCount());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_TITLE, searchedVideoLists.get(i).getTitle());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DESCRIPTION, searchedVideoLists.get(i).getDescription());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_CHANNELTITLE, searchedVideoLists.get(i).getChannelTitle());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_FAVORATELIST, searchedVideoLists.get(i).getFavorateList());
            }
            Log.d(TAG, "insert swim data searchedVideoLists" + searchedVideoLists.get(1).getTitle());
            getActivity().getContentResolver().bulkInsert(YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI, swimValuesArr);
        }
    }

    private void insertBreathData() {
        if (searchedVideoLists != null) {
            ContentValues[] swimBreathArr = new ContentValues[searchedVideoLists.size()];
            for (int i = 0; i < searchedVideoLists.size(); i++) {
                swimBreathArr[i] = new ContentValues();
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_ID, searchedVideoLists.get(i).getId());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_THUMBNAIL, searchedVideoLists.get(i).getThumbnailURL());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_DURATION, searchedVideoLists.get(i).getDuration());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_VIEWCOUNT, searchedVideoLists.get(i).getViewCount());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_LIKECOUNT, searchedVideoLists.get(i).getLikesCount());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_DISLIKECOUNT, searchedVideoLists.get(i).getDislikeCount());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_TITLE, searchedVideoLists.get(i).getTitle());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_DESCRIPTION, searchedVideoLists.get(i).getDescription());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_CHANNELTITLE, searchedVideoLists.get(i).getChannelTitle());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_FAVORATELIST, searchedVideoLists.get(i).getFavorateList());
            }
            Log.d(TAG, "insert breath data searchedVideoLists" + searchedVideoLists.get(1).getTitle());
            getActivity().getContentResolver().bulkInsert(YoutubeContract.Breathing.CONTENT_BREATH_URI, swimBreathArr);
        }
    }

    private void insertBackStrokeData() {
        if (searchedVideoLists != null) {
            ContentValues[] swimBackArr = new ContentValues[searchedVideoLists.size()];
            for (int i = 0; i < searchedVideoLists.size(); i++) {
                swimBackArr[i] = new ContentValues();
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_ID, searchedVideoLists.get(i).getId());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_THUMBNAIL, searchedVideoLists.get(i).getThumbnailURL());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_DURATION, searchedVideoLists.get(i).getDuration());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_VIEWCOUNT, searchedVideoLists.get(i).getViewCount());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_LIKECOUNT, searchedVideoLists.get(i).getLikesCount());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_DISLIKECOUNT, searchedVideoLists.get(i).getDislikeCount());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_TITLE, searchedVideoLists.get(i).getTitle());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_DESCRIPTION, searchedVideoLists.get(i).getDescription());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_CHANNELTITLE, searchedVideoLists.get(i).getChannelTitle());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_FAVORATELIST, searchedVideoLists.get(i).getFavorateList());
            }
            Log.d(TAG, "insert backstroke data searchedVideoLists" + searchedVideoLists.get(1).getTitle());
            getActivity().getContentResolver().bulkInsert(YoutubeContract.Backstroke.CONTENT_BACKSTROKE_URI, swimBackArr);
        }
    }

    private void insertButterflyData() {
        if (searchedVideoLists != null) {
            ContentValues[] swimButterArr = new ContentValues[searchedVideoLists.size()];
            for (int i = 0; i < searchedVideoLists.size(); i++) {
                swimButterArr[i] = new ContentValues();
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_ID, searchedVideoLists.get(i).getId());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_THUMBNAIL, searchedVideoLists.get(i).getThumbnailURL());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_DURATION, searchedVideoLists.get(i).getDuration());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_VIEWCOUNT, searchedVideoLists.get(i).getViewCount());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_LIKECOUNT, searchedVideoLists.get(i).getLikesCount());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_DISLIKECOUNT, searchedVideoLists.get(i).getDislikeCount());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_TITLE, searchedVideoLists.get(i).getTitle());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_DESCRIPTION, searchedVideoLists.get(i).getDescription());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_CHANNELTITLE, searchedVideoLists.get(i).getChannelTitle());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_FAVORATELIST, searchedVideoLists.get(i).getFavorateList());
            }
            Log.d(TAG, "insert butterfly data searchedVideoLists" + searchedVideoLists.get(1).getTitle());
            getActivity().getContentResolver().bulkInsert(YoutubeContract.Butterfly.CONTENT_BUTTERFLY_URI, swimButterArr);
        }
    }

    class initiateLoader extends AsyncTask<String, Void, List<SearchedVideoList>> {

        private String search;
        private int ILoaderID = 0;

        public initiateLoader(String string, int cursorLoaderId) {
            this.search = string;
            this.ILoaderID = cursorLoaderId;
        }

        @Override
        protected List<SearchedVideoList> doInBackground(String... params) {
            YoutubeConnector yc = new YoutubeConnector(getActivity());
            searchedVideoLists = yc.searchList(search);
            return searchedVideoLists;
        }

        @Override
        protected void onPostExecute(List<SearchedVideoList> searchedVideoLists) {
            if (isAdded())
                getLoaderManager().initLoader(ILoaderID, null, SwimListFragment.this);

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        CursorLoader loader = null;
        switch (id) {
            case 0:
                Log.d(TAG, "swimtech");
                Cursor cursor = getActivity().getContentResolver().query(YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI,
                        new String[]{YoutubeContract.YoutubeSwimmingTechniques._ID}, null, null, null);
                Log.d(TAG, "cursor data" + YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI + "    " + YoutubeContract.YoutubeSwimmingTechniques._ID);
                if (cursor.getCount() == 0)
                    insertData();
                gridImageAdapter = new GridImageAdapter(getActivity(), null, 0, Constants.CURSOR_LOADER_ID);
                updateAdapter();
                loader = new CursorLoader(getActivity(),
                        YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                Log.d(TAG, "loader" + loader);
                break;
            case 1:
                Log.d(TAG, "breath");
                Cursor cursorB = getActivity().getContentResolver().query(YoutubeContract.Breathing.CONTENT_BREATH_URI,
                        new String[]{YoutubeContract.Breathing._ID}, null, null, null);
                if (cursorB.getCount() == 0)
                    insertBreathData();
                gridImageAdapter = new GridImageAdapter(getActivity(), null, 0, Constants.CURSOR_LOADER_BREATH_ID);
                updateAdapter();
                loader = new CursorLoader(getActivity(),
                        YoutubeContract.Breathing.CONTENT_BREATH_URI,
                        null,
                        null,
                        null,
                        null);
                break;
            case 2:
                Log.d(TAG, "backstroke");
                Cursor cursorC = getActivity().getContentResolver().query(YoutubeContract.Backstroke.CONTENT_BACKSTROKE_URI,
                        new String[]{YoutubeContract.Backstroke._ID}, null, null, null);
                if (cursorC.getCount() == 0)
                    insertBackStrokeData();
                gridImageAdapter = new GridImageAdapter(getActivity(), null, 0, Constants.CURSOR_LOADER_BACKSTROKE);
                updateAdapter();
                loader = new CursorLoader(getActivity(),
                        YoutubeContract.Backstroke.CONTENT_BACKSTROKE_URI,
                        null,
                        null,
                        null,
                        null);
                break;
            case 3:
                Log.d(TAG, "butterfly");
                Cursor cursorD = getActivity().getContentResolver().query(YoutubeContract.Butterfly.CONTENT_BUTTERFLY_URI,
                        new String[]{YoutubeContract.Butterfly._ID}, null, null, null);
                if (cursorD.getCount() == 0)
                    insertButterflyData();
                gridImageAdapter = new GridImageAdapter(getActivity(), null, 0, Constants.CURSOR_LOADER_BUTTERFLY);
                updateAdapter();
                loader = new CursorLoader(getActivity(),
                        YoutubeContract.Butterfly.CONTENT_BUTTERFLY_URI,
                        null,
                        null,
                        null,
                        null);
                break;
            case 4:
                Log.d(TAG, "favorite");
                Cursor cursorE = getActivity().getContentResolver().query(YoutubeContract.Favorite.CONTENT_FAVORITE_URI,
                        new String[]{YoutubeContract.Favorite._ID}, null, null, null);
                if (cursorE.getCount() > 0) {
                    gridImageAdapter = new GridImageAdapter(getActivity(), null, 0, Constants.CURSOR_LOADER_FAVORITE);
                    updateAdapter();
                } else {
                    Toast.makeText(getActivity(), "No Favorite movie added", Toast.LENGTH_LONG).show();
                }
                loader = new CursorLoader(getActivity(),
                        YoutubeContract.Favorite.CONTENT_FAVORITE_URI,
                        null,
                        null,
                        null,
                        null);
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished");
        gridImageAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset");
        gridImageAdapter.swapCursor(null);
    }

    private void updateUri(int loaderID, int uriId) {
        Uri uri;
        switch (mLoaderID) {
            case Constants.CURSOR_LOADER_ID:
                uri = ContentUris.withAppendedId(YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI, uriId);
                SwimDetailActivity.mUri = uri;
                SwimDetailActivity.value = Constants.CURSOR_LOADER_ID;
                break;
            case Constants.CURSOR_LOADER_BREATH_ID:
                uri = ContentUris.withAppendedId(YoutubeContract.Breathing.CONTENT_BREATH_URI, uriId);
                SwimDetailActivity.mUri = uri;
                SwimDetailActivity.value = Constants.CURSOR_LOADER_BREATH_ID;
                break;
            case Constants.CURSOR_LOADER_BACKSTROKE:
                uri = ContentUris.withAppendedId(YoutubeContract.Backstroke.CONTENT_BACKSTROKE_URI, uriId);
                SwimDetailActivity.mUri = uri;
                SwimDetailActivity.value = Constants.CURSOR_LOADER_BACKSTROKE;
                break;
            case Constants.CURSOR_LOADER_BUTTERFLY:
                uri = ContentUris.withAppendedId(YoutubeContract.Butterfly.CONTENT_BUTTERFLY_URI, uriId);
                SwimDetailActivity.mUri = uri;
                SwimDetailActivity.value = Constants.CURSOR_LOADER_BUTTERFLY;
                break;
            case Constants.CURSOR_LOADER_FAVORITE:
                uri = ContentUris.withAppendedId(YoutubeContract.Favorite.CONTENT_FAVORITE_URI, uriId);
                SwimDetailActivity.mUri = uri;
                SwimDetailActivity.value = Constants.CURSOR_LOADER_FAVORITE;
                break;

        }
    }
}