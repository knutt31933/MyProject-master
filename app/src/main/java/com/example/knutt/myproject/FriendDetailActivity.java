package com.example.knutt.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FriendDetailActivity extends AppCompatActivity {

private ListView listView;
private DatabaseForFriends databaseForFriends;
private ArrayList<String> addName = new ArrayList<>();
private ArrayList<String> addID= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        databaseForFriends = new DatabaseForFriends(getApplicationContext());
        ArrayList<HashMap<String, String>> test = databaseForFriends.getIDFriends();
        for(int i = 0;i<test.size();i++){
            addID.add(test.get(i).get("FriendsIDFB"));
            addName.add(test.get(i).get("FriendsNameFB"));
        }

        listView = (ListView)findViewById(R.id.Listview);
        MyAdapter myAdapter = new MyAdapter(this,addName,addID);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = addName.get(i);
                String id = addID.get(i);
                SharedPreferences sharedPref = getSharedPreferences("friendname", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", name);
                editor.commit();
                SharedPreferences sharedPref2 = getSharedPreferences("friendname2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPref2.edit();
                editor2.putString("id", id);
                editor2.commit();
                Toast.makeText(getApplicationContext(),name +" "+id,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FriendDetailActivity.this,FriendTimelineActivity.class);
                startActivity(intent);
            }
        });





    }
}

class MyAdapter extends ArrayAdapter<String>{
    Context context;
    ArrayList<String> name;
    ArrayList<String> id ;

    public MyAdapter(Context context, ArrayList<String> name,ArrayList<String> id) {
        super(context, R.layout.index_paging,R.id.textname,name);
        this.context = context;
        this.name = name;
        this.id = id;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent ){
        LayoutInflater layoutInflater =(LayoutInflater) getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.index_paging,parent,false);
        ProfilePictureView profilePictureView = (ProfilePictureView) row.findViewById(R.id.profile);
        TextView textView = (TextView) row.findViewById(R.id.textname);
        textView.setTextColor(Color.WHITE);
        profilePictureView.setProfileId(id.get(position));
        textView.setText(name.get(position));





        return row;

    }

}




