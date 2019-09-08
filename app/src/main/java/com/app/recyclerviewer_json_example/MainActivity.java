package com.app.recyclerviewer_json_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener{

    private final static String KITTEN_CATS_URL = "https://pixabay.com/api/?key=5303976-fd6581ad4ac165d1b75cc15b3&q=kitten&image_type=photo&pretty=true";
    public final static String EXTRA_URL = "imageUrl";
    public final static String EXTRA_CREATOR = "creatorName";
    public final static String EXTRA_LIKES = "likeCount";

    private RecyclerView mRecycler;
    private ItemAdapter mAdapter;
    private ArrayList<Item> mItemList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycler = findViewById(R.id.recycler_view);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mItemList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        parseJson();
    }

    private void parseJson() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, KITTEN_CATS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            for (int i=0; i < jsonArray.length() ; i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("user");
                                int likes = hit.getInt("likes");
                                String imageUrl = hit.getString("webformatURL");

                                mItemList.add(new Item(imageUrl, creatorName, likes));
                            }

                            mAdapter = new ItemAdapter(MainActivity.this, mItemList);
                            mAdapter.setOnItemClickListener(MainActivity.this);

                            mRecycler.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent intentDetail = new Intent(this, DetailActivity.class);

        Item item = mItemList.get(position);

        intentDetail.putExtra(EXTRA_URL, item.getImageUrl());
        intentDetail.putExtra(EXTRA_CREATOR, item.getCreator());
        intentDetail.putExtra(EXTRA_LIKES, item.getLikes());

        startActivity(intentDetail);
    }
}
