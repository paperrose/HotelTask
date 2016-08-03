package com.artfonapps.hotelstask.db;

import com.activeandroid.query.Select;
import com.artfonapps.hotelstask.db.models.Hotel;
import com.artfonapps.hotelstask.db.models.Image;

import java.util.List;

public class Helper {
    public static void saveHotels(List<Hotel> hotels) {
        for (Hotel hotel:hotels) {
            hotel.save();
        }
    }

    public static void saveHotels(Hotel hotel) {
        hotel.save();
        for (Image image : hotel.images)
        {
            image.hotel = hotel;
            image.save();
        }
    }

    public static List<Hotel> getHotelsInOrder(String order_column, String order) {
        return (new Select().from(Hotel.class).orderBy(order_column + order).execute());
    }
}
