package com.artfonapps.hotelstask.network.events;


import com.artfonapps.hotelstask.db.models.Hotel;

import java.util.List;

public class HotelsLoadEvent {
    public List<Hotel> getHotels() {
        return hotels;
    }

    private List<Hotel> hotels;

    public HotelsLoadEvent(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
