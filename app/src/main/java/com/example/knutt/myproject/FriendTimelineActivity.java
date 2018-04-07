package com.example.knutt.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendTimelineActivity extends AppCompatActivity {
    private ProfilePictureView profilePictureView;
    private TextView textname;
    private ArrayList<String> addMessage = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_timeline);
        profilePictureView = (ProfilePictureView)findViewById(R.id.profiletm);
        textname = (TextView)findViewById(R.id.textnametm);
        listView = (ListView)findViewById(R.id.listviewtm);


        SharedPreferences sp = getSharedPreferences("friendname", Context.MODE_PRIVATE);
        String name = sp.getString("name","");

        SharedPreferences sp2 = getSharedPreferences("friendname2", Context.MODE_PRIVATE);
        String id = sp2.getString("id","");

        profilePictureView.setProfileId(id);
        textname.setText(name);

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+id+"/feed?limit=500",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                        // JSON GETS THE DATA
                        JSONObject jsonData = response.getJSONObject();

                        try {



                            JSONArray postsData = jsonData.getJSONArray("data");
                            if (postsData != null) {
                                addMessage.clear();
                                for (int i = 0; i < postsData.length(); i++) {
                                    JSONObject story = postsData.getJSONObject(i);
                                    String MessageStory = "";
                                    String Message = "";
//
                                    if(story.has("story")){
                                      MessageStory = story.getString("story");


                                      }
                                    if(story.has("message")){
                                        Message = story.getString("message");





                                    }

                          addMessage.add("เรื่องราว : "+MessageStory+"\n"+"ข้อความที่โพสต์ : "+Message);




                                }




                            }

                            arrayAdapter = new ArrayAdapter<String>(FriendTimelineActivity.this,R.layout.index_timelinefriend,addMessage);
                            listView.setAdapter(arrayAdapter);




                        } catch (Exception e) {
                            Log.d("JSON", "error" + e.toString());
                        }

                    }
                });

        request.executeAsync();
        Bundle parameters = new Bundle();
        parameters.putString("fields", "created_time,message,story");
        request.setParameters(parameters);
        request.executeAsync();


    }
}
