package com.artfonapps.hotelstask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.artfonapps.hotelstask.R;
import com.artfonapps.hotelstask.constants.Strings;
import com.artfonapps.hotelstask.db.models.Hotel;

import java.util.List;

public class HotelAdapter extends ArrayAdapter<Hotel> {

    private List<Hotel> objects;
    private Context context;
    LayoutInflater inflater;

    public HotelAdapter(Context context, int resource, List<Hotel> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    public void refresh(List<Hotel> objects)
    {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @Override
    public Hotel getItem(int i) {
        return objects.get(i);
    }


    public int getItemIndex(Hotel object) {
        return objects.indexOf(object);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.hotel_item, viewGroup, false);
        }
        Hotel hotel = objects.get(i);
        ((TextView)view.findViewById(R.id.hotelName)).setText(hotel.name);
        ((TextView)view.findViewById(R.id.hotelAddress)).setText(hotel.address);
        ((TextView)view.findViewById(R.id.hotelDistance)).setText(hotel.distance + Strings.DISTANCE);
        String [] rooms;
        if (hotel.suites_availability.isEmpty()) {
            rooms = new String[] {};
        } else {
            rooms = hotel.suites_availability.split(":");
        }

        ((TextView)view.findViewById(R.id.hotelFree)).setText(Strings.FREE_ROOMS + rooms.length);
        String stars = "";
        for (int j = 0; j < hotel.stars; j++) {
            stars += "*";
        }
        TextView starsTV = (TextView)view.findViewById(R.id.hotelStars);
        if (stars.isEmpty()) {
            starsTV.setVisibility(View.GONE);
        } else {
            starsTV.setVisibility(View.VISIBLE);
            starsTV.setText(stars);
        }
        return view;
    }
}
