/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.SunshineDateUtils;
import com.example.android.sunshine.utilities.SunshineWeatherUtils;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /*
     * In this Activity, you can share the selected day's forecast. No social sharing is complete
     * without using a hashtag. #BeTogetherNotTheSame
     */
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private String[] PROJECTION = {
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_DEGREES,
    };

    private final int DATE_INDEX = 0;
    private final int MAX_TEMP_INDEX = 1;
    private final int MIN_TEMP_INDEX = 2;
    private final int WEATHER_ID_INDEX = 3;
    private final int HUMIDITY_INDEX = 4;
    private final int PRESSURE_INDEX = 5;
    private final int WIND_INDEX = 6;
    private final int DEGREES_INDEX = 7;

    private static final int ID_DETAIL_LOADER = 353;

    /* A summary of the forecast that can be shared by clicking the share button in the ActionBar */
    private String mForecastSummary;


    private Uri mUri;


    private TextView mWeatherDate;
    private TextView mWeatherDescription;
    private TextView mWeatherHigh;
    private TextView mWeatherLow;
    private TextView mWeatherHumidity;
    private TextView mWeatherWind;
    private TextView mWeatherPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWeatherDate = (TextView) findViewById(R.id.tv_day_date);

        mWeatherDescription = (TextView) findViewById(R.id.tv_weather_description);

        mWeatherHigh = (TextView) findViewById(R.id.tv_high_temp);

        mWeatherLow = (TextView) findViewById(R.id.tv_low_temp);

        mWeatherHumidity = (TextView) findViewById(R.id.tv_humidity);

        mWeatherWind = (TextView) findViewById(R.id.tv_wind);

        mWeatherPressure = (TextView) findViewById(R.id.tv_pressure);

        mUri = getIntent().getData();

        if (mUri == null) throw new NullPointerException();

        Log.d(this.getClass().getSimpleName(), mUri.toString());
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER,null,this);
    }

    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu. Android will
     * automatically handle clicks on the "up" button for us so long as we have specified
     * DetailActivity's parent Activity in the AndroidManifest.
     *
     * @param item The menu item that was selected by the user
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Get the ID of the clicked item */
        int id = item.getItemId();

        /* Settings menu item clicked */
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        /* Share menu item clicked */
        if (id == R.id.action_share) {
            Intent shareIntent = createShareForecastIntent();
            startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Uses the ShareCompat Intent builder to create our Forecast intent for sharing.  All we need
     * to do is set the type, text and the NEW_DOCUMENT flag so it treats our share as a new task.
     * See: http://developer.android.com/guide/components/tasks-and-back-stack.html for more info.
     *
     * @return the Intent to use to share our weather forecast
     */
    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecastSummary + FORECAST_SHARE_HASHTAG)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, @Nullable Bundle bundle) {
        switch (loaderId) {
            case ID_DETAIL_LOADER:
                return new CursorLoader(this,
                        mUri,
                        PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader not implemented");
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        boolean validData = false;
        if(cursor != null && cursor.moveToFirst()){
            validData = true;
        }

        if(!validData) return;

        long date = cursor.getLong(DATE_INDEX);
        String dateText = SunshineDateUtils.getFriendlyDateString(this,date, true);
        mWeatherDate.setText(dateText);

        int weatherId = cursor.getInt(WEATHER_ID_INDEX);
        String description = SunshineWeatherUtils.getStringForWeatherCondition(this, weatherId);
        mWeatherDescription.setText(description);

        double high = cursor.getDouble(MAX_TEMP_INDEX);
        String highString = SunshineWeatherUtils.formatTemperature(this, high);
        mWeatherHigh.setText(highString);

        double low = cursor.getDouble(MIN_TEMP_INDEX);
        String minString = SunshineWeatherUtils.formatTemperature(this, low);
        mWeatherLow.setText(minString);

        float humidity = cursor.getFloat(HUMIDITY_INDEX);
        String humidityString = getString(R.string.format_humidity, humidity);
        mWeatherHumidity.setText(humidityString);

        float wind = cursor.getFloat(WIND_INDEX);
        float windDirection = cursor.getFloat(DEGREES_INDEX);
        String windString = SunshineWeatherUtils.getFormattedWind(this, wind, windDirection);
        mWeatherWind.setText(windString);

        float pressure = cursor.getFloat(PRESSURE_INDEX);
        String pressureString = getString(R.string.format_pressure, pressure);
        mWeatherPressure.setText(pressureString);

        mForecastSummary = String.format("%s - %s - %s/%s",
                dateText, description, highString, minString);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

}