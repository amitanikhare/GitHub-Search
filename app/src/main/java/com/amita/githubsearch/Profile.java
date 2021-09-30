package com.amita.githubsearch;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    String user;
    ImageView profile;
    TextView bio , follower, following;
    String url="https://api.github.com/users/";
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        Log.d("user", getIntent().getStringExtra("Username"));
        user = getIntent().getStringExtra("Username");
        url = url + user;
        profile = findViewById(R.id.image);
        bio = findViewById(R.id.bio);
        follower = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        mRecyclerView = findViewById(R.id.repolist);
        loadData(url);
        loadrepository(url);
    }
    public void loadData(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String imageUrl = response.getString("avatar_url");
                            String followersc = response.getString("followers");
                            String followingc = response.getString("following");
                            String bioc = response.getString("bio");
                            follower.setText("Followers : "+followersc);
                            following.setText("Following : "+followingc);
                            bio.setText("BIO : "+bioc);
                            Glide.with(Profile.this).load(imageUrl).into(profile);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Profile.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });
//              queue.add(jsonObjectRequest);
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
    public void loadrepository(String url){
        url = url + "/repos?per_page=100&sort=created";
//      RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Repository[] repoarray = gson.fromJson(response, Repository[].class);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(Profile.this));
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setAdapter(new RepositoryAdapter(Profile.this , repoarray));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
            }
    });
        //queue.add(stringRequest);
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}