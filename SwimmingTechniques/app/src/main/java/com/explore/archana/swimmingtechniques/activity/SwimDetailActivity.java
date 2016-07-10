package com.explore.archana.swimmingtechniques.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.fragment.YouTubeFragment;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;


/**
 * Created by archana on 7/8/2016.
 */
public class SwimDetailActivity extends FragmentActivity {

    public static final String TAG = "SwimDetailActivity";
    public static final String KEY_VIDEO_ID = "KEY_VIDEO";
    YouTubeFragment youTubeFragment;
    SearchedVideoList searchedVideoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swim_detail_activity);

        searchedVideoList = (SearchedVideoList)getIntent().getParcelableExtra(KEY_VIDEO_ID);
        Log.d(TAG,"parcelable data"+searchedVideoList);

        if (searchedVideoList !=null) {
            String videoId = searchedVideoList.getId();
            Log.d(TAG,"inside bundle"+ videoId);
            youTubeFragment = (YouTubeFragment) getSupportFragmentManager().findFragmentById(R.id.swim_detail_container);
            youTubeFragment.setVideoId(videoId);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, SwimListActivity.DETAILFRAGMENT, youTubeFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        youTubeFragment = (YouTubeFragment) getSupportFragmentManager().getFragment(savedInstanceState, SwimListActivity.DETAILFRAGMENT);
    }
}
