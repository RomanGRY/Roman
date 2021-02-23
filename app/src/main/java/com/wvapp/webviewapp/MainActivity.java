package com.wvapp.webviewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.onesignal.OneSignal;
import com.wvapp.webviewapp.Fragments.FormFragment;
import com.wvapp.webviewapp.Fragments.VisitorsListFragment;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;


public class MainActivity extends AppCompatActivity {

    private static final String ONESIGNAL_APP_ID = "dac9d965-96ca-41de-858c-04c408152145";
    private static final String AppMetrica_APP_ID = "4c044d5f-ff8c-45e9-b7db-bf85c2ffd00a";
    private InstallReferrerClient referrerClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referrerClient = InstallReferrerClient.newBuilder(this).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
            }
        });

            YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(AppMetrica_APP_ID).build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(getApplication());

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done_all);
        getSupportActionBar().setTitle("All Visitors");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FormFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        referrerClient.endConnection();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.see_all_visitors:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VisitorsListFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}