package com.hackathon.hackfleisch.androidrnvclient.com.hackathon.hackfleisch.androidrnvclient.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by ubuntu on 16.04.16.
 */
public class GoogleAPIHelper implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static GoogleAPIHelper instance;

    public static GoogleAPIHelper getInstance() {
        return instance;
    }

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;

    public GoogleAPIHelper(Context context) {
        this.context = context;

        GoogleAPIHelper.instance = this;
    }

    public GoogleApiClient getGoogleApiClient() {
        //Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return mGoogleApiClient;
    }

    public LocationRequest getLocationRequest() {
        if (mLastLocation == null) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            mLocationRequest.setFastestInterval(5000); // Alle 5 Sekunden
            mLocationRequest.setInterval(10000); // Alle 10 Sekunden
            mLocationRequest.setMaxWaitTime(20000); // Max 20 Sekunden
        }
        return mLocationRequest;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            LocationHandler.getLocationHelper().handleLocationUpdate(LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient()));
            LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), getLocationRequest(), this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    @Override
    public void onConnectionSuspended(int reason) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LocationHandler.getLocationHelper().handleLocationUpdate(location);
    }
}
