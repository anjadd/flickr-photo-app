package com.anjad.flickrphotoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjad.flickrphotoapp.models.Photo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenActivity extends AppCompatActivity {

    private static final String TAG = "FullScreenActivity";

    @BindView(R.id.iv_fullscreen_photo)
    ImageView mIvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        ButterKnife.bind(this);

        getPhoto();
    }

    private void getPhoto() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String link = intent.getStringExtra("clickedPhoto");

            Glide.with(this)
                    .load(link)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(mIvPhoto);
        }
    }
}
