package lf.com.br.gasstations;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by Fernando on 18/04/2016.
 */
public class Configura implements GoogleApiClient.ConnectionCallbacks {

    private MainActivity mainActivity;

    public Configura(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setInterval(2000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setSmallestDisplacement(50);

        mainActivity.iniciar(request);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
