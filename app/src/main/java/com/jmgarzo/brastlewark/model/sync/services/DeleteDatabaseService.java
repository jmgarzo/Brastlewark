package com.jmgarzo.brastlewark.model.sync.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jmgarzo.brastlewark.model.sync.BrastlewarkSyncTask;

/**
 * Created by jmgarzo on 24/02/18.
 */

public class DeleteDatabaseService extends IntentService {

    public DeleteDatabaseService() {
        super("DeleteDatabaseService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        BrastlewarkSyncTask.deleteDb(this);
    }
}
