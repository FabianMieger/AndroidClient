package com.hackathon.hackfleisch.androidrnvclient.com.hackathon.hackfleisch.androidrnvclient.location;

/**
 * Created by ubuntu on 16.04.16.
 */
public class LocationHelper {

    private static LocationHelper instance;

    public static LocationHelper getLocationHelper() {
        if (LocationHelper.instance == null) {
            LocationHelper.instance = new LocationHelper();
        }
        return LocationHelper.instance;
    }

    public

}
