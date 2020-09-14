package com.huawei.analyticskit.java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private HiAnalyticsWrapper hiAnalyticsWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        hiAnalyticsWrapper = new HiAnalyticsWrapper(this);
        hiAnalyticsWrapper.setUpUserId();

        final EditText etFavouriteSport = findViewById(R.id.etFavouriteSport);
        final Button btnSaveSettings = findViewById(R.id.btnSaveSettings);

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFavorSport = etFavouriteSport.getText().toString().trim();
                hiAnalyticsWrapper.setUserProfile(strFavorSport);
            }
        });
    }
}