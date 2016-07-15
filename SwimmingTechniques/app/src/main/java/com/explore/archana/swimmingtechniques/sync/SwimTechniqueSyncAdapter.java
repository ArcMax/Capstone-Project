package com.explore.archana.swimmingtechniques.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.connector.YoutubeConnector;
import com.explore.archana.swimmingtechniques.data.YoutubeContract;
import com.explore.archana.swimmingtechniques.model.SearchedVideoList;

import java.util.List;

public class SwimTechniqueSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String LOG_TAG = SwimTechniqueSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;


    public static void initializeSyncAdapter(Context context) {
        Log.d(LOG_TAG, "initializeSyncAdapter");
        getSyncAccount(context);
    }

    public SwimTechniqueSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        Log.d(LOG_TAG, "SwimTechniqueSyncAdapter");
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");

        YoutubeConnector connector = new YoutubeConnector(getContext());
        List<SearchedVideoList> freestyle = connector.searchList(getContext().getResources().getString(R.string.search_freestyle));
        List<SearchedVideoList> breathing = connector.searchList(getContext().getResources().getString(R.string.search_breathing));
        List<SearchedVideoList> backstroke = connector.searchList(getContext().getResources().getString(R.string.search_backstroke));
        List<SearchedVideoList> butterfly = connector.searchList(getContext().getResources().getString(R.string.search_butterfly));
        Log.d(LOG_TAG, "on perform sync free" + freestyle.get(1).getTitle());
        Log.d(LOG_TAG, "on perform sync breath" + breathing.get(1).getTitle());
        Log.d(LOG_TAG, "on perform sync backstroke" + backstroke.get(1).getTitle());
        Log.d(LOG_TAG, "on perform sync butterfly" + butterfly.get(1).getTitle());

        if (freestyle != null) {
            ContentValues[] swimValuesArr = new ContentValues[freestyle.size()];
            for (int i = 0; i < freestyle.size(); i++) {
                swimValuesArr[i] = new ContentValues();
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_ID, freestyle.get(i).getId());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_THUMBNAIL, freestyle.get(i).getThumbnailURL());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DURATION, freestyle.get(i).getDuration());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_VIEWCOUNT, freestyle.get(i).getViewCount());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_LIKECOUNT, freestyle.get(i).getLikesCount());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DISLIKECOUNT, freestyle.get(i).getDislikeCount());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_TITLE, freestyle.get(i).getTitle());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_DESCRIPTION, freestyle.get(i).getDescription());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_CHANNELTITLE, freestyle.get(i).getChannelTitle());
                swimValuesArr[i].put(YoutubeContract.YoutubeSwimmingTechniques.SWIM_FAVORATELIST, freestyle.get(i).getFavorateList());
            }
            Log.d(LOG_TAG, "insert swim data searchedVideoLists" + freestyle.get(1).getTitle());
            getContext().getContentResolver().bulkInsert(YoutubeContract.YoutubeSwimmingTechniques.CONTENT_URI, swimValuesArr);
        }

        if (breathing != null) {
            ContentValues[] swimBreathArr = new ContentValues[breathing.size()];
            for (int i = 0; i < breathing.size(); i++) {
                swimBreathArr[i] = new ContentValues();
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_ID, breathing.get(i).getId());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_THUMBNAIL, breathing.get(i).getThumbnailURL());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_DURATION, breathing.get(i).getDuration());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_VIEWCOUNT, breathing.get(i).getViewCount());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_LIKECOUNT, breathing.get(i).getLikesCount());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_DISLIKECOUNT, breathing.get(i).getDislikeCount());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_TITLE, breathing.get(i).getTitle());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_DESCRIPTION, breathing.get(i).getDescription());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_CHANNELTITLE, breathing.get(i).getChannelTitle());
                swimBreathArr[i].put(YoutubeContract.Breathing.SWIM_FAVORATELIST, breathing.get(i).getFavorateList());
            }
            Log.d(LOG_TAG, "insert breath data searchedVideoLists" + breathing.get(1).getTitle());
            getContext().getContentResolver().bulkInsert(YoutubeContract.Breathing.CONTENT_BREATH_URI, swimBreathArr);
        }


        if (backstroke != null) {
            ContentValues[] swimBackArr = new ContentValues[backstroke.size()];
            for (int i = 0; i < backstroke.size(); i++) {
                swimBackArr[i] = new ContentValues();
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_ID, backstroke.get(i).getId());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_THUMBNAIL, backstroke.get(i).getThumbnailURL());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_DURATION, backstroke.get(i).getDuration());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_VIEWCOUNT, backstroke.get(i).getViewCount());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_LIKECOUNT, backstroke.get(i).getLikesCount());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_DISLIKECOUNT, backstroke.get(i).getDislikeCount());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_TITLE, backstroke.get(i).getTitle());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_DESCRIPTION, backstroke.get(i).getDescription());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_CHANNELTITLE, backstroke.get(i).getChannelTitle());
                swimBackArr[i].put(YoutubeContract.Backstroke.SWIM_FAVORATELIST, backstroke.get(i).getFavorateList());
            }
            Log.d(LOG_TAG, "insert backstroke data searchedVideoLists" + backstroke.get(1).getTitle());
            getContext().getContentResolver().bulkInsert(YoutubeContract.Backstroke.CONTENT_BACKSTROKE_URI, swimBackArr);
        }


        if (butterfly != null) {
            ContentValues[] swimButterArr = new ContentValues[butterfly.size()];
            for (int i = 0; i < butterfly.size(); i++) {
                swimButterArr[i] = new ContentValues();
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_ID, butterfly.get(i).getId());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_THUMBNAIL, butterfly.get(i).getThumbnailURL());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_DURATION, butterfly.get(i).getDuration());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_VIEWCOUNT, butterfly.get(i).getViewCount());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_LIKECOUNT, butterfly.get(i).getLikesCount());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_DISLIKECOUNT, butterfly.get(i).getDislikeCount());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_TITLE, butterfly.get(i).getTitle());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_DESCRIPTION, butterfly.get(i).getDescription());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_CHANNELTITLE, butterfly.get(i).getChannelTitle());
                swimButterArr[i].put(YoutubeContract.Butterfly.SWIM_FAVORATELIST, butterfly.get(i).getFavorateList());
            }
            Log.d(LOG_TAG, "insert butterfly data searchedVideoLists" + butterfly.get(1).getTitle());
            getContext().getContentResolver().bulkInsert(YoutubeContract.Butterfly.CONTENT_BUTTERFLY_URI, swimButterArr);
        }

    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Log.d(LOG_TAG, "configurePeriodicSync");
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(getSyncAccount(context), authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(getSyncAccount(context), authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Log.d(LOG_TAG, "syncImmediately");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        Log.d(LOG_TAG, "getSyncAccount");
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        Log.d(LOG_TAG, "onAccountCreated");
        /*
         * Since we've created an account
         */
        SwimTechniqueSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }
}