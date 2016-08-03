package com.artfonapps.hotelstask.db.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.artfonapps.hotelstask.constants.Columns;
import com.artfonapps.hotelstask.constants.Tables;

@Table(name = Tables.IMAGE, id = Columns._ID)
public class Image extends Model {
    @Column(name = Columns.ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;
    @Column(name = Tables.HOTEL, onDelete = Column.ForeignKeyAction.CASCADE)
    public Hotel hotel;
    @Column(name = Columns.URL)
    public String url;

    public Image() {
    }
}
