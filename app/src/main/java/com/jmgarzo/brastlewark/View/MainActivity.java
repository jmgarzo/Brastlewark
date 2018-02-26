package com.jmgarzo.brastlewark.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jmgarzo.brastlewark.R;

import static com.jmgarzo.brastlewark.model.sync.BrastlewarkSyncTask.initialize;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize(this);
    }
}
