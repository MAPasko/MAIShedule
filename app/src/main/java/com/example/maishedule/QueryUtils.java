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

    public static List<Group> fetchSheduleData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makehttprequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Problem making the HTTP request.",e);
        }

        List<Group> groups = extractFeatureFromJson(jsonResponse);

        return groups;
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

    private static List<Group> extractFeatureFromJson(String groupsJSON) {
        if(TextUtils.isEmpty(groupsJSON)) {
            return null;
        }

        List<Group> groups = new ArrayList<>();
        List<Shedule> shedules = new ArrayList<>();
        List<Days> days = new ArrayList<>();
        List<Lessons> lessons = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(groupsJSON);

            JSONArray groupsArray = baseJsonResponse.getJSONArray("groups");

            for(int i = 0; i < groupsArray.length(); i++) {
                JSONArray sheduleArray = baseJsonResponse.getJSONArray("shedule");

                JSONObject currentGroup = groupsArray.getJSONObject(i);

                JSONObject oneGroup = currentGroup.getJSONObject("groups");

                String groupId = oneGroup.getString("groupid");

                for(int j = 0; j < sheduleArray.length(); j++) {
                    JSONArray daysArray = baseJsonResponse.getJSONArray("days");

                    currentGroup = sheduleArray.getJSONObject(j);

                    oneGroup = currentGroup.getJSONObject("shedule");

                    int week = oneGroup.getInt("week");

                    for(int k = 0; k < daysArray.length(); k++) {
                        JSONArray lessonsArray = baseJsonResponse.getJSONArray("lessons");

                        currentGroup = daysArray.getJSONObject(k);

                        oneGroup = currentGroup.getJSONObject("days");

                        int currentDay = oneGroup.getInt("day");

                        for(int l = 0; l < lessonsArray.length(); l++) {

                            currentGroup = lessonsArray.getJSONObject(l);

                            oneGroup = currentGroup.getJSONObject("lessons");

                            String currentLesson = oneGroup.getString("lesson");
                            String teacher = oneGroup.getString("teacher");
                            String time = oneGroup.getString("time");
                            String place = oneGroup.getString("place");

                            Lessons lesson = new Lessons(currentLesson, teacher, time, place);
                            lessons.add(lesson);
                        }

                        Days day = new Days(currentDay, (Lessons) lessons);
                        days.add(day);
                    }

                    Shedule shedule = new Shedule(week, (Days) days);
                    shedules.add(shedule);
                }

                Group group = new Group(groupId, (Shedule) shedules);
                groups.add(group);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the shedule JSON results" + groupsJSON, e);
        }
        return groups;
    }
}
