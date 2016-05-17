package lf.com.br.gasstations;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by Fernando on 18/04/2016.
 */
public class Configurador implements GoogleApiClient.ConnectionCallbacks {

    private RefreshGeoLocation refreshLocation;

    public Configurador(RefreshGeoLocation refreshLocation) {
        this.refreshLocation = refreshLocation;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        request.setSmallestDisplacement(50);

        refreshLocation.inicia(request);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
