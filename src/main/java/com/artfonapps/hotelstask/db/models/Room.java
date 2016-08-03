package com.artfonapps.hotelstask.db.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.artfonapps.hotelstask.constants.Columns;

@Table(name = "Room", id = Columns._ID)
public class Room extends Model {
    @Column(name = Columns.ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;
    @Column(name = "name")
    public String name;  //  Классический номер с одноместной кроватью и правом посещения бассейна
    @Column(name = "max_guests")
    public int max_guests; // 2
    @Column(name = "bed_type")
    public BedType bed_type; //Тип кровати
    @Column(name = "with_breakfast")
    public boolean with_breakfast; // Завтрак включен / Без завтрака
    @Column(name = "pay_return")
    public boolean pay_return; // Можно отменить / Нельзя отменить
    @Column(name = "hotel")
    public Hotel hotel; // В каком отеле
    @Column(name = "is_free")
    public boolean is_free; // Свободен
    @Column(name = "price")
    public float old_price; // Цена за номер
    @Column(name = "discount")
    public float discount; // Скидка в процентах
    @Column(name = "size")
    public float size; // Площадь номера в м^2

    public Room() {
    }
}
