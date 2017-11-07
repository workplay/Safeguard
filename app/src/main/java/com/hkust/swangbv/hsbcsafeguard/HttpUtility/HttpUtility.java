package com.hkust.swangbv.hsbcsafeguard.HttpUtility;


import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by admin on 28/6/2017.
 */
public abstract class HttpUtility {
    protected HttpURLConnection httpConn;

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


    protected String getResponse() throws IOException {

        int status = httpConn.getResponseCode();

        String response = "";

        if(status == HttpURLConnection.HTTP_OK){
            response = readStream(httpConn.getInputStream());
        } else {
            return httpConn.getResponseMessage();
        }

        httpConn.disconnect();
        return response;
    }

    protected byte[] getBinaryResponse() throws IOException {

        int status = httpConn.getResponseCode();

        InputStream is;

        if (status >= 200 && status < 400) {
            is = httpConn.getInputStream();
        } else {
            is =  httpConn.getErrorStream();
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[4096];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        httpConn.disconnect();

        return buffer.toByteArray();
    }
}
