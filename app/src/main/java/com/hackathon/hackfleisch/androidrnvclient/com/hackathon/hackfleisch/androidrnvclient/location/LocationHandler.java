package com.hackathon.hackfleisch.androidrnvclient.com.hackathon.hackfleisch.androidrnvclient.location;

import android.location.Location;
import android.widget.EditText;

import com.hackathon.hackfleisch.androidrnvclient.MainActivity;
import com.hackathon.hackfleisch.androidrnvclient.R;

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
            text.setText(updatedLocation.getLatitude() + " / " + updatedLocation.getLongitude() +
                    " / " + DateFormat.getTimeInstance().format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
