package com.example.maishedule;

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

    //основная функция класса вызывающая остальные фцнкции
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

    //создание URL для запроса
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //установка соединения
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

    //получение из потока данных
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

    //преобразование json в структуру данных
    private static List<Group> extractFeatureFromJson(String groupsJSON) {
        if(TextUtils.isEmpty(groupsJSON)) {
            return null;
        }

        List<Group> groups = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(groupsJSON);

            JSONArray groupsArray = baseJsonResponse.getJSONArray("groups");

            for(int i = 0; i < groupsArray.length(); i++) {

                JSONObject currentObject = groupsArray.getJSONObject(i);

                JSONArray sheduleArray = currentObject.getJSONArray("schedule");

                List<Shedule> shedules = new ArrayList<>();

                String groupId = currentObject.getString("groupid");

                for(int j = 0; j < sheduleArray.length(); j++) {

                    currentObject = sheduleArray.getJSONObject(j);

                    JSONArray daysArray = currentObject.getJSONArray("days");

                    List<Days> days = new ArrayList<>();

                    int week = currentObject.getInt("week");

                    for(int k = 0; k < daysArray.length(); k++) {

                        currentObject = daysArray.getJSONObject(k);

                        JSONArray lessonsArray = currentObject.getJSONArray("lessons");

                        List<Lessons> lessons = new ArrayList<>();

                        String currentDay = currentObject.getString("day");

                        for(int l = 0; l < lessonsArray.length(); l++) {

                            currentObject = lessonsArray.getJSONObject(l);

                            String currentLesson = currentObject.getString("lesson");
                            String teacher = currentObject.getString("teacher");
                            String date = currentObject.getString("data");
                            String time = currentObject.getString("time");
                            String place = currentObject.getString("place");
                            Lessons lesson = new Lessons(currentLesson, teacher, date, time, place);
                            lessons.add(lesson);
                        }

                        Days day = new Days(currentDay, lessons);
                        days.add(day);
                    }

                    Shedule shedule = new Shedule(week, days);
                    shedules.add(shedule);
                }
                //Log.e("ПРОВЕРКА", groupId);
                Group group = new Group(groupId, shedules);
                groups.add(group);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the shedule JSON results" + groupsJSON, e);
        }
        return groups;
    }
}
