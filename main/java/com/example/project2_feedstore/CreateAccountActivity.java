package com.example.project2_feedstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2_feedstore.DB.AppDataBase;
import com.example.project2_feedstore.DB.FeedStoreDAO;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mButtonCreate;
    private FeedStoreDAO mFeedStoreDAO;

    private String mUsername;
    private String mPassword;
    private User mUser;
    private int mUserId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);

        wireupDisplay();
        getDatabase();

    }
    private void wireupDisplay(){
        mUsernameField = findViewById(R.id.editTextCreateUserName);
        mPasswordField = findViewById(R.id.editTextTextCreatePassword);

        mButtonCreate = findViewById(R.id.buttonCreateAccount);
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValuesFromDisplay();
                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(CreateAccountActivity.this, "Password is Taken", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = LandingPage.intentFactory(getApplicationContext(), mUserId);
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
    private void createNewAccount(){

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
        Intent intent1 = new Intent(context, CreateAccountActivity.class);
        return intent1;
    }
}
