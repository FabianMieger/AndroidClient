package com.hackathon.hackfleisch.androidrnvclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.hackathon.hackfleisch.androidrnvclient.com.hackathon.hackfleisch.androidrnvclient.location.GoogleAPIHelper;
import com.hackathon.hackfleisch.androidrnvclient.com.hackathon.hackfleisch.androidrnvclient.location.LocationHandler;

import java.io.IOException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;
    //private static final String SERVERURL = "http://192.168.0.1/";
    private static final String SERVERURL = "https://github.com/square/okhttp";
    private static final String TAG ="MainActivity";

    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public enum Rqmodes {
        GET_NEAREST_STATION,
        POST_MY_STATS
    }


    public void run(final Rqmodes mode) throws Exception {

        Request request = null;
        if (mode == Rqmodes.GET_NEAREST_STATION){
            Log.e(TAG, "Using " + Rqmodes.GET_NEAREST_STATION);

            request = new Request.Builder()
                    .url("http://publicobject.com/helloworld.txt")
                    .build();
         } else if(mode == Rqmodes.POST_MY_STATS) {
            Log.e(TAG, "Using " + Rqmodes.POST_MY_STATS);

            RequestBody body = RequestBody.create(JSON, LocationHandler.getStationAndDepaturesData().toString());
            request = new Request.Builder()
                    .url(new URL("http", "10.10.1.133", 80, "/api/get_station_and_departures"))
                    //.url("http://10.10.1.133:8080/api/get_station_and_departures")
                    .post(body)
                    .build();
         }else {
            Log.e(TAG," NOT DEFINED REQUEST MODE ");
         }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Log.v(TAG, "Hallo");

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Log.v(TAG, response.body().string());


                if (mode == Rqmodes.GET_NEAREST_STATION){
                      //DO HANDLING HERE LATER
                }else if(mode == Rqmodes.POST_MY_STATS){
                    //DO HANDLING HERE LATER
                }else {
                    Log.e(TAG," NOT DEFINED REQUEST MODE ");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.instance = this;
        try {
            //run(Rqmodes.GET_NEAREST_STATION);
        } catch (Exception e) {
            Log.e(TAG,e.toString());
        }
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        new GoogleAPIHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleApiClient client = GoogleAPIHelper.getInstance().getGoogleApiClient();
        if (client != null) {
            client.connect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
