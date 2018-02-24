package com.jmgarzo.brastlewark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jmgarzo.brastlewark.model.sync.services.DeleteDatabaseService;
import com.jmgarzo.brastlewark.model.sync.services.SyncInhabitantService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentDeleteDatabaseService = new Intent(this, DeleteDatabaseService.class);
        startService(intentDeleteDatabaseService);

        Intent intentSyncInhabitantsService = new Intent(this, SyncInhabitantService.class);
        startService(intentSyncInhabitantsService);
    }
}
