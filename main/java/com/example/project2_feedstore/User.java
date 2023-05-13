/** Title: User
 *  Description: This java class will define what fields and keys will encompass the created database, setting and getting established values.
 *  Author: Rhys Crowell
 *  Date: 04/23/2023
* */

package com.example.project2_feedstore;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2_feedstore.DB.AppDataBase;

@Entity(tableName = AppDataBase.USERS_TABLE)
public class User {
 @PrimaryKey(autoGenerate = true)
 private int mUserID;

 private boolean mIsAdmin;

 private String mPassword;

 private String mUsername;

 public User(String username, String password) {
  mPassword = password;
  mUsername = username;
  //mIsAdmin = isAdmin;
 }

 @Override
 public String toString() {
  return "User: " + mUserID + "\n" +
          "Username: " + mUsername + "\n" +
          "Password: " + mPassword + "\n" +
          "Admin: " + mIsAdmin + "\n";
 }

 public int getUserID() {
  return mUserID;
 }

 public void setUserID(int userID) {
  mUserID = userID;
 }

 public boolean getIsAdmin() {
  return mIsAdmin;
 }

 public void setIsAdmin(boolean isAdmin) {
  mIsAdmin = isAdmin;
 }

 public String getPassword() {
  return mPassword;
 }

 public void setPassword(String password) {
  mPassword = password;
 }

 public String getUsername() {
  return mUsername;
 }

 public void setUsername(String username) {
  mUsername = username;
 }
}
