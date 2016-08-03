package com.artfonapps.hotelstask.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.artfonapps.hotelstask.R;
import com.artfonapps.hotelstask.adapters.HotelAdapter;
import com.artfonapps.hotelstask.constants.Methods;
import com.artfonapps.hotelstask.constants.Columns;
import com.artfonapps.hotelstask.constants.Orders;
import com.artfonapps.hotelstask.db.Helper;
import com.artfonapps.hotelstask.db.models.Hotel;
import com.artfonapps.hotelstask.network.BusProvider;
import com.artfonapps.hotelstask.network.Communicator;
import com.artfonapps.hotelstask.network.ErrorEvent;
import com.artfonapps.hotelstask.network.events.HotelsLoadEvent;
import com.artfonapps.hotelstask.utils.PDManager;
import com.squareup.otto.Subscribe;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvData;
    HotelAdapter scAdapter;
    private final static String TAG = "MainActivity";
    ProgressDialog pd;
    List<Hotel> hotels;
    SwipeRefreshLayout srl;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pd = PDManager.showProgressDialog(MainActivity.this);

        hotels = Helper.getHotelsInOrder(Columns.ID, Orders.ASC);
        scAdapter = new HotelAdapter(MainActivity.this, -1, hotels);

        lvData = (ListView) findViewById(R.id.hotelsList);
        lvData.setAdapter(scAdapter);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, HotelDescActivity.class);
                intent.putExtra(Columns.ID, hotels.get(position).id);
                startActivity(intent);
            }
        });


        if (hotels.isEmpty()) {
            Communicator.INSTANCE.communicate(Methods.HOTELS, null);
        } else {
            pd.dismiss();
        }

        srl = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Communicator.INSTANCE.communicate(Methods.HOTELS, null);
            }
        });

    }

    private void refreshHotels(List<Hotel> hotels) {
        this.hotels.clear();
        this.hotels.addAll(hotels);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(0).setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);

        if (id == R.id.sort_none) {
            refreshHotels(Helper.getHotelsInOrder(Columns.ID, Orders.ASC));
        } else if (id == R.id.sort_distance_up) {
            refreshHotels(Helper.getHotelsInOrder(Columns.DISTANCE, Orders.ASC));
        } else if (id == R.id.sort_distance_down) {
            refreshHotels(Helper.getHotelsInOrder(Columns.DISTANCE, Orders.DESC));
        } else if (id == R.id.sort_star_up) {
            refreshHotels(Helper.getHotelsInOrder(Columns.STARS, Orders.ASC));
        } else if (id == R.id.sort_star_down) {
            refreshHotels(Helper.getHotelsInOrder(Columns.STARS, Orders.DESC));
        }
        scAdapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Subscribe
    public void onHotelsLoadEvent(HotelsLoadEvent hotelsLoadEvent){
        refreshHotels(hotelsLoadEvent.getHotels());
        scAdapter.notifyDataSetChanged();
        srl.setRefreshing(false);
        pd.dismiss();

    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent){
        Toast.makeText(this, errorEvent.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

}
