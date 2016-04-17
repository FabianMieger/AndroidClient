package com.hackathon.hackfleisch.androidrnvclient.com.hackathon.hackfleisch.androidrnvclient.location;

import android.location.Location;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.location.LocationRequest;
import com.hackathon.hackfleisch.androidrnvclient.MainActivity;
import com.hackathon.hackfleisch.androidrnvclient.NetworkStuff;
import com.hackathon.hackfleisch.androidrnvclient.R;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by ubuntu on 16.04.16.
 */
public class LocationHandler {

    private static LocationHandler instance;

    public static LocationHandler getLocationHelper() {
        if (LocationHandler.instance == null) {
            LocationHandler.instance = new LocationHandler();
        }
        return LocationHandler.instance;
    }

    private Location currentLocation;
    private Collection<Location> locations = new ArrayList<>();

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void handleLocationUpdate(Location updatedLocation) {
        this.currentLocation = updatedLocation;
        this.locations.add(updatedLocation);
        EditText text = (EditText) MainActivity.instance.findViewById(R.id.edit_message);
        if (updatedLocation == null) {
            return;
        }
        try {
            //MainActivity.instance.run(MainActivity.Rqmodes.POST_MY_STATS);
            Log.v("BLA", NetworkStuff.getResponse());
            text.setText(updatedLocation.getLatitude() + " / " + updatedLocation.getLongitude() +
                    " / " + DateFormat.getTimeInstance().format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getStationAndDepaturesData() throws Exception {
        Location loc = getLocationHelper().getCurrentLocation();

        JSONObject json = new JSONObject();
        JSONObject position = new JSONObject();
        position.put("latitude", String.valueOf(loc.getLatitude()));
        position.put("longitude", String.valueOf(loc.getLatitude()));
        json.put("position", position);

        return json;
    }

}
