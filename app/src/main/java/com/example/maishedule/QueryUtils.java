package com.example.maishedule;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Shedule> fetchSheduleData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makehttprequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Problem making the HTTP request.",e);
        }

        List<Shedule> shedules = extractFeatureFromJson(jsonResponse);

        return shedules;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makehttprequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url==null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the shedule JSON results.", e);
        } finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Shedule> extractFeatureFromJson(String sheduleJSON) {
        if(TextUtils.isEmpty(sheduleJSON)) {
            return null;
        }

        List<Shedule> shedules = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(sheduleJSON);

            JSONArray shedulesArray = baseJsonResponse.getJSONArray("schedule");

            for(int i = 0; i < shedulesArray.length(); i++) {
                JSONObject currentShedule = shedulesArray.getJSONObject(i);

                JSONObject oneDay = currentShedule.getJSONObject("days");

                String lesson = oneDay.getString("lesson");
                String teacher = oneDay.getString("teacher");
                String data = oneDay.getString("data");
                String time = oneDay.getString("time");
                String place = oneDay.getString("place");

                Shedule shedule = new Shedule(lesson, teacher, data, time, place, i);
                shedules.add(shedule);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the shedule JSON results" + sheduleJSON, e);
        }
        return shedules;
    }
}
