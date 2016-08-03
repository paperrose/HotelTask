package com.artfonapps.hotelstask.network;

import com.artfonapps.hotelstask.constants.Columns;
import com.artfonapps.hotelstask.db.models.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {

    @GET("hotels/hotels.json")
    Call<List<Hotel>> hotels();

    @GET("hotels/{id}/desc.json")
    Call<Hotel> hotel(@Path(Columns.ID) int hotel_id);



/*    @GET("hotels/{hotel_id}/rooms.json")
    Call<List<Room>> rooms(@Path("hotel_id") int hotel_id);

    @GET("hotels/{hotel_id}/reviews.json")
    Call<List<Review>> reviews(@Path("hotel_id") int hotel_id);

    @GET("hotels/{hotel_id}/images.json")
    Call<List<Image>> images(@Path("hotel_id") int hotel_id);

    @GET("rooms/{room_id}.json")
    Call<Room> room(@Path("room_id") int room_id);*/
}
