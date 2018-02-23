package com.jmgarzo.brastlewark.model.sync.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jmgarzo.brastlewark.model.sync.BrastlewarkSyncTask;

/**
 * Created by jmgarzo on 2/23/2018.
 */

public class SyncInhabitantService extends IntentService {

    public SyncInhabitantService() {
        super("SyncInhabitantService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        BrastlewarkSyncTask.syncInhabitants(this);

    }
}
