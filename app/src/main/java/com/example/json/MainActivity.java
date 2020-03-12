package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static class DownloadCoordinates extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection connection;
            StringBuilder parsed = new StringBuilder();

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    parsed.append(current);
                    data = reader.read();
                }
                return parsed.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class DownloadWeather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection connection;
            StringBuilder parsed = new StringBuilder();

            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    parsed.append(current);
                    data = reader.read();
                }
                return parsed.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] coordinates = getCoordinates("Bihar");
        getWeather(coordinates);
    }

    public static String[] getCoordinates(String query) {
        DownloadCoordinates newTask = new DownloadCoordinates();
        String geocodeApiResponse = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + query + ".json?access_token=pk.eyJ1IjoidHVyYm9uYXRlcjYyIiwiYSI6ImNrMGR2cmNmbzBiamYzYm8yMWh5czc1cXUifQ.p2Cm5XjCyEQAQX_o7YU5pQ";
        try {
            String geocodeInfo = newTask.execute(geocodeApiResponse).get();
            JSONObject geocodeJson = new JSONObject(geocodeInfo);
            String features = geocodeJson.getString("features");
            JSONArray featuresArray = new JSONArray(features);
            JSONObject jsonPart = featuresArray.getJSONObject(0);
            String lastString = jsonPart.getString("center");
            JSONArray jsonArray = new JSONArray(lastString);
            return new String[]{jsonArray.get(0).toString(), jsonArray.get(1).toString()};
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getWeather(String[] coordinates){
        DownloadWeather newWeather = new DownloadWeather();
        String latitude, longitude;
        longitude = coordinates[0];
        latitude = coordinates[1];
        try {
            String weatherAPIResponse = newWeather.execute(String.format("https://api.darksky.net/forecast/b0422226b6861e4c519e5a9de9945162/%s,%s?units=si", latitude, longitude)).get();
            Log.i("dekhi", weatherAPIResponse);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

