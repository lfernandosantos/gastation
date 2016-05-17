package lf.com.br.gasstations;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;


public class MapsActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FragmentTransaction tx =  getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame_mapa, new MapFragment());
        tx.commit();

    }



}
