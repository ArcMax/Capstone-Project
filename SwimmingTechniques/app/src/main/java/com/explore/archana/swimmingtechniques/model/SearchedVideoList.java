package com.explore.archana.swimmingtechniques.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by archana on 7/8/2016.
 */
public class SearchedVideoList implements Parcelable {

    private String id;
    private String thumbnailURL;
    private String duration;
    private String viewCount;
    private String likesCount;
    private String dislikeCount;
    private String title;

    public static final String KEY_ID = "id";
    public static final String KEY_TUMBNAIL = "thumbnail";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_VIEWCOUNT = "viewcount";
    public static final String KEY_LIKECOUNT = "likecount";
    public static final String KEY_DISLIKECOUNT = "dislikecount";
    public static final String KEY_TITLE = "title";

    private SearchedVideoList mInfo;


    public SearchedVideoList() {

    }

   /* public SearchedVideoList(String id, String thumbnailURL, String duration, String viewCount, String likesCount, String dislikeCount, String title) {
        this.id = id;
        this.thumbnailURL = thumbnailURL;
        this.duration = duration;
        this.viewCount = viewCount;
        this.likesCount = likesCount;
        this.dislikeCount = dislikeCount;
        this.title = title;
    }*/


    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(thumbnailURL);
        dest.writeString(duration);
        dest.writeString(viewCount);
        dest.writeString(likesCount);
        dest.writeString(dislikeCount);
        dest.writeString(title);
        dest.writeParcelable(mInfo,flags);
    }

    private void readFromParcel(Parcel in) {
        id = in.readString();
        thumbnailURL = in.readString();
        duration = in.readString();
        viewCount = in.readString();
        likesCount = in.readString();
        dislikeCount = in.readString();
        title = in.readString();
        mInfo = in.readParcelable(SearchedVideoList.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchedVideoList> CREATOR = new Creator<SearchedVideoList>() {
        @Override
        public SearchedVideoList createFromParcel(Parcel source) {
            SearchedVideoList list = new SearchedVideoList();
            list.id = source.readString();
            list.thumbnailURL = source.readString();
            list.likesCount = source.readString();
            list.dislikeCount = source.readString();
            list.duration = source.readString();
            list.title = source.readString();
            return list;
        }

        @Override
        public SearchedVideoList[] newArray(int size) {
            return new SearchedVideoList[size];
        }
    };
}
