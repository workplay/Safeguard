package com.hkust.swangbv.hsbcsafeguard;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hkust.swangbv.hsbcsafeguard.HttpUtility.GetUtility;
import com.hkust.swangbv.hsbcsafeguard.HttpUtility.HttpHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LearningServer extends AppCompatActivity {

    private static String domain = "10.0.2.2:10007";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_server);

        new MyTask().execute("http://10.0.2.2:10007/api/account/getAccounts");



    }


    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        static final String TAG = "LearningServer";

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("ddd",HttpHelper.getAccounts());
            } catch (IOException e) {
                e.printStackTrace();
            }


/*
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    String server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

*/
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, "onPostExecute(Result result) called");
        }
    }



}
