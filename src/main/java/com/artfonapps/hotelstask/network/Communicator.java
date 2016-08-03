package com.artfonapps.hotelstask.network;

import android.content.ContentValues;
import android.util.Log;

import com.artfonapps.hotelstask.constants.Errors;
import com.artfonapps.hotelstask.db.Helper;
import com.artfonapps.hotelstask.db.models.Hotel;
import com.artfonapps.hotelstask.network.events.HotelLoadEvent;
import com.artfonapps.hotelstask.network.events.HotelsLoadEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Produce;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Communicator {

    private static final String TAG = "Communicator";
    private static final String SERVER_ADDRESS = "https://raw.githubusercontent.com/paperrose/HT-data/master/data/";
    private static Retrofit retrofit;
    public static final Communicator INSTANCE = new Communicator();

    public static String getImageAddress(String image, String hotel_id) {
        return SERVER_ADDRESS + "hotels/" + hotel_id + "/" + image;
    }

    private Communicator() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Produce
    public HotelsLoadEvent produceHotelsLoadEvent(List<Hotel> hotels) {
        return new HotelsLoadEvent(hotels);
    }

    @Produce
    public HotelLoadEvent produceHotelLoadEvent(Hotel hotel) {
        return new HotelLoadEvent(hotel);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }


    public void communicate(String method, ContentValues vars) {
        switch (method) {
            case "Hotels":
                getHotels();
                break;
            case "Hotel":
                getHotel(vars.getAsInteger("id"));
                break;
            default:
                break;
        }
    }

    public void getHotels(){
        RequestInterface communicatorInterface = retrofit.create(RequestInterface.class);
        Callback<List<Hotel>> hotelsCallback = new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.code() == 200) {
                    BusProvider.getInstance().post(produceHotelsLoadEvent(response.body()));
                    Helper.saveHotels(response.body());
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(-1, Errors.SERVER_ERROR));
                    Log.e(TAG, "" + response.code());
                }

            }
            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                BusProvider.getInstance().post(produceErrorEvent(-1, Errors.SERVER_ERROR));
                Log.e(TAG, t.getMessage());
            }
        };
        Call<List<Hotel>> response = communicatorInterface.hotels();
        response.enqueue(hotelsCallback);
    }

    public void getHotel(int id){
        RequestInterface communicatorInterface = retrofit.create(RequestInterface.class);
        Callback<Hotel> hotelCallback = new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response) {
                if (response.code() == 200) {
                    Helper.saveHotels(response.body());
                    BusProvider.getInstance().post(produceHotelLoadEvent(response.body()));
                } else {
                    BusProvider.getInstance().post(produceErrorEvent(-1, Errors.HOTEL_ERROR));
                    Log.e(TAG, ""+response.code());
                }

            }
            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                BusProvider.getInstance().post(produceErrorEvent(-1, Errors.HOTEL_ERROR));
                Log.e(TAG, t.getMessage());
            }
        };
        Call<Hotel> response = communicatorInterface.hotel(id);
        response.enqueue(hotelCallback);
    }



}
