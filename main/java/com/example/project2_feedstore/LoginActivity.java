package com.example.project2_feedstore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2_feedstore.DB.AppDataBase;
import com.example.project2_feedstore.DB.FeedStoreDAO;
import com.example.project2_feedstore.databinding.LoginActivityBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mButtonLogin;
    private FeedStoreDAO mFeedStoreDAO;

    private String mUsername;
    private String mPassword;
    private int mUserID = -1;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        wireupDisplay();
        getDatabase();

    }
    private void wireupDisplay(){
        mUsernameField = findViewById(R.id.editTextTextLoginUserName);
        mPasswordField = findViewById(R.id.editTextLoginPassword);

        mButtonLogin = findViewById(R.id.buttonLogin);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = LandingPage.intentFactory(getApplicationContext(), mUserID+1);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private boolean validatePassword(){
        return mUser.getPassword().equals(mPassword);
    }
    private void getValuesFromDisplay(){
        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
    }
    private boolean checkForUserInDatabase(){
        mUser =  mFeedStoreDAO.getUsersByUsername(mUsername);
        if(mUser == null){
            Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void getDatabase(){
        mFeedStoreDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .FeedStoreDAO();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
