package com.example.android.appprealpha;

/**
 * Created by Pavlo on 24-Apr-18.
 */
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;


public class LocationServiceSingleton implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{
    private static LocationServiceSingleton instance;


    private LocationServiceSingleton() {

    }

    public static LocationServiceSingleton getInstance(String value){
        if(instance==null){
            instance = new LocationServiceSingleton();
        }
        return instance;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
