package lf.com.br.gasstations;


import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import lf.com.br.gasstations.model.Posto;

/**
 * Created by Fernando on 17/04/2016.
 */
public class MapFragment extends SupportMapFragment {

    private List<Posto> listaPostos;
    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaPostos = (List<Posto>) getActivity().getIntent().getSerializableExtra("posto");

    }


    @Override
    public void onResume() {
        super.onResume();

        dialog = ProgressDialog.show(getActivity(),"Aguarde...", "Atualizando localização.");

        Localizador localizador = new Localizador(getActivity());

        // centraliza(local);


        for (Posto p : listaPostos) {

            Log.i("LAT", "Nome: "+String.valueOf(p.latitude));
            Log.i("LONG", "Nome: "+String.valueOf(p.longitude));
            LatLng coordenada = new LatLng(Double.valueOf(p.latitude), Double.valueOf(p.longitude));

            if (coordenada != null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(coordenada).title(p.nome).snippet(p.endereco).getIcon();
                getMap().addMarker(markerOptions);
            }
        }

        RefreshGeoLocation refreshGeoLocation = new RefreshGeoLocation(getActivity(), this);

    }



    public void centraliza(LatLng local) {

        GoogleMap mapa = this.getMap();
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 15));
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            return;
        }
        mapa.setMyLocationEnabled(true);

        if (local!=null) {
            dialog.dismiss();
        }


    }
}
