package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {

    ListView usersListView;
    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        usersListView = findViewById(R.id.usersListView);

        myAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,users);
        usersListView.setAdapter(myAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser object : objects) {

                            users.add(object.getUsername());
                            Log.i("username", object.getUsername());
                        }
                        myAdapter.notifyDataSetChanged();
                    } else {
                        Log.i("Query", "failed");
                    }
                } else {
                    Log.i("Query", "failed");
                }
            }
        });

        Log.i("Logged in",ParseUser.getCurrentUser().getUsername());
    }
}
