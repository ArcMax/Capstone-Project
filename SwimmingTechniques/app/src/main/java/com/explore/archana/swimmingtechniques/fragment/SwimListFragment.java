package com.explore.archana.swimmingtechniques.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.activity.PlayerActivity;
import com.explore.archana.swimmingtechniques.adapter.GridImageAdapter;
import com.explore.archana.swimmingtechniques.connector.YoutubeConnector;
import com.explore.archana.swimmingtechniques.listener.ILoadSwimVideoDetail;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;

import java.util.List;

/**
 * Created by archana on 7/8/2016.
 */
public class SwimListFragment extends Fragment implements AdapterView.OnItemClickListener{

    public static final String TAG = "SwimListFragment";
    private GridView gridView;
    private List<SearchedVideoList> searchedVideoLists;
    private Handler handler;
    ILoadSwimVideoDetail iLoadSwimVideoDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate");
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            iLoadSwimVideoDetail = (ILoadSwimVideoDetail) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement listener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "oncreateview");
        View view = inflater.inflate(R.layout.swimlist_layout, null);
        gridView = (GridView) view.findViewById(R.id.swim_gridView);
        gridView.setOnItemClickListener(this);
        searchOnYoutube("swimming techniques");
        handler = new Handler();
        return view;
    }

    private void searchOnYoutube(final String keywords) {
        new Thread() {
            @Override
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(getActivity());
                searchedVideoLists = yc.searchList(keywords);

                handler.post(new Runnable() {
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound() {
        GridImageAdapter gridImageAdapter = new GridImageAdapter(getActivity(), R.layout.grid_item_layout, searchedVideoLists);
        if(searchedVideoLists!=null)
            gridView.setAdapter(gridImageAdapter);
    }

    private void addClickListener() {
       /* gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("VIDEO_ID", searchedVideoLists.get(position).getId());
                startActivity(intent);
            }
        });*/
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.swim_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Breathing:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.breathing);
                searchOnYoutube(getActivity().getResources().getString(R.string.search_breathing));
                break;
            case R.id.Backstroke:

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.backstroke);
                searchOnYoutube(getActivity().getResources().getString(R.string.search_backstroke));
                break;
            case R.id.ButterFly:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.butterfly);
                searchOnYoutube(getActivity().getResources().getString(R.string.search_butterfly));
                break;
            case R.id.Freestyle:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.freestyle);
                searchOnYoutube(getActivity().getResources().getString(R.string.search_freestyle));
                break;
            default:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.app_name);
                searchOnYoutube(getActivity().getResources().getString(R.string.app_name));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            SearchedVideoList data = (SearchedVideoList) parent.getItemAtPosition(position);
            iLoadSwimVideoDetail.onSwimVideoSelected(position, data, null);
        } catch (ClassCastException e) {
        }
    }
}
