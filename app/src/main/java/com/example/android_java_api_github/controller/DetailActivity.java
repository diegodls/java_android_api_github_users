package com.example.android_java_api_github.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android_java_api_github.R;

/**
 * Created by DiegoL on 2/20/20
 */
public class DetailActivity extends AppCompatActivity {

    TextView Link, Username;
    Toolbar mActionToolbar;
    ImageView imageView;

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.activity_details_user_image_header);
        Username = findViewById(R.id.activity_details_username);
        Link = findViewById(R.id.activity_details_github_link);

        //recuperando dados do putExtra
        String username = getIntent().getExtras().getString("login");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        String link = getIntent().getExtras().getString("html_url");

        Link.setText(link);
        Linkify.addLinks(Link, Linkify.WEB_URLS);

        Username.setText(username);
        Glide.with(this)
                .load(avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.load)
                .into(imageView);
        getSupportActionBar().setTitle(("Detalhes"));
    }

    private Intent createSharedForcasIntent() {
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("link");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Confira este magnifico pefil @" + username + " , " + link)
                .getIntent();
        return shareIntent;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent((createSharedForcasIntent()));
        return true;
    }
}
