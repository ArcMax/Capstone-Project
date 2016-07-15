package com.explore.archana.swimmingtechniques.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by archana on 7/11/2016.
 */
public class YoutubeContract {

    public static final String CONTENT_AUTHORITY = "com.explore.archana.swimmingtechniques.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final class YoutubeSwimmingTechniques implements BaseColumns{
        //table name
        public static final String TABLE_SWIM = "swim";
        //columns
        public static final String _ID = "_id";
        public static final String SWIM_ID = "swim_id";
        public static final String SWIM_THUMBNAIL = "swim_thumbnail";
        public static final String SWIM_DURATION = "swim_duration";
        public static final String SWIM_VIEWCOUNT = "swim_viewcount";
        public static final String SWIM_LIKECOUNT = "swim_likecount";
        public static final String SWIM_DISLIKECOUNT = "swim_dislikecount";
        public static final String SWIM_TITLE = "swim_title";
        public static final String SWIM_DESCRIPTION = "swim_description";
        public static final String SWIM_CHANNELTITLE = "swim_channeltitle";
        public static final String SWIM_FAVORATELIST = "swim_favoratelist";

        //create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SWIM).build();

        //create cursor for base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM;

        //create cursor for base type item for single entry
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM;

        //For buildeing URIs on insertion
        public static Uri buildSwimTechUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

        public static Uri buildSwimUriWithSwimId(String SwimId) {
            return CONTENT_URI.buildUpon().appendPath(SwimId).build();
        }
    }

    public static final class Breathing implements BaseColumns {

        public static final String TABLE_SWIM_BREATH = "swim_breath";

        public static final String _ID = "_id";
        public static final String SWIM_ID = "swim_id";
        public static final String SWIM_THUMBNAIL = "swim_thumbnail";
        public static final String SWIM_DURATION = "swim_duration";
        public static final String SWIM_VIEWCOUNT = "swim_viewcount";
        public static final String SWIM_LIKECOUNT = "swim_likecount";
        public static final String SWIM_DISLIKECOUNT = "swim_dislikecount";
        public static final String SWIM_TITLE = "swim_title";
        public static final String SWIM_DESCRIPTION = "swim_description";
        public static final String SWIM_CHANNELTITLE = "swim_channeltitle";
        public static final String SWIM_FAVORATELIST = "swim_favoratelist";

        public static final Uri CONTENT_BREATH_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SWIM_BREATH).build();

        public static final String CONTENT_DIR_BREATH_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_BREATH;

        public static final String CONTENT_ITEM_BREATH_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_BREATH;

        public static Uri buildBreathUri(long id){
            return ContentUris.withAppendedId(CONTENT_BREATH_URI,id);
        }
    }

    public static final class Backstroke implements BaseColumns {

        public static final String TABLE_SWIM_BACKSTROKE = "swim_backstroke";

        public static final String _ID = "_id";
        public static final String SWIM_ID = "swim_id";
        public static final String SWIM_THUMBNAIL = "swim_thumbnail";
        public static final String SWIM_DURATION = "swim_duration";
        public static final String SWIM_VIEWCOUNT = "swim_viewcount";
        public static final String SWIM_LIKECOUNT = "swim_likecount";
        public static final String SWIM_DISLIKECOUNT = "swim_dislikecount";
        public static final String SWIM_TITLE = "swim_title";
        public static final String SWIM_DESCRIPTION = "swim_description";
        public static final String SWIM_CHANNELTITLE = "swim_channeltitle";
        public static final String SWIM_FAVORATELIST = "swim_favoratelist";

        public static final Uri CONTENT_BACKSTROKE_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SWIM_BACKSTROKE).build();

        public static final String CONTENT_DIR_BACKSTROKE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_BACKSTROKE;

        public static final String CONTENT_ITEM_BACKSTROKE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_BACKSTROKE;

        public static Uri buildBackStrokeUri(long id){
            return ContentUris.withAppendedId(CONTENT_BACKSTROKE_URI,id);
        }
    }

    public static final class Butterfly implements BaseColumns {

        public static final String TABLE_SWIM_BUTTERFLY = "swim_butterfly";

        public static final String _ID = "_id";
        public static final String SWIM_ID = "swim_id";
        public static final String SWIM_THUMBNAIL = "swim_thumbnail";
        public static final String SWIM_DURATION = "swim_duration";
        public static final String SWIM_VIEWCOUNT = "swim_viewcount";
        public static final String SWIM_LIKECOUNT = "swim_likecount";
        public static final String SWIM_DISLIKECOUNT = "swim_dislikecount";
        public static final String SWIM_TITLE = "swim_title";
        public static final String SWIM_DESCRIPTION = "swim_description";
        public static final String SWIM_CHANNELTITLE = "swim_channeltitle";
        public static final String SWIM_FAVORATELIST = "swim_favoratelist";

        public static final Uri CONTENT_BUTTERFLY_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SWIM_BUTTERFLY).build();

        public static final String CONTENT_DIR_BUTTERFLY_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_BUTTERFLY;

        public static final String CONTENT_ITEM_BUTTERFLY_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_BUTTERFLY;

        public static Uri buildButterflyUri(long id){
            return ContentUris.withAppendedId(CONTENT_BUTTERFLY_URI,id);
        }
    }
    public static final class Favorite implements BaseColumns {

        public static final String TABLE_SWIM_FAVORITE = "swim_favorite";

        public static final String _ID = "_id";
        public static final String SWIM_ID = "swim_id";
        public static final String SWIM_THUMBNAIL = "swim_thumbnail";
        public static final String SWIM_DURATION = "swim_duration";
        public static final String SWIM_VIEWCOUNT = "swim_viewcount";
        public static final String SWIM_LIKECOUNT = "swim_likecount";
        public static final String SWIM_DISLIKECOUNT = "swim_dislikecount";
        public static final String SWIM_TITLE = "swim_title";
        public static final String SWIM_DESCRIPTION = "swim_description";
        public static final String SWIM_CHANNELTITLE = "swim_channeltitle";
        public static final String SWIM_FAVORATELIST = "swim_favoratelist";

        public static final Uri CONTENT_FAVORITE_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SWIM_FAVORITE).build();

        public static final String CONTENT_DIR_FAVORITE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_FAVORITE;

        public static final String CONTENT_ITEM_FAVORITE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+TABLE_SWIM_FAVORITE;

        public static Uri buildFavoriteUri(long id){
            return ContentUris.withAppendedId(CONTENT_FAVORITE_URI,id);
        }
    }
}
