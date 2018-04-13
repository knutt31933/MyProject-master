package com.example.knutt.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    private ProfilePictureView profilePictureView;
    private TextView textname,textemail;
    private JSONObject reponse;
    private CallbackManager callbackManager;
    private Button btnback;
    private DatabaseForFriends databaseForFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_profile);
        btnback = (Button)findViewById(R.id.backbtn);
        databaseForFriends = new DatabaseForFriends(getApplicationContext());
        databaseForFriends.getWritableDatabase();

        final Intent intent = getIntent();
        final String jsondata = intent.getStringExtra("userProfile");

        profilePictureView = (ProfilePictureView)findViewById(R.id.profileview);
        textname = (TextView)findViewById(R.id.textname);

        SharedPreferences sp = getSharedPreferences("username", Context.MODE_PRIVATE);
        String name = sp.getString("username", "");

        SharedPreferences sp2 = getSharedPreferences("userid", Context.MODE_PRIVATE);
        String id = sp2.getString("userid", "");

        profilePictureView.setProfileId(id);
        textname.setText(name);

        Toast.makeText(ProfileActivity.this,"id = "+id,Toast.LENGTH_LONG).show();


//        btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent profile = new Intent(ProfileActivity.this,AccountActivity.class);
//               profile.putExtra("userProfile",jsondata);
//               startActivity(profile);
//
//            }
//        });

//        try {
//
//            reponse = new JSONObject(jsondata);
//            String fname = reponse.get("first_name").toString();
//            String lname = reponse.get("last_name").toString();
//            String idprofile = reponse.get("id").toString();
//            String fullname = fname + " " +lname;
//            //String bh = reponse.get("birthday").toString();
//            textname.setText(fullname);
//
//            profilePictureView.setProfileId(idprofile);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }




    }
}
