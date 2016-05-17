package lf.com.br.gasstations;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.drive.internal.StringListResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lf.com.br.gasstations.model.Posto;

/**
 * Created by Fernando on 17/04/2016.
 */
public class MapFragment extends SupportMapFragment {


    private List<Posto> listaPostos;
    private ProgressDialog dialog;
    private RadioGroup radioGroup;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaPostos = (List<Posto>) getActivity().getIntent().getSerializableExtra("posto");

        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.radioAll: Log.i("RADIO","ALL");
                        pegaAll();
                        break;
                    case R.id.radioGAS: Log.i("RADIO","GAS");
                        pegaApenasGAS();
                        break;
                    case R.id.radioAlcool: Log.i("RADIO","ALCOOL");
                        pegaApenasAlcool();
                        break;
                    case R.id.radioGNV: Log.i("RADIO","GNV");
                        pegaApenasGNV();
                        break;
                }
            }
        });
    }

    private void pegaAll() {
        getMap().clear();
        adcMarker(listaPostos);

    }

    private void pegaApenasGNV() {
        List<Posto> listaGNV = new ArrayList<Posto>();
        for (Posto p : listaPostos){
            if (Double.valueOf(p.gnv) > 0.0){
                listaGNV.add(p);
            }
        }
        getMap().clear();
        adcMarker(listaGNV);
    }

    private void pegaApenasAlcool() {
        List<Posto> listaAlcool = new ArrayList<Posto>();
        for (Posto p : listaPostos){
            if (Double.valueOf(p.alcool) > 0.0){
                listaAlcool.add(p);
            }
        }
        getMap().clear();
        adcMarker(listaAlcool);
    }

    private void pegaApenasGAS() {
        List<Posto> listaGAS = new ArrayList<Posto>();
        for (Posto p : listaPostos){
            if (Double.valueOf(p.gasolina) > 0.0){
                listaGAS.add(p);
            }
        }
        getMap().clear();
        adcMarker(listaGAS);
    }


    @Override
    public void onResume() {
        super.onResume();

        dialog = ProgressDialog.show(getActivity(),"Aguarde...", "Atualizando localização.");

        adcMarker(listaPostos);


        RefreshGeoLocation refreshGeoLocation = new RefreshGeoLocation(getActivity(), this);

    }

    private void adcMarker(List<Posto> lPosto) {
        for (Posto p : lPosto) {

            LatLng coordenada = new LatLng(Double.valueOf(p.latitude), Double.valueOf(p.longitude));

            if (coordenada != null) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(coordenada).title(p.nome).snippet(p.endereco).draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_gasstation_dra));
                getMap().addMarker(markerOptions);
            }
        }
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
