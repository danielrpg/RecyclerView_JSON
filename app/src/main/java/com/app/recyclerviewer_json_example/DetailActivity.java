package com.app.recyclerviewer_json_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        String imageUrl = intent.getStringExtra(MainActivity.EXTRA_URL);
        String creatorName = intent.getStringExtra(MainActivity.EXTRA_CREATOR);
        int numberLikes = intent.getIntExtra(MainActivity.EXTRA_LIKES, 0);

        ImageView imageView = findViewById(R.id.image_detail);
        TextView creatorView = findViewById(R.id.name_creator_detail);
        TextView likesView = findViewById(R.id.number_likes_detail);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        creatorView.setText(creatorName);
        likesView.setText("Likes: " + numberLikes);
    }
}
