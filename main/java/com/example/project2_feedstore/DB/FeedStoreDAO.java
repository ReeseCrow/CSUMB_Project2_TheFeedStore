/**
 * Title: Feed Store User Database
 * Description: This java file will define the data base that will store user information
 * Author: Rhys Crowell
 * Date: 04/23/2023
* */

package com.example.project2_feedstore.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2_feedstore.Inventory;
import com.example.project2_feedstore.User;

import java.util.List;

@Dao
public interface FeedStoreDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User users);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE mUsername = :username")
    User getUsersByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE mUserID = :userId")
    User getUserByUserId(int userId);

//    @Insert
//    void insert(Inventory... products);
//
//    @Update
//    void update(Inventory... products);
//
//    @Delete
//    void delete(Inventory products);
//
//    @Query("SELECT * FROM " + AppDataBase.INVENTORY_TABLE)
//    List<Inventory> getInventory();
//
//    @Query("SELECT * FROM " + AppDataBase.INVENTORY_TABLE + " WHERE mProductName = :productName")
//    Inventory getProductByName(String productName);
//
//    @Query("SELECT * FROM " + AppDataBase.INVENTORY_TABLE + " WHERE mInvID = :invId")
//    User getProductByInventoryId(int invId);
}
