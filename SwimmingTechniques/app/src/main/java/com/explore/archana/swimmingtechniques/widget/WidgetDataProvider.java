package com.explore.archana.swimmingtechniques.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.activity.SwimDetailActivity;
import com.explore.archana.swimmingtechniques.data.YoutubeContract;
import com.explore.archana.swimmingtechniques.utility.Constants;

import java.util.List;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";
    private Context mContext = null;
    private Cursor cursor;
    private String title;
    private String thumbnail;
    private String videoID;
    private List<String> swimCollection;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        initData();
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged");
        initData();
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount");
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt");
        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.witget_item_layout);
        view.setTextViewText(R.id.widget_title_text, title);
       /* view.setImageViewResource(R.id.widget_img_launcher, Integer.parseInt(thumbnail));
        Picasso.with(mContext).load(cursor.getString(versionThumbnailUrl)).into(holder.imageView);*/

        final Intent intent = new Intent();
        intent.putExtra("click", position + 1);
        intent.putExtra("cString", videoID);
        SwimDetailActivity.value = Constants.CURSOR_LOADER_FAVORITE;
        view.setOnClickFillInIntent(R.id.itemid, intent);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        Log.d(TAG, "getLoadingView");
        return new RemoteViews(mContext.getPackageName(), R.layout.witget_item_layout);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "getViewTypeCount");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId");
        if (cursor.moveToPosition(position))
            return cursor.getLong(1);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d(TAG, "hasStableIds");
        return true;
    }

    private void initData() {
        Log.d(TAG, "initData");
        cursor = mContext.getContentResolver().query(YoutubeContract.Favorite.CONTENT_FAVORITE_URI,
                null, null, null, "_id");
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                videoID = cursor.getString(cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_ID));
                title = cursor.getString(cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_TITLE));
                thumbnail = cursor.getString(cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_THUMBNAIL));
                cursor.moveToNext();
            }
        }
    }
}
