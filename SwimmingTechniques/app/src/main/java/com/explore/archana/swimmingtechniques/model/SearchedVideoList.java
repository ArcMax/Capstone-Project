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
    private String description;
    private String channelTitle;
    private String favorateList;

    private SearchedVideoList mInfo;

    public SearchedVideoList() {}
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getFavorateList() {
        return favorateList;
    }

    public void setFavorateList(String favorateList) {
        favorateList = favorateList;
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
        dest.writeString(description);
        dest.writeString(channelTitle);
        dest.writeString(favorateList);
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
        description = in.readString();
        channelTitle = in.readString();
        favorateList = in.readString();
        mInfo = in.readParcelable(SearchedVideoList.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchedVideoList> CREATOR = new Creator<SearchedVideoList>() {
        @Override
        public SearchedVideoList createFromParcel(Parcel source) {
            SearchedVideoList list = new SearchedVideoList();
            list.id = source.readString();
            list.thumbnailURL = source.readString();
            list.duration = source.readString();
            list.viewCount = source.readString();
            list.likesCount = source.readString();
            list.dislikeCount = source.readString();
            list.title = source.readString();
            list.description = source.readString();
            list.channelTitle = source.readString();
            list.favorateList = source.readString();
            return list;
        }

        @Override
        public SearchedVideoList[] newArray(int size) {
            return new SearchedVideoList[size];
        }
    };
}
