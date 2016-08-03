package com.artfonapps.hotelstask.network.events;


import com.artfonapps.hotelstask.db.models.Hotel;

public class HotelLoadEvent {
    public Hotel getHotel() {
        return hotel;
    }

    private Hotel hotel;

    public HotelLoadEvent(Hotel hotel) {
        this.hotel = hotel;
    }
}
