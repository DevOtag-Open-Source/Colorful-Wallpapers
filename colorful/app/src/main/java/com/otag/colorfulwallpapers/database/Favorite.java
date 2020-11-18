package com.otag.colorfulwallpapers.database;
import androidx.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite  {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "linkimage")
    public String linkImage;

    @ColumnInfo(name = "time")
    public String time;

    public Favorite(@NonNull String linkImage, String time) {
        this.linkImage = linkImage;
        this.time = time;
    }
}
