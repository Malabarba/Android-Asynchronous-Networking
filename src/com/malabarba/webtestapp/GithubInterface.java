package com.malabarba.webtestapp;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context; // 
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

public class GithubInterface {

    public final static String apiURL = "https://api.github.com/";

    // This is the method that is called when the submit button is clicked
    static public ArrayAdapter<String> fetchRepos(Context context, String username) {
        if( username != null && !username.isEmpty()) {
            String urlString = apiURL + "users/" + username + "/repos";

            ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

            CallAPI call = new CallAPI(adapter);
            call.execute(urlString);

            return adapter;
        } else
            return null;
    }

    static private class CallAPI extends AsyncTask<String, Void, ArrayList<String>> {
        private ArrayAdapter<String> adapter;

        public CallAPI(ArrayAdapter<String> adapter) {
            super();
            this.adapter = adapter;
        }

        static String streamToString(java.io.InputStream is) {
            java.util.Scanner s =
                new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            String urlString=params[0]; // URL to call
            ArrayList<String> resultList = new ArrayList<String>();
            String result = null;

            // HTTP Get
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                result = streamToString(urlConnection.getInputStream());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                resultList.add(e.getMessage());
            }

            // Parse JSON
            try {
                JSONArray array = new JSONArray(result);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    resultList.add(object.getString("full_name"));
                }
            } catch (Exception e) {
                resultList.add(e.getMessage());
            }
            return resultList;
        }

        private int j = 0;
        
        protected void onPostExecute(ArrayList<String> resultList) {
            adapter.addAll(resultList);
            adapter.notifyDataSetChanged();
        }
    }
}
