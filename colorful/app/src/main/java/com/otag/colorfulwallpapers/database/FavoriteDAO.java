package com.otag.colorfulwallpapers.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDAO {


    @Query("select linkimage from favorite_table order by time desc ")
    List<String> getAllFavorite();

    @Insert
    void insertFavorite(Favorite favorite);
    @Query("delete from  favorite_table where linkimage=:id")
    void deleteFavorite(String id);


    @Query("select * from favorite_table where linkimage=:id")
    Favorite isExist(String id);
}
