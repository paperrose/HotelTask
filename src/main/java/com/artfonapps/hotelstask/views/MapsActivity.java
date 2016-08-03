package com.artfonapps.hotelstask.views;

import android.support.v7.app.ActionBar;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.artfonapps.hotelstask.R;
import com.artfonapps.hotelstask.constants.Columns;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity {

    MapView mapView;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng current;// = -34, 151);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra(Columns.NAME));
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        map = mapFragment.getMap();
        if (map == null) {
          finish();
          return;
        }
        current = new LatLng(getIntent().getDoubleExtra(Columns.LAT, 0), getIntent().getDoubleExtra(Columns.LON, 0));

        final Marker marker = map.addMarker(new MarkerOptions().position(current).title(getIntent().getStringExtra(Columns.NAME)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
    }

    public void navigateClk(View v) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15), 1000, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
