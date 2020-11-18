package com.otag.colorfulwallpapers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class},version = 1)
public abstract class FavoriteRoomDatabase extends RoomDatabase {

    public abstract FavoriteDAO dao();
    private static FavoriteRoomDatabase instance;

    public   static FavoriteRoomDatabase getDatabse(Context context){

        if (instance==null){

            synchronized (FavoriteRoomDatabase.class){
                if (instance==null){

                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteRoomDatabase.class,
                            "favorite_database")
                            .build();




                }





            }



        }



        return instance;


    }







}