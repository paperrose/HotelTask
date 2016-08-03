package com.artfonapps.hotelstask.db.models;

public enum BedType {
    DOUBLE_FLOOR("Двухярусная", 1),
    SINGLE("Односпальная", 1),
    DOUBLE("Двуспальная", 1),
    LARGE_DOUBLE("Широкая двуспальная", 1),
    EXTRA_LARGE_DOUBLE("Особо широкая двуспальная", 1);

    private String name;
    private int icon;

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    BedType(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }
}
