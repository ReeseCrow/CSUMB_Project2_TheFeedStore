/** Title: The Feed Store - Front Page
 * Description: This code will act as the starting point for the app. It is the front page where the user could either login or create a account.
 * Author:Rhys Crowell
 * Date: 05/12/2023
* */

package com.example.project2_feedstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2_feedstore.DB.AppDataBase;
import com.example.project2_feedstore.DB.FeedStoreDAO;
import com.example.project2_feedstore.databinding.ActivityMainBinding;

import java.util.List;

public class MainFrontPageActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.project2_feedstore.userIdKey";
    private static final String PREFERENCES_KEY = "com.daclink.gymlog_v_sp22.PREFERENCES_KEY";
    private ActivityMainBinding binding;
    private Button mLogin;
    private Button mCreate;
    private FeedStoreDAO mFeedStoreDAO;
    private int mUserId = -1;
    private SharedPreferences mPreferences = null;
    private User mUser;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mLogin = binding.mainLoginButton;
        mCreate = binding.mainCreateButton;


        mFeedStoreDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().FeedStoreDAO();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent1 = LoginActivity.intentFactory(getApplicationContext());
                startActivity(myintent1);
            }
        });
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent2 = CreateAccountActivity.intentFactory(getApplicationContext());
                startActivity(myintent2);
            }
        });

    }
    private void loginUser(int userId) {
        mUser = mFeedStoreDAO.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        if(mUser != null){
            MenuItem item = menu.findItem(R.id.userMenuLogout);
            item.setTitle(mUser.getUsername());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void getDatabase() {
        mFeedStoreDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build().FeedStoreDAO();
    }
    private void addUserToPreferences(int userId) {
        if(mPreferences == null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
    }
    private void checkForUser() {
        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //do we have a user in the preferences?
        if(mUserId != -1){
            return;
        }


//        mUserId = preferences.getInt(USER_ID_KEY, -1);
        if(mPreferences == null){
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }
        //do we have any users at all?
        List<User> users = mFeedStoreDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("gergAMorb", "greg123");
            User altUser = new User("gerb", "greg123");
            mFeedStoreDAO.insert(defaultUser, altUser);
        }
        Intent intent = MainFrontPageActivity.intentFactory(getApplicationContext());
        startActivity(intent);
    }
    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Logout?");
        alertBuilder.setPositiveButton("Yes",
                (dialog, which) -> {
                    clearUserFromIntent();
                    clearUserFromPref();
                    mUserId = -1;
                    checkForUser();
                });
        alertBuilder.setNegativeButton("No",
                (dialog, which) -> {

                });
        alertBuilder.create().show();
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }
    private void clearUserFromPref(){
        addUserToPreferences(-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.userMenuLogout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, MainFrontPageActivity.class);
        return intent;
    }
}