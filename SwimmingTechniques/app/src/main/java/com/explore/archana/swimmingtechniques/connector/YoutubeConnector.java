package com.explore.archana.swimmingtechniques.connector;

import android.content.Context;
import android.util.Log;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.application.AppConfig;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Joiner;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;
    private YouTube.Videos.List videoQuery;

    public static final String TAG = "YoutubeConnector";

    public YoutubeConnector(Context context) {
        Log.d(TAG,"YoutubeConnector");
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            // Define the API request for retrieving search results.
            query = youtube.search().list("id,snippet");

            // Set your developer key from the Google Developers Console for
            // non-authenticated requests. See:
            // https://console.developers.google.com/
            query.setKey(AppConfig.GOOGLE_BROWSER_API_KEY);
            query.setMaxResults((long) 50);
            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            query.setType("video");
            // As a best practice, only retrieve the fields that the
            // application uses.
            query.setFields("items(id/videoId)");

            videoQuery = youtube.videos().list("id,snippet,contentDetails,statistics");
            videoQuery.setKey(AppConfig.GOOGLE_BROWSER_API_KEY);
            videoQuery.setMaxResults((long) 50);
            videoQuery.setFields("items(id,snippet/title,snippet/thumbnails/high/url,snippet/description,snippet/channelTitle," +
                    "contentDetails/duration,statistics/viewCount,statistics/likeCount,statistics/dislikeCount)");

        } catch (IOException e) {
            Log.d(TAG, "Could not initialize: " + e.getMessage());
        }
    }

    public List<SearchedVideoList> searchList(String keywords) {
        query.setQ(keywords);
        try {
            // Call the API and print results.
            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();
            List<String> videoIds = new ArrayList<String>();

            for (SearchResult result : results) {
                videoIds.add(result.getId().getVideoId());
            }
            // Merge video IDs
            Joiner stringJoiner = Joiner.on(',');
            String videoId = stringJoiner.join(videoIds);

            // Call the YouTube Data API's youtube.videos.list method to
            // retrieve the resources that represent the specified videos.
            videoQuery.setId(videoId);
            VideoListResponse videoListResponse = videoQuery.execute();
            List<Video> videoList = videoListResponse.getItems();

            List<SearchedVideoList> list = new ArrayList<>();
            for (Video video : videoList) {
                SearchedVideoList searchedVideoList = new SearchedVideoList();
                searchedVideoList.setId(video.getId());
                searchedVideoList.setDuration(video.getContentDetails().getDuration());
                searchedVideoList.setThumbnailURL(video.getSnippet().getThumbnails().getHigh().getUrl());
                searchedVideoList.setViewCount(video.getStatistics().getViewCount().toString());
                searchedVideoList.setTitle(video.getSnippet().getTitle());
                searchedVideoList.setLikesCount(String.valueOf(video.getStatistics().getLikeCount()));
                searchedVideoList.setDislikeCount(String.valueOf(video.getStatistics().getDislikeCount()));
                searchedVideoList.setDescription(video.getSnippet().getDescription());
                searchedVideoList.setChannelTitle(video.getSnippet().getChannelTitle());
                list.add(searchedVideoList);
            }
            Log.d(TAG,"yc searched video list"+list.get(1).getTitle());

            return list;
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
            return null;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}