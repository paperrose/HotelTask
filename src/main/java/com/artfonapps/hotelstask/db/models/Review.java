package com.artfonapps.hotelstask.db.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.artfonapps.hotelstask.constants.Columns;

@Table(name = "Review", id = Columns._ID)
public class Review extends Model {
    @Column(name = Columns.ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;
    @Column(name = "author")
    public String author;
    @Column(name = "hotel")
    public Hotel hotel;
    @Column(name = "rating")
    public float rating;
    @Column(name = "text")
    public String text;

    public Review() {
    }

}
