package com.example.project2_feedstore;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_feedstore.DB.AppDataBase;

@Entity(tableName = AppDataBase.INVENTORY_TABLE)
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int mInvID;

    private String mProductName;
    private boolean isRental;
    private int mProductInventoryQuantity;

    public Inventory(int invID, String productName) {
        mInvID = invID;
        mProductName = productName;
    }

    public int getInvID() {
        return mInvID;
    }

    public void setInvID(int invID) {
        mInvID = invID;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public boolean isRental() {
        return isRental;
    }

    public void setRental(boolean rental) {
        isRental = rental;
    }

    public int getProductInventoryQuantity() {
        return mProductInventoryQuantity;
    }

    public void setProductInventoryQuantity(int productInventoryQuantity) {
        mProductInventoryQuantity = productInventoryQuantity;
    }
}
