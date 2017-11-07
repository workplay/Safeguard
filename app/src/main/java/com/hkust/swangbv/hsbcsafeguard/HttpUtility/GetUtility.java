package com.hkust.swangbv.hsbcsafeguard.HttpUtility;


import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 22/6/2017.
 */
public class GetUtility extends HttpUtility {

    private String requestURL;

    public GetUtility(String requestURL) throws IOException {
        this.requestURL = requestURL;
    }

    public String getResponse() throws IOException {

        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();


        return super.getResponse();
    }
}
