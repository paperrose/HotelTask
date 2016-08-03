package com.artfonapps.hotelstask.views;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.artfonapps.hotelstask.R;
import com.artfonapps.hotelstask.adapters.ImageViewPagerAdapter;
import com.artfonapps.hotelstask.constants.Methods;
import com.artfonapps.hotelstask.constants.Strings;
import com.artfonapps.hotelstask.constants.Columns;
import com.artfonapps.hotelstask.db.models.Hotel;
import com.artfonapps.hotelstask.db.models.Image;
import com.artfonapps.hotelstask.network.BusProvider;
import com.artfonapps.hotelstask.network.Communicator;
import com.artfonapps.hotelstask.network.ErrorEvent;
import com.artfonapps.hotelstask.network.events.HotelLoadEvent;
import com.artfonapps.hotelstask.speedviewpager.SpeedViewPager;
import com.artfonapps.hotelstask.utils.PDManager;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HotelDescActivity extends AppCompatActivity {

    ProgressDialog pd;
    Hotel hotel;
    List<Image> images;
    ImageViewPagerAdapter vpAdapter;
    SpeedViewPager vp;

    public static final int DELAY = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_desc);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        final String hotelId = getIntent().getStringExtra(Columns.ID);
        //сначала вытаскиваем данные из БД, если есть - чтоб не было пустого экрана;
        hotel = (new Select().from(Hotel.class).where(Columns.ID + " = ?", hotelId).executeSingle());
        ((TextView) findViewById(R.id.hotelName)).setText(hotel.name);
        ((TextView) findViewById(R.id.hotelAddress)).setText(hotel.address);
        String [] rooms;
        if (hotel.suites_availability.isEmpty()) {
            rooms = new String[] {};
        } else {
            rooms = hotel.suites_availability.split(":");
        }

        ((TextView)findViewById(R.id.hotelFree)).setText(Strings.FREE_ROOMS + rooms.length);
        String stars = "";
        for (int j = 0; j < hotel.stars; j++) {
            stars += "*";
        }
        TextView starsTV = (TextView)findViewById(R.id.hotelStars);
        vp = (SpeedViewPager)findViewById(R.id.hotelImage);
        vp.setScrollSpeed(5.f);
        if (stars.isEmpty()) {
            starsTV.setVisibility(View.GONE);
        } else {
            starsTV.setVisibility(View.VISIBLE);
            starsTV.setText(stars);
        }

        pd = PDManager.showProgressDialog(HotelDescActivity.this);
        images = hotel.getImages();

        if (images == null || images.isEmpty()) {
            ContentValues cv = new ContentValues();
            cv.put(Columns.ID, hotel.id);
            Communicator.INSTANCE.communicate(Methods.HOTEL, cv);
        } else {
            pd.dismiss();
            uploadHotel();
        }
        setAutoChange();
    }


    Handler handler;
    int currentPage = 0;
    Runnable update;


    private void setAutoChange() {
        handler = new Handler();
        update = new Runnable() {
            public void run() {
                if (images == null || images.isEmpty() || vp == null) return;
                if (currentPage == images.size()) {
                    currentPage = 0;
                }
               // vp.beginFakeDrag();
                vp.setCurrentItem(currentPage++, true);
            }
        };


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, DELAY);
    }

    private void uploadHotel() {
        if (images == null || images.isEmpty()) return;
        vpAdapter = new ImageViewPagerAdapter(HotelDescActivity.this, images);
        vp.setAdapter(vpAdapter);
    }

    public void navigateClk(View v) {
        Intent intent = new Intent(HotelDescActivity.this, MapsActivity.class);
        intent.putExtra(Columns.LON, hotel.lon);
        intent.putExtra(Columns.LAT, hotel.lat);
        intent.putExtra(Columns.NAME, hotel.name);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }


    @Subscribe
    public void onHotelLoadEvent(HotelLoadEvent hotelsLoadEvent){
        images = hotelsLoadEvent.getHotel().images;
        uploadHotel();
        pd.dismiss();
    }


    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent){
        pd.dismiss();
        Picasso.with(HotelDescActivity.this)
                .load(R.drawable.no_image)
                .fit()
                .into((ImageView)findViewById(R.id.hotelImage));
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
