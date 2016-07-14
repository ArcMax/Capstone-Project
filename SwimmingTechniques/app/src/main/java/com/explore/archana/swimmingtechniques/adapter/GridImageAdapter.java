package com.explore.archana.swimmingtechniques.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.coustomviews.CustomImageView;
import com.explore.archana.swimmingtechniques.data.YoutubeContract;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends CursorAdapter {

    private static final String TAG = GridImageAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater cursorInflater;
    private int mLoaderID;

    int versionTitle;
    int versionLikes;
    int versionDislikes;
    int versionThumbnailUrl;

    public GridImageAdapter(Context context, Cursor cursor, int flag, int cursorLoaderId) {
        super(context,cursor,flag);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.mLoaderID = cursorLoaderId;
        Log.d(TAG,"cursor loader value"+mLoaderID);
        Log.d(TAG,"constructor");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = cursorInflater.inflate(R.layout.grid_item_layout, parent, false);;
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        Log.d(TAG,"view"+view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        Log.d(TAG,"bind view"+view);
        if(mLoaderID == 0) {
            versionTitle = cursor.getColumnIndex(YoutubeContract.YoutubeSwimmingTechniques.SWIM_TITLE);
            versionLikes = cursor.getColumnIndex(YoutubeContract.YoutubeSwimmingTechniques.SWIM_LIKECOUNT);
            versionDislikes = cursor.getColumnIndex(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DISLIKECOUNT);
            versionThumbnailUrl = cursor.getColumnIndex(YoutubeContract.YoutubeSwimmingTechniques.SWIM_THUMBNAIL);
        }if(mLoaderID == 1) {
            versionTitle = cursor.getColumnIndex(YoutubeContract.Breathing.SWIM_TITLE);
            versionLikes = cursor.getColumnIndex(YoutubeContract.Breathing.SWIM_LIKECOUNT);
            versionDislikes = cursor.getColumnIndex(YoutubeContract.Breathing.SWIM_DISLIKECOUNT);
            versionThumbnailUrl = cursor.getColumnIndex(YoutubeContract.Breathing.SWIM_THUMBNAIL);
        }if(mLoaderID == 2) {
            versionTitle = cursor.getColumnIndex(YoutubeContract.Backstroke.SWIM_TITLE);
            versionLikes = cursor.getColumnIndex(YoutubeContract.Backstroke.SWIM_LIKECOUNT);
            versionDislikes = cursor.getColumnIndex(YoutubeContract.Backstroke.SWIM_DISLIKECOUNT);
            versionThumbnailUrl = cursor.getColumnIndex(YoutubeContract.Backstroke.SWIM_THUMBNAIL);
        }if(mLoaderID == 3) {
            versionTitle = cursor.getColumnIndex(YoutubeContract.Butterfly.SWIM_TITLE);
            versionLikes = cursor.getColumnIndex(YoutubeContract.Butterfly.SWIM_LIKECOUNT);
            versionDislikes = cursor.getColumnIndex(YoutubeContract.Butterfly.SWIM_DISLIKECOUNT);
            versionThumbnailUrl = cursor.getColumnIndex(YoutubeContract.Butterfly.SWIM_THUMBNAIL);
        }if(mLoaderID == 4) {
            versionTitle = cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_TITLE);
            versionLikes = cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_LIKECOUNT);
            versionDislikes = cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_DISLIKECOUNT);
            versionThumbnailUrl = cursor.getColumnIndex(YoutubeContract.Favorite.SWIM_THUMBNAIL);
        }

        Log.d(TAG,"version title"+versionTitle);
        holder.views_textView.setText(cursor.getString(versionLikes) + " " + "views" + " *   " + cursor.getString(versionDislikes) + " " + "likes");
        holder.title_textview.setText(cursor.getString(versionTitle));
        Picasso.with(mContext).load(cursor.getString(versionThumbnailUrl)).into(holder.imageView);

    }

    class ViewHolder{
        CustomImageView imageView;
        TextView views_textView;
        TextView title_textview;

        public ViewHolder(View view){
            imageView = (CustomImageView)view.findViewById(R.id.thumbnail);
            views_textView = (TextView)view.findViewById(R.id.views_text);
            title_textview = (TextView) view.findViewById(R.id.title_text);
        }
    }

    @Override
    public int getCount() {

        if(getCursor()==null)
            return 0;
        return super.getCount();
    }
}