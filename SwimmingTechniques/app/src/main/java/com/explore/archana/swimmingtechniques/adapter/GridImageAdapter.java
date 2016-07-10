package com.explore.archana.swimmingtechniques.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.coustomviews.CustomImageView;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends ArrayAdapter<SearchedVideoList> {

    private Context mContext;
    private int mLayoutResourceId;
    private List<SearchedVideoList> swimDataArrayList = new ArrayList<SearchedVideoList>();

    public GridImageAdapter(Context context, int resource, List<SearchedVideoList> swimDatas) {
        super(context, resource, swimDatas);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.swimDataArrayList = swimDatas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
            row = layoutInflater.inflate(mLayoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (CustomImageView) row.findViewById(R.id.thumbnail);
            holder.views_textView = (TextView)row.findViewById(R.id.views_text);
            holder.title_textview = (TextView) row.findViewById(R.id.title_text);
            row.setTag(holder);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        SearchedVideoList data = swimDataArrayList.get(position);
        holder.views_textView.setText(data.getViewCount() + " " + "views" + " *   " + data.getLikesCount() + " " + "likes");

        //To set duration of video
       /* String fetched_duration = data.getDuration();
        String[] parts = fetched_duration.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)|(?<=\\d)(?=\\D)|(?<=\\d)(?=\\D)");
        Log.d("gridadapter", "strings" + "     " + parts[0] + " " + parts[1] + "  " + parts[3] + " " + parts[4]);
        String part1 = parts[1];
        String part2 = parts[3];
        String duration = part1+":"+part2;
        holder.duration_textview.setText(duration);*/

        holder.title_textview.setText(data.getTitle());
        Picasso.with(mContext).load(data.getThumbnailURL()).into(holder.imageView);

        return row;
    }

    @Override
    public int getCount() {
        return swimDataArrayList.size();
    }

    @Override
    public SearchedVideoList getItem(int position) {
        return swimDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        CustomImageView imageView;
        TextView views_textView;
        TextView title_textview;
    }
}
