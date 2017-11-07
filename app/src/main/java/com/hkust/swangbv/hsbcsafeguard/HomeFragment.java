package com.hkust.swangbv.hsbcsafeguard;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkust.swangbv.hsbcsafeguard.HttpUtility.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        tv = myView.findViewById(R.id.tv_fragment_home);

        new MyTask().execute();

        return myView;
    }


    private class MyTask extends AsyncTask<String, Integer, String> {
        static final String TAG = "LearningServer";

        @Override
        protected String doInBackground(String... params) {
            try {
                return HttpHelper.getAccount("swangbv");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject obj = new JSONObject(result);
                StringBuilder sb = new StringBuilder();
                sb.append("Hi "+obj.getString("name")+",Your current information: \n \n");
                sb.append("Name:  " + obj.getString("name")+"\n");
                sb.append("PhoneNumber:  " + obj.getString("phoneNumber")+"\n");
                sb.append("Email:  " + obj.getString("email")+"\n");
                sb.append("Address:  " + obj.getString("residentialAddress")+"\n");
                sb.append("Job:  " + obj.getString("job")+"\n");

                tv.setTextColor(Color.BLACK);
                tv.setTextSize(20);
                tv.setText(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }






}
