package com.faisalnazir.project;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisalnazir.project.Models.Articles;
import com.faisalnazir.project.Models.Headlines;
import com.google.android.gms.ads.AdView;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    NotificationHandler notificationHandler;
    private static final String ONESIGNAL_APP_ID = "7af227c6-25ed-426b-a82d-e16b9231756f";
    AdView mAdView;
    final String API_KEY = "34ae0b8e5b4f4ca6bdacd824878e08a8";
    Adapter adapter;
    public String c;
    List<Articles> articles = new ArrayList<>();
    Context context;

    //Spinner spCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        Spinner spinner = findViewById(R.id.spCountry);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.country, R.layout.color_spinner);
        adapter1.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(MainActivity.this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ;
        c = getUserCountry(this);
        Toast.makeText(MainActivity.this, c, Toast.LENGTH_SHORT).show();

        retrieveJson(c, API_KEY);

    }

    public void retrieveJson(String country, String apiKey) {


        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);


        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    //swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                //swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        //Toast.makeText(MainActivity.this, country, Toast.LENGTH_SHORT).show();

        return country.toLowerCase();
    }


    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            } else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }

        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (i == 0)
            c = "in";
        if (i == 1)
            c = "us";
        if (i == 2)
            c = "gb";
        if (i == 3)
            c = "au";
        if (i == 4)
            c = "ca";
        if (i == 5)
            c = "nz";
        if (i == 6)
            c = "ae";
        if (i == 7)
            c = "ru";
        if (i == 8)
            c = "cn";
        if (i == 9)
            c = "ch";
        if (i == 10)
            c = "sa";
        if (i == 11)
            c = "de";
        if (i == 12)
            c = "za";
        if (i == 13)
            c = "jp";
        if (i == 14)
            c = "tr";
        retrieveJson(c, API_KEY);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        c = getUserCountry(this);
    }
}


