package com.example.project2_feedstore.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2_feedstore.User;

@Database(entities = {User.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "FeedStore.db";
    public static final String USERS_TABLE = "users_table";
    public static final String PURCHASE_TABLE = "purchase_table";
    public static final String INVENTORY_TABLE = "inventory_table";
    public static final String RENTALS_TABLE = "rental_table";
    public static final String CUSTOMERSERVICE_TABLE = "customerService_table";

    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();

    public abstract FeedStoreDAO FeedStoreDAO();

    public static AppDataBase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
}
