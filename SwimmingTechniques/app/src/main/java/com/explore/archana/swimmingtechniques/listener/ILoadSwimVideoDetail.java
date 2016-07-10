package com.explore.archana.swimmingtechniques.listener;

import android.database.Cursor;

import com.explore.archana.swimmingtechniques.model.SearchedVideoList;

/**
 * Created by archana on 8/20/2015.
 */
public interface ILoadSwimVideoDetail {

    public void onSwimVideoSelected(int position, SearchedVideoList videoList, Cursor cursor);
}
