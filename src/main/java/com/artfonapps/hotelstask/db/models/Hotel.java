package com.artfonapps.hotelstask.db.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.artfonapps.hotelstask.constants.Columns;
import com.artfonapps.hotelstask.constants.Tables;

import java.util.List;

@Table(name = Tables.HOTEL, id = Columns._ID)
public class Hotel extends Model {
    @Column(name = Columns.ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;
    @Column(name = Columns.NAME)
    public String name;//        "name": "Dream",
    @Column(name = Columns.ADDRESS)
    public String address;//        "address": "210 W. 55 STREET, NEW YORK NY 10019, UNITED STATES",
    @Column(name = Columns.STARS)
    public float stars;//        "stars": 4.0,
    @Column(name = Columns.DISTANCE)
    public float distance;//        "distance": 554.4,
 //   @Column(name = Columns.IMAGE)
//    public String image;//        "image": "a7.jpg",
    @Column(name = Columns.SUITES_AVAILABILITY)
    public String suites_availability;//        "suites_availability": "42:33:22",
    @Column(name = Columns.LAT)
    public double lat;//        "lat": 40.76438100000000,
    @Column(name = Columns.LON)
    public double lon;//        "lon": -73.98169700000000

    public List<Image> images;

    public List<Image> getImages()
    {
        return getMany(Image.class, Tables.HOTEL);
    }


    public Hotel() {
        super();
    }
}
