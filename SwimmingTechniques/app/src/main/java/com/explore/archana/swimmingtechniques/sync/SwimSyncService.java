package com.explore.archana.swimmingtechniques.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SwimSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static SwimTechniqueSyncAdapter sSwimSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SwimSyncService", "onCreate - SwimSyncService");
        synchronized (sSyncAdapterLock) {
            if (sSwimSyncAdapter == null) {
                sSwimSyncAdapter = new SwimTechniqueSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSwimSyncAdapter.getSyncAdapterBinder();
    }
}