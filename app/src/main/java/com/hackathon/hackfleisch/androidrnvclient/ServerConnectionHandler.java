package com.example.johannes.myapplication;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by johannes on 16.04.16.
 * Reference: http://developer.android.com/reference/java/net/HttpURLConnection.html
 */
public class ServerConnectionHandler {

    private static final String TAG = "ServerConnectionHandler";
    private static final String SERVERURL = "http://192.168.0.1/";

    public void getStuff() {
        Log.v(TAG," getStuff ");
        URL url = null;
        try {
            url = new URL(SERVERURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
            urlConnection.disconnect();

        } catch (Exception e) {
            Log.v(TAG, "Exception occured ", e);
        }

    }
    public void readStream(InputStream in){




    }

    public void postStuff() {
        Log.v(TAG," postStuff ");
        URL url = null;

        try {
            url = new URL(SERVERURL);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);


            urlConnection.disconnect();

        } catch (Exception ex) {
            Log.v(TAG, "Exception occured ", ex);
        }
    }

}